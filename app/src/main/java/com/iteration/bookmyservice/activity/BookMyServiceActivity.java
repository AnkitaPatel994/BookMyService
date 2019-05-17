package com.iteration.bookmyservice.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.model.Message;
import com.iteration.bookmyservice.model.MessageOTP;
import com.iteration.bookmyservice.model.Service;
import com.iteration.bookmyservice.model.ServiceList;
import com.iteration.bookmyservice.model.Timeslot;
import com.iteration.bookmyservice.model.TimeslotList;
import com.iteration.bookmyservice.network.GetProductDataService;
import com.iteration.bookmyservice.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookMyServiceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText txtName,txtEmail,txtOTP,txtMobile,txtAddress,txtVINNumber,txtComment;
    Button btnEmailSend,btnEmailVeri,btnSubmit;
    LinearLayout llOTPBox,llBox;
    RadioGroup rgServiceOpt;
    RadioButton rbYourPlace,rbOurPlace;
    Spinner spService,spTimeSlot;
    LinearLayout llDate;
    TextView txtDate;
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
    String OTP,ServiceId,TimeSlotId;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        txtName = (EditText)findViewById(R.id.txtName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtOTP = (EditText)findViewById(R.id.txtOTP);
        llOTPBox = (LinearLayout) findViewById(R.id.llOTPBox);
        llBox = (LinearLayout) findViewById(R.id.llBox);

        btnEmailSend = (Button) findViewById(R.id.btnEmailSend);
        btnEmailVeri = (Button)findViewById(R.id.btnEmailVeri);

        txtMobile = (EditText)findViewById(R.id.txtMobile);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtVINNumber = (EditText)findViewById(R.id.txtVINNumber);
        txtComment = (EditText)findViewById(R.id.txtComment);
        rgServiceOpt = (RadioGroup) findViewById(R.id.rgServiceOpt);
        rbYourPlace = (RadioButton) findViewById(R.id.rbYourPlace);
        rbOurPlace = (RadioButton) findViewById(R.id.rbOurPlace);
        rbYourPlace.setChecked(true);

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
                            txtOTP.setText(OTP);
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
                    public void onFailure(Call<MessageOTP> call, Throwable t) {
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
                }
                else
                {
                    Toast.makeText(BookMyServiceActivity.this, "Not Match OTP Code", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rgServiceOpt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rbYourPlace:
                        txtAddress.setEnabled(true);
                        txtAddress.setText("");
                        break;
                    case R.id.rbOurPlace:
                        txtAddress.setText("fgdh");
                        txtAddress.setEnabled(false);
                        break;
                }
            }
        });

        spService = (Spinner)findViewById(R.id.spService);

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
                        String service_name = ServiceListArray.get(i).getService_name();
                        ServiceNameArray.add(service_name);
                        String service_id = ServiceListArray.get(i).getService_id();
                        ServiceIdArray.add(service_id);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ServiceNameArray);
                    spService.setAdapter(adapter);
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

        spService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int po = spService.getSelectedItemPosition();
                ServiceId = ServiceIdArray.get(po);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTimeSlot = (Spinner)findViewById(R.id.spTimeSlot);

        Call<TimeslotList> TimeslotListCall = productDataService.getTimeslotData();
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, TimeslotArray);
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

        llDate = (LinearLayout)findViewById(R.id.llDate);
        txtDate = (TextView) findViewById(R.id.txtDate);
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
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();
            }
        });


        cbCondition = (CheckBox)findViewById(R.id.cbCondition);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String booking_name = txtName.getText().toString();
                String booking_email = txtEmail.getText().toString();
                String booking_phone = txtMobile.getText().toString();
                String booking_address = txtAddress.getText().toString();
                String booking_service_id = ServiceId;
                String booking_date = txtDate.getText().toString();
                String booking_vinno = txtVINNumber.getText().toString();
                String booking_comment = txtComment.getText().toString();
                String booking_t_id = TimeSlotId;
                String booking_status = "Pending";

                final ProgressDialog dialog = new ProgressDialog(BookMyServiceActivity.this);
                dialog.setMessage("Loading...");
                dialog.setCancelable(true);
                dialog.show();

                Call<Message> AddBookingCall = productDataService.getAddBookingData(booking_name,booking_email,booking_phone,booking_address,booking_service_id,booking_date,booking_vinno,booking_comment,booking_t_id,booking_status);
                AddBookingCall.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        dialog.dismiss();
                        String status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if (status.equals("1"))
                        {
                            if (cbCondition.isChecked())
                            {
                                Intent i = new Intent(BookMyServiceActivity.this,HomeActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(BookMyServiceActivity.this, "CheckBox not checked", Toast.LENGTH_SHORT).show();
                            }

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
        else if (id == R.id.nav_admin)
        {
            Intent i = new Intent(getApplicationContext(),AdminLoginActivity.class);
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
}
