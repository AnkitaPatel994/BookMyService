package com.iteration.bookmyservice.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

public class BookServiceActivity extends AppCompatActivity {

    EditText txtName,txtEmail,txtMobile,txtAddress,txtVINNumber,txtComment;
    RadioGroup rgServiceOpt;
    RadioButton rbYourPlace,rbOurPlace;
    Spinner spService,spTimeSlot;
    LinearLayout llDate;
    TextView txtDate;
    CheckBox cbCondition;
    Button btnSubmit;
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
    String ServiceId,TimeSlotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        txtName = (EditText)findViewById(R.id.txtName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtMobile = (EditText)findViewById(R.id.txtMobile);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtVINNumber = (EditText)findViewById(R.id.txtVINNumber);
        txtComment = (EditText)findViewById(R.id.txtComment);
        rgServiceOpt = (RadioGroup) findViewById(R.id.rgServiceOpt);
        rbYourPlace = (RadioButton) findViewById(R.id.rbYourPlace);
        rbOurPlace = (RadioButton) findViewById(R.id.rbOurPlace);
        rbYourPlace.setChecked(true);
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
                    Toast.makeText(BookServiceActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceList> call, Throwable t) {
                Toast.makeText(BookServiceActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BookServiceActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TimeslotList> call, Throwable t) {
                Toast.makeText(BookServiceActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                mDatePicker = new DatePickerDialog(BookServiceActivity.this,new DatePickerDialog.OnDateSetListener() {
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

                final ProgressDialog dialog = new ProgressDialog(BookServiceActivity.this);
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
                                Intent i = new Intent(BookServiceActivity.this,HomeActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(BookServiceActivity.this, "CheckBox not checked", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(BookServiceActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(BookServiceActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

}
