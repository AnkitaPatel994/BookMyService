package com.iteration.bookmyservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.activity.ManageBookingActivity;
import com.iteration.bookmyservice.model.Booking;
import com.iteration.bookmyservice.model.Service;

import java.util.ArrayList;

public class ManageBookingListAdapter extends RecyclerView.Adapter<ManageBookingListAdapter.ViewHolder> {

    Context context;
    ArrayList<Booking> bookingListArray;

    public ManageBookingListAdapter(Context context, ArrayList<Booking> bookingListArray) {
        this.context = context;
        this.bookingListArray = bookingListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_booking_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        String booking_id = bookingListArray.get(position).getBooking_id();
        String booking_name = bookingListArray.get(position).getBooking_name();
        String booking_email = bookingListArray.get(position).getBooking_email();
        String booking_phone = bookingListArray.get(position).getBooking_phone();
        String booking_address = bookingListArray.get(position).getBooking_address();
        String booking_service_id = bookingListArray.get(position).getBooking_service_id();
        String Service_name = bookingListArray.get(position).getService_name();
        String booking_date = bookingListArray.get(position).getBooking_date();
        String booking_vinno = bookingListArray.get(position).getBooking_vinno();
        String booking_comment = bookingListArray.get(position).getBooking_comment();
        String booking_t_id = bookingListArray.get(position).getBooking_t_id();
        String t_timeslot = bookingListArray.get(position).getT_timeslot();

        viewHolder.txtMBName.setText(booking_name);
        viewHolder.txtMBEmail.setText(booking_email);
        viewHolder.txtMBServiceName.setText(Service_name);
        viewHolder.txtMBTimeslot.setText(t_timeslot);


    }

    @Override
    public int getItemCount() {
        return bookingListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMBName,txtMBEmail,txtMBServiceName,txtMBTimeslot;

        public ViewHolder(View itemView) {
            super(itemView);

            txtMBName = (TextView)itemView.findViewById(R.id.txtMBName);
            txtMBEmail = (TextView)itemView.findViewById(R.id.txtMBEmail);
            txtMBServiceName = (TextView)itemView.findViewById(R.id.txtMBServiceName);
            txtMBTimeslot = (TextView)itemView.findViewById(R.id.txtMBTimeslot);
        }
    }
}
