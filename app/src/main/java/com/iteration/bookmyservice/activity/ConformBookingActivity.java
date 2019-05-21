package com.iteration.bookmyservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.iteration.bookmyservice.R;

public class ConformBookingActivity extends AppCompatActivity {

    TextView txtCBookingName,txtCBookingEmail,txtCBookingPhoneNo,txtCBookingAddress,txtCServiceName,txtCBookingDate,txtCBookingVINno,txtCComment,txtCTimeSlott;
    Button btnDone,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_booking);

        txtCBookingName = (TextView)findViewById(R.id.txtCBookingName);
        txtCBookingEmail = (TextView)findViewById(R.id.txtCBookingEmail);
        txtCBookingPhoneNo = (TextView)findViewById(R.id.txtCBookingPhoneNo);
        txtCBookingAddress = (TextView)findViewById(R.id.txtCBookingAddress);
        txtCServiceName = (TextView)findViewById(R.id.txtCServiceName);
        txtCBookingDate = (TextView)findViewById(R.id.txtCBookingDate);
        txtCBookingVINno = (TextView)findViewById(R.id.txtCBookingVINno);
        txtCComment = (TextView)findViewById(R.id.txtCComment);
        txtCTimeSlott = (TextView)findViewById(R.id.txtCTimeSlott);

        btnDone = (Button)findViewById(R.id.btnDone);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        String booking_id = getIntent().getExtras().getString("booking_id");
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


    }
}
