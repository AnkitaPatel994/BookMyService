package com.iteration.bookmyservice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.model.Message;
import com.iteration.bookmyservice.network.GetProductDataService;
import com.iteration.bookmyservice.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConformBookingActivity extends AppCompatActivity {

    TextView txtCBookingName,txtCBookingEmail,txtCBookingPhoneNo,txtCBookingAddress,txtCServiceName,txtCBookingDate,txtCBookingVINno,txtCComment,txtCTimeSlott;
    Button btnDone,btnCancel;
    EditText txtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_booking);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtCBookingName = (TextView)findViewById(R.id.txtCBookingName);
        txtCBookingEmail = (TextView)findViewById(R.id.txtCBookingEmail);
        txtCBookingPhoneNo = (TextView)findViewById(R.id.txtCBookingPhoneNo);
        txtCBookingAddress = (TextView)findViewById(R.id.txtCBookingAddress);
        txtCServiceName = (TextView)findViewById(R.id.txtCServiceName);
        txtCBookingDate = (TextView)findViewById(R.id.txtCBookingDate);
        txtCBookingVINno = (TextView)findViewById(R.id.txtCBookingVINno);
        txtCComment = (TextView)findViewById(R.id.txtCComment);
        txtCTimeSlott = (TextView)findViewById(R.id.txtCTimeSlott);
        txtPrice = (EditText) findViewById(R.id.txtPrice);

        btnDone = (Button)findViewById(R.id.btnDone);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        final GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        final String booking_id = getIntent().getExtras().getString("booking_id");
        String booking_name = getIntent().getExtras().getString("booking_name");
        String booking_email = getIntent().getExtras().getString("booking_email");
        String booking_phone = getIntent().getExtras().getString("booking_phone");
        String booking_address = getIntent().getExtras().getString("booking_address");
        String Service_name = getIntent().getExtras().getString("Service_name");
        String booking_date = getIntent().getExtras().getString("booking_date");
        String booking_vinno = getIntent().getExtras().getString("booking_vinno");
        String booking_comment = getIntent().getExtras().getString("booking_comment");
        String t_timeslot = getIntent().getExtras().getString("t_timeslot");

        txtCBookingName.setText(booking_name);
        txtCBookingEmail.setText(booking_email);
        txtCBookingPhoneNo.setText(booking_phone);
        txtCBookingAddress.setText(booking_address);
        txtCServiceName.setText(Service_name);
        txtCBookingDate.setText(booking_date);
        txtCBookingVINno.setText(booking_vinno);
        txtCComment.setText(booking_comment);
        txtCTimeSlott.setText(t_timeslot);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConformBookingActivity.super.onBackPressed();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String booking_price = txtPrice.getText().toString();

                final ProgressDialog d = new ProgressDialog(ConformBookingActivity.this);
                d.setMessage("Loading...");
                d.setCancelable(true);
                d.show();

                Call<Message> ConformBookingCall = productDataService.getConformBookingData(booking_id,"Conform",booking_price);
                ConformBookingCall.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        d.dismiss();
                        String status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if (status.equals("1"))
                        {
                            Intent i = new Intent(ConformBookingActivity.this, AdminBookingActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(ConformBookingActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(ConformBookingActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
