package com.iteration.bookmyservice.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.adapter.ManageBookingListAdapter;
import com.iteration.bookmyservice.model.Message;
import com.iteration.bookmyservice.model.MessageOTP;
import com.iteration.bookmyservice.model.Service;
import com.iteration.bookmyservice.model.ServiceList;
import com.iteration.bookmyservice.model.Timeslot;
import com.iteration.bookmyservice.model.TimeslotList;
import com.iteration.bookmyservice.model.TokenUpdateResponse;
import com.iteration.bookmyservice.network.Config;
import com.iteration.bookmyservice.network.GetProductDataService;
import com.iteration.bookmyservice.network.RetrofitInstance;
import com.iteration.bookmyservice.network.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookMyServiceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText txtName,txtEmail,txtOTP,txtMobile,txtAddress,txtMake,txtModel,txtMsgYear,txtEngineType,txtVanPlateNo,txtComment;
    Button btnEmailSend,btnEmailVeri,btnSubmit;
    LinearLayout llOTPBox,llBox;
    RadioGroup rgServiceOpt;
    RadioButton rbYourPlace,rbOurPlace;
    Spinner spTimeSlot;
    LinearLayout llDate;
    TextView txtDate,txtResendOTPCode;
    CheckBox cbCondition;
    ArrayList<Service> ServiceListArray = new ArrayList<>();
    ArrayList<String> ServiceIdArray = new ArrayList<>();
    ArrayList<String> ServiceNameArray = new ArrayList<>();
    ArrayList<Timeslot> TimeslotListArray = new ArrayList<>();
    ArrayList<String> TimeslotIdArray = new ArrayList<>();
    ArrayList<String> TimeslotArray = new ArrayList<>();
    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    String OTP,TimeSlotId,service_opt;
    SessionManager session;
    int flag = 0;
    TextView txtService;
    ArrayList<String> positions = new ArrayList<String>();
    String BookinglistString="";
    private static final String TAGs = MainActivity.class.getSimpleName();
    GetProductDataService productDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_my_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        session = new SessionManager(BookMyServiceActivity.this);
        flag = session.checkLogin();

        HashMap<String,String> user = session.getUserDetails();
        String user_email = user.get(SessionManager.user_email);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        txtName = (EditText)findViewById(R.id.txtName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtOTP = (EditText)findViewById(R.id.txtOTP);

        llOTPBox = (LinearLayout) findViewById(R.id.llOTPBox);
        llBox = (LinearLayout) findViewById(R.id.llBox);
        txtResendOTPCode = (TextView) findViewById(R.id.txtResendOTPCode);

        btnEmailSend = (Button) findViewById(R.id.btnEmailSend);
        btnEmailVeri = (Button)findViewById(R.id.btnEmailVeri);

        txtMobile = (EditText)findViewById(R.id.txtMobile);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        //txtVINNumber = (EditText)findViewById(R.id.txtVINNumber);
        txtMake = (EditText)findViewById(R.id.txtMake);
        txtModel = (EditText)findViewById(R.id.txtModel);
        txtMsgYear = (EditText)findViewById(R.id.txtMsgYear);
        txtEngineType = (EditText)findViewById(R.id.txtEngineType);
        txtVanPlateNo = (EditText)findViewById(R.id.txtVanPlateNo);
        txtComment = (EditText)findViewById(R.id.txtComment);
        rgServiceOpt = (RadioGroup) findViewById(R.id.rgServiceOpt);
        rbYourPlace = (RadioButton) findViewById(R.id.rbYourPlace);
        rbOurPlace = (RadioButton) findViewById(R.id.rbOurPlace);
        rbYourPlace.setChecked(true);

        if (flag == 1)
        {
            llOTPBox.setVisibility(View.GONE);
            btnEmailVeri.setVisibility(View.GONE);
            btnEmailSend.setVisibility(View.GONE);
            llBox.setVisibility(View.VISIBLE);
            txtEmail.setText(user_email);
            txtEmail.setEnabled(false);
        }
        else
        {
            llOTPBox.setVisibility(View.GONE);
            btnEmailVeri.setVisibility(View.GONE);
            btnEmailSend.setVisibility(View.VISIBLE);
            llBox.setVisibility(View.GONE);
            txtEmail.setEnabled(true);
        }

        txtResendOTPCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOTPBox.setVisibility(View.GONE);
                btnEmailVeri.setVisibility(View.GONE);
                btnEmailSend.setVisibility(View.VISIBLE);
                llBox.setVisibility(View.GONE);
                txtEmail.setText("");
                txtOTP.setText("");
                txtEmail.setEnabled(true);
            }
        });

        btnEmailSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString();

                final ProgressDialog dialog = new ProgressDialog(BookMyServiceActivity.this);
                dialog.setMessage("Loading...");
                dialog.setCancelable(true);
                dialog.show();

                Call<MessageOTP> EmailSendCall = productDataService.getEmailSendOtpData(email);
                EmailSendCall.enqueue(new Callback<MessageOTP>() {
                    @Override
                    public void onResponse(Call<MessageOTP> call, Response<MessageOTP> response) {
                        dialog.dismiss();
                        String status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if (status.equals("1"))
                        {
                            OTP = response.body().getOtp();
                            Log.d("OTP",""+OTP);
                            /*txtOTP.setText(OTP);*/
                            llOTPBox.setVisibility(View.VISIBLE);
                            btnEmailVeri.setVisibility(View.VISIBLE);
                            btnEmailSend.setVisibility(View.GONE);
                        }
                        else
                        {
                            Toast.makeText(BookMyServiceActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageOTP>  call, Throwable t) {
                        Toast.makeText(BookMyServiceActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnEmailVeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OTP.equals(txtOTP.getText().toString()))
                {
                    llOTPBox.setVisibility(View.GONE);
                    btnEmailVeri.setVisibility(View.GONE);
                    btnEmailSend.setVisibility(View.GONE);
                    llBox.setVisibility(View.VISIBLE);
                    txtEmail.setEnabled(false);

                    String UserEmail = txtEmail.getText().toString();

                    session.createLoginSession(UserEmail);

                }
                else
                {
                    Toast.makeText(BookMyServiceActivity.this, "Not Match OTP Code", Toast.LENGTH_SHORT).show();
                }
            }
        });

        service_opt = "Your Place";

        rgServiceOpt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rbYourPlace:
                        txtAddress.setEnabled(true);
                        txtAddress.setText("");
                        service_opt = "Your Place";
                        break;
                    case R.id.rbOurPlace:
                        txtAddress.setText("3903, Millar Avenue, Unit No 10(backside) Saskatoon");
                        txtAddress.setEnabled(false);
                        service_opt = "Our Place";
                        break;
                }
            }
        });

        LinearLayout llService = (LinearLayout)findViewById(R.id.llService);
        txtService = (TextView)findViewById(R.id.txtService);
        spTimeSlot = (Spinner)findViewById(R.id.spTimeSlot);

        Call<ServiceList> ServiceListCall = productDataService.getServiceData();
        ServiceListCall.enqueue(new Callback<ServiceList>() {
            @Override
            public void onResponse(Call<ServiceList> call, Response<ServiceList> response) {
                String status = response.body().getStatus();
                String message = response.body().getMessage();
                if(status.equals("1"))
                {
                    ServiceListArray = response.body().getServiceList();
                    for (int i=0;i<ServiceListArray.size();i++)
                    {
                        String sn = ServiceListArray.get(0).getService_name();
                        txtService.setText(sn);
                    }
                }
                else
                {
                    Toast.makeText(BookMyServiceActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceList> call, Throwable t) {
                Toast.makeText(BookMyServiceActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });



        llService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                positions.clear();
                BookinglistString = "";

                final Dialog dial = new Dialog(BookMyServiceActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dial.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dial.setContentView(R.layout.service_list_dialog);
                dial.setCancelable(true);

                Button btnDDone = (Button)dial.findViewById(R.id.btnDDone);
                ImageView ivDClose = (ImageView)dial.findViewById(R.id.ivDClose);
                ivDClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dial.dismiss();
                    }
                });

                final RecyclerView rvDServiceList = (RecyclerView)dial.findViewById(R.id.rvDServiceList);
                rvDServiceList.setHasFixedSize(true);

                RecyclerView.LayoutManager manager = new LinearLayoutManager(BookMyServiceActivity.this,LinearLayoutManager.VERTICAL,false);
                rvDServiceList.setLayoutManager(manager);

                ServiceMultiListAdapter serviceMultiListAdapter = new ServiceMultiListAdapter(BookMyServiceActivity.this, ServiceListArray);
                rvDServiceList.setAdapter(serviceMultiListAdapter);

                /*Call<ServiceList> ServiceListCall = productDataService.getServiceData();
                ServiceListCall.enqueue(new Callback<ServiceList>() {
                    @Override
                    public void onResponse(Call<ServiceList> call, Response<ServiceList> response) {
                        String status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if(status.equals("1"))
                        {
                            ServiceListArray = response.body().getServiceList();
                            ServiceMultiListAdapter serviceMultiListAdapter = new ServiceMultiListAdapter(BookMyServiceActivity.this, ServiceListArray);
                            rvDServiceList.setAdapter(serviceMultiListAdapter);

                        }
                        else
                        {
                            Toast.makeText(BookMyServiceActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServiceList> call, Throwable t) {
                        Toast.makeText(BookMyServiceActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });*/
                positions.clear();
                btnDDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (String ss : positions)
                        {
                            if(BookinglistString == ""){
                                BookinglistString += ss;
                            }else{
                                BookinglistString += "," + ss;
                            }
                        }
                        txtService.setText(BookinglistString);
                        dial.dismiss();
                    }
                });

                dial.show();
            }
        });

        llDate = (LinearLayout)findViewById(R.id.llDate);
        txtDate = (TextView) findViewById(R.id.txtDate);

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        txtDate.setText(sdfDate.format(new Date()));

        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(BookMyServiceActivity.this,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;

                        if(selectedmonth < 10 && selectedday < 10)
                        {
                            txtDate.setText("0"+selectedday + "-" + "0"+selectedmonth + "-" + selectedyear);
                        }
                        else if(selectedmonth < 10)
                        {
                            txtDate.setText(selectedday + "-" + "0"+selectedmonth + "-" + selectedyear);
                        }
                        else if(selectedday < 10)
                        {
                            txtDate.setText("0"+selectedday + "-" + selectedmonth + "-" + selectedyear);
                        }
                        else
                        {
                            txtDate.setText(selectedday + "-" + selectedmonth + "-" + selectedyear);
                        }

                        String booking_date = txtDate.getText().toString();
                        TimeslotListArray.clear();
                        TimeslotIdArray.clear();
                        TimeslotArray.clear();
                        AvailableTimeslot(booking_date,service_opt);

                    }
                }, mYear, mMonth, mDay);

                mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();

            }

        });

        String booking_date = txtDate.getText().toString();
        TimeslotListArray.clear();
        TimeslotIdArray.clear();
        TimeslotArray.clear();
        AvailableTimeslot(booking_date,service_opt);

        spTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int po = spTimeSlot.getSelectedItemPosition();
                TimeSlotId = TimeslotIdArray.get(po);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbCondition = (CheckBox)findViewById(R.id.cbCondition);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cbCondition.isChecked())
                {
                    final String booking_name = txtName.getText().toString();
                    final String booking_email = txtEmail.getText().toString();
                    final String booking_phone = txtMobile.getText().toString();
                    final String booking_address = txtAddress.getText().toString();
                    final String booking_service_name = BookinglistString;
                    final String booking_date = txtDate.getText().toString();
                    //final String booking_vinno = txtVINNumber.getText().toString();
                    final String booking_make = txtMake.getText().toString();
                    final String booking_model = txtModel.getText().toString();
                    final String booking_msgyear = txtMsgYear.getText().toString();
                    final String booking_enginetype = txtEngineType.getText().toString();
                    final String booking_vanplateno = txtVanPlateNo.getText().toString();
                    final String booking_comment = txtComment.getText().toString();
                    final String booking_t_id = TimeSlotId;
                    final String booking_status = "Pending";
                    final String booking_service_opt = service_opt;

                    SimpleDateFormat sdfTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String booking_time = sdfTime.format(new Date());

                    final String booking_t_name = spTimeSlot.getSelectedItem().toString();

                    final ProgressDialog dialog = new ProgressDialog(BookMyServiceActivity.this);
                    dialog.setMessage("Loading...");
                    dialog.setCancelable(true);
                    dialog.show();

                    Call<Message> AddBookingCall = productDataService.getAddBookingData(booking_name,booking_email,booking_phone,booking_service_opt,booking_address,booking_service_name,booking_date,booking_make,booking_model,booking_msgyear,booking_enginetype,booking_vanplateno,booking_comment,booking_t_id,booking_status,booking_time);
                    AddBookingCall.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            dialog.dismiss();
                            String status = response.body().getStatus();
                            String message = response.body().getMessage();
                            if (status.equals("1"))
                            {
                                Call<Message> BookingEmailSendCall = productDataService.getBookingEmailSendData(booking_email,booking_name,booking_phone,booking_address,booking_service_name,booking_date,booking_make,booking_model,booking_msgyear,booking_enginetype,booking_vanplateno,booking_comment,booking_t_name);
                                BookingEmailSendCall.enqueue(new Callback<Message>() {
                                    @Override
                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                        displayFirebaseRegId(booking_email);
                                        Intent i = new Intent(BookMyServiceActivity.this,HomeActivity.class);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onFailure(Call<Message> call, Throwable t) {
                                        Toast.makeText(BookMyServiceActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(BookMyServiceActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(BookMyServiceActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(BookMyServiceActivity.this, "Please Accept terms and conditions...", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void displayFirebaseRegId(String email) {
        String regId = Config.getToken(BookMyServiceActivity.this);
        if (!TextUtils.isEmpty(regId)) {
            updateToken(regId,email);
        } else {
            Toast.makeText(BookMyServiceActivity.this, "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateToken(String token, String email) {
        String getWifiMac = Config.getWifiMacAddress();
        Log.e(TAGs, "onResponse->" + getWifiMac + " - " + token);

        Call<TokenUpdateResponse> call = productDataService.getUpdateToken_email("android", email, getWifiMac, token);
        call.enqueue(new Callback<TokenUpdateResponse>() {
            @Override
            public void onResponse(Call<TokenUpdateResponse> call, Response<TokenUpdateResponse> response) {
                Log.e(TAGs, "onResponse->" + response.body().getStatus());
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        Config.setUploadToken(BookMyServiceActivity.this, true);
                    } else {
                        Toast.makeText(BookMyServiceActivity.this, "Token Not Updated..!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BookMyServiceActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenUpdateResponse> call, Throwable t) {
                Log.e(TAGs, "onFailure-> " + t.toString());
                Toast.makeText(BookMyServiceActivity.this, "Something Wrong!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AvailableTimeslot(String booking_date, String service_opt) {
        Call<TimeslotList> TimeslotListCall = productDataService.getTimeslotData(booking_date,service_opt);
        TimeslotListCall.enqueue(new Callback<TimeslotList>() {
            @Override
            public void onResponse(Call<TimeslotList> call, Response<TimeslotList> response) {
                String status = response.body().getStatus();
                String message = response.body().getMessage();
                if(status.equals("1"))
                {
                    TimeslotListArray = response.body().getTimeslotList();
                    for (int i=0;i<TimeslotListArray.size();i++)
                    {
                        String t_Id = TimeslotListArray.get(i).getT_id();
                        TimeslotIdArray.add(t_Id);
                        String timeslot = TimeslotListArray.get(i).getT_timeslot();
                        TimeslotArray.add(timeslot);
                    }

                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, TimeslotArray);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, TimeslotArray);
                    spTimeSlot.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(BookMyServiceActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TimeslotList> call, Throwable t) {
                Toast.makeText(BookMyServiceActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_aboutus)
        {
            Intent i = new Intent(getApplicationContext(),AboutUsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_ourservice)
        {
            Intent i = new Intent(getApplicationContext(),OurServiceActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_managebooking)
        {
            Intent i = new Intent(getApplicationContext(),ManageBookingActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_contactus)
        {
            Intent i = new Intent(getApplicationContext(),ContactUsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_faq)
        {
            Intent i = new Intent(getApplicationContext(),FAQActivity.class);
            startActivity(i);
        }
        /*else if (id == R.id.nav_admin)
        {
            Intent i = new Intent(getApplicationContext(),AdminLoginActivity.class);
            startActivity(i);
        }*/
        else if (id == R.id.nav_tc)
        {
            Intent i = new Intent(getApplicationContext(),TermsConditionsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_rate)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.bookmyservice"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.bookmyservice"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="https://play.google.com/store/apps/details?id=com.iteration.bookmyservice";
            i.putExtra(Intent.EXTRA_SUBJECT,body);
            i.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(i,"Share using"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private boolean MyStartActivity(Intent i) {
        try
        {
            startActivity(i);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

    private class ServiceMultiListAdapter extends RecyclerView.Adapter<ServiceMultiListAdapter.ViewHolder>{

        Context context;
        ArrayList<Service> serviceListArray;

        public ServiceMultiListAdapter(Context context, ArrayList<Service> serviceListArray) {
            this.context = context;
            this.serviceListArray = serviceListArray;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.service_multi_list, viewGroup, false);

            ServiceMultiListAdapter.ViewHolder viewHolder = new ServiceMultiListAdapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ServiceMultiListAdapter.ViewHolder viewHolder, int position) {

            String Service_id = serviceListArray.get(position).getService_id();
            final String Service_name = serviceListArray.get(position).getService_name();

            viewHolder.txtSMlName.setText(Service_name);

            if (position == 0)
            {
                viewHolder.chMService.setChecked(true);
                positions.add(Service_name);
            }

            viewHolder.chMService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                    {
                        positions.add(Service_name);
                    }
                    else
                    {
                        positions.remove(Service_name);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return serviceListArray.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txtSMlName;
            CheckBox chMService;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                txtSMlName = (TextView)itemView.findViewById(R.id.txtSMlName);
                chMService = (CheckBox) itemView.findViewById(R.id.chMService);

            }
        }
    }
}
