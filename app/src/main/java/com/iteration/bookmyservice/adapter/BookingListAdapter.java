package com.iteration.bookmyservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.activity.ConformBookingActivity;
import com.iteration.bookmyservice.model.Booking;

import java.util.ArrayList;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder> {

    Context context;
    ArrayList<Booking> bookingListArray;

    public BookingListAdapter(Context context, ArrayList<Booking> bookingListArray) {
        this.context = context;
        this.bookingListArray = bookingListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final String booking_id = bookingListArray.get(position).getBooking_id();
        final String booking_name = bookingListArray.get(position).getBooking_name();
        final String booking_email = bookingListArray.get(position).getBooking_email();
        final String booking_phone = bookingListArray.get(position).getBooking_phone();
        final String booking_address = bookingListArray.get(position).getBooking_address();
        final String booking_service_id = bookingListArray.get(position).getBooking_service_id();
        final String Service_name = bookingListArray.get(position).getService_name();
        final String booking_date = bookingListArray.get(position).getBooking_date();
        final String booking_vinno = bookingListArray.get(position).getBooking_vinno();
        final String booking_comment = bookingListArray.get(position).getBooking_comment();
        final String booking_t_id = bookingListArray.get(position).getBooking_t_id();
        final String t_timeslot = bookingListArray.get(position).getT_timeslot();

        viewHolder.txtBookingName.setText(booking_name);
        viewHolder.txtBookingEmail.setText(booking_email);
        viewHolder.txtBookingPhoneNo.setText(booking_phone);
        viewHolder.txtBookingAddress.setText(booking_address);
        viewHolder.txtServiceName.setText(Service_name);
        viewHolder.txtBookingDate.setText(booking_date);
        viewHolder.txtBookingVINno.setText(booking_vinno);
        viewHolder.txtBTimeSlott.setText(t_timeslot);

        viewHolder.llBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ConformBookingActivity.class);
                i.putExtra("booking_id",booking_id);
                i.putExtra("booking_name",booking_name);
                i.putExtra("booking_email",booking_email);
                i.putExtra("booking_phone",booking_phone);
                i.putExtra("booking_address",booking_address);
                i.putExtra("Service_name",Service_name);
                i.putExtra("booking_date",booking_date);
                i.putExtra("booking_vinno",booking_vinno);
                i.putExtra("booking_comment",booking_comment);
                i.putExtra("t_timeslot",t_timeslot);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtBookingName,txtBookingEmail,txtBookingPhoneNo,txtBookingAddress,txtServiceName,txtBookingDate,txtBookingVINno,txtBTimeSlott;
        LinearLayout llBookingList;

        public ViewHolder(View itemView) {
            super(itemView);

            txtBookingName = (TextView)itemView.findViewById(R.id.txtBookingName);
            txtBookingEmail = (TextView)itemView.findViewById(R.id.txtBookingEmail);
            txtBookingPhoneNo = (TextView)itemView.findViewById(R.id.txtBookingPhoneNo);
            txtBookingAddress = (TextView)itemView.findViewById(R.id.txtBookingAddress);
            txtServiceName = (TextView)itemView.findViewById(R.id.txtServiceName);
            txtBookingDate = (TextView)itemView.findViewById(R.id.txtBookingDate);
            txtBookingVINno = (TextView)itemView.findViewById(R.id.txtBookingVINno);
            txtBTimeSlott = (TextView)itemView.findViewById(R.id.txtBTimeSlott);
            llBookingList = (LinearLayout) itemView.findViewById(R.id.llBookingList);
        }
    }
}
