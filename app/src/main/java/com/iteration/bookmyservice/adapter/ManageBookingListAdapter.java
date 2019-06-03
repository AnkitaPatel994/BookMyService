package com.iteration.bookmyservice.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ScrollingTabContainerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.activity.BookMyServiceActivity;
import com.iteration.bookmyservice.activity.ManageBookingActivity;
import com.iteration.bookmyservice.activity.OurServiceActivity;
import com.iteration.bookmyservice.model.Booking;
import com.iteration.bookmyservice.model.BookingList;
import com.iteration.bookmyservice.model.Message;
import com.iteration.bookmyservice.model.Service;
import com.iteration.bookmyservice.model.ServiceList;
import com.iteration.bookmyservice.model.Timeslot;
import com.iteration.bookmyservice.model.TimeslotList;
import com.iteration.bookmyservice.network.GetProductDataService;
import com.iteration.bookmyservice.network.RetrofitInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBookingListAdapter extends RecyclerView.Adapter<ManageBookingListAdapter.ViewHolder> {

    Context context;
    ArrayList<Booking> bookingListArray;
    GetProductDataService productDataService;
    ArrayList<Service> ServiceDListArray = new ArrayList<>();
    ArrayList<Service> ServiceListArray = new ArrayList<>();
    ArrayList<String> ServiceDIdArray = new ArrayList<>();
    ArrayList<String> ServiceDNameArray = new ArrayList<>();
    ArrayList<Timeslot> TimeslotDListArray = new ArrayList<>();
    ArrayList<String> TimeslotDIdArray = new ArrayList<>();
    ArrayList<String> TimeslotDArray = new ArrayList<>();
    ArrayList<Timeslot> BookingTimeslotDListArray = new ArrayList<>();
    ArrayList<String> BookingTimeslotDIdArray = new ArrayList<>();
    ArrayList<String> BookingTimeslotDArray = new ArrayList<>();
    ArrayList<String> positions = new ArrayList<String>();
    String TimeSlotId;
    String BookinglistString="";

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

        productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        final String booking_id = bookingListArray.get(position).getBooking_id();
        final String booking_name = bookingListArray.get(position).getBooking_name();
        String booking_email = bookingListArray.get(position).getBooking_email();
        String booking_phone = bookingListArray.get(position).getBooking_phone();
        final String booking_service_opt = bookingListArray.get(position).getBooking_service_opt();
        String booking_address = bookingListArray.get(position).getBooking_address();
        final String booking_service_name = bookingListArray.get(position).getBooking_service_name();
        final String booking_date = bookingListArray.get(position).getBooking_date();
        String booking_vinno = bookingListArray.get(position).getBooking_vinno();
        final String booking_make = bookingListArray.get(position).getBooking_make();
        final String booking_model = bookingListArray.get(position).getBooking_model();
        final String booking_msgyear = bookingListArray.get(position).getBooking_msgyear();
        final String booking_enginetype = bookingListArray.get(position).getBooking_enginetype();
        final String booking_vanplateno = bookingListArray.get(position).getBooking_vanplateno();
        String booking_comment = bookingListArray.get(position).getBooking_comment();
        final int booking_t_id = Integer.parseInt(bookingListArray.get(position).getBooking_t_id());
        String t_timeslot = bookingListArray.get(position).getT_timeslot();
        String Booking_time = bookingListArray.get(position).getBooking_time();

        Date oldDate = new Date();
        Date Date = new Date();
        Date newDate = new Date(oldDate.getTime() + TimeUnit.HOURS.toMillis(24)); // Adds 2 hours

        Log.d("Date",Date+"=="+oldDate+"=="+newDate);

        if (Date.equals(newDate))
        {
            viewHolder.llEdit.setVisibility(View.GONE);
            viewHolder.llDelete.setVisibility(View.GONE);
        }

        viewHolder.txtMBName.setText(booking_name);
        viewHolder.txtMBDate.setText(booking_date);
        viewHolder.txtMBServiceName.setText(booking_service_name);
        viewHolder.txtMBTimeslot.setText(t_timeslot);
        viewHolder.txtMBAddress.setText(booking_address);

        viewHolder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.delete_booking_dialog);
                dialog.setCancelable(true);

                TextView txtBtnCancel = (TextView)dialog.findViewById(R.id.txtBtnCancel);
                TextView txtBtnRemove = (TextView)dialog.findViewById(R.id.txtBtnRemove);
                LinearLayout llDeleteDialog = (LinearLayout)dialog.findViewById(R.id.llDeleteDialog);
                llDeleteDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                txtBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                txtBtnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final ProgressDialog d = new ProgressDialog(context);
                        d.setMessage("Loading...");
                        d.setCancelable(true);
                        d.show();

                        Call<Message> DeleteBookingCall = productDataService.getDeleteBookingData(booking_id);
                        DeleteBookingCall.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                dialog.dismiss();
                                d.dismiss();
                                String status = response.body().getStatus();
                                String message = response.body().getMessage();
                                if (status.equals("1"))
                                {
                                    Intent i = new Intent(context,ManageBookingActivity.class);
                                    context.startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
                                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                dialog.show();
            }
        });

        viewHolder.llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.edit_booking_dialog);
                dialog.setCancelable(true);

                ServiceDNameArray.clear();
                TimeslotDArray.clear();

                LinearLayout llEditDialog = (LinearLayout)dialog.findViewById(R.id.llEditDialog);
                llEditDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                TextView txtBDName = (TextView)dialog.findViewById(R.id.txtBDName);
                txtBDName.setText(booking_name);

                LinearLayout llDServiceName = (LinearLayout)dialog.findViewById(R.id.llDServiceName);
                final TextView txtDServiceName = (TextView)dialog.findViewById(R.id.txtDServiceName);
                txtDServiceName.setText(booking_service_name);
                llDServiceName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dial = new Dialog(context,android.R.style.Theme_Light_NoTitleBar);
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

                        RecyclerView.LayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                        rvDServiceList.setLayoutManager(manager);

                        Call<ServiceList> ServiceListCall = productDataService.getServiceData();
                        ServiceListCall.enqueue(new Callback<ServiceList>() {
                            @Override
                            public void onResponse(Call<ServiceList> call, Response<ServiceList> response) {
                                String status = response.body().getStatus();
                                String message = response.body().getMessage();
                                if(status.equals("1"))
                                {
                                    ServiceListArray = response.body().getServiceList();
                                    ServiceMultiListAdapter serviceMultiListAdapter = new ServiceMultiListAdapter(context, ServiceListArray);
                                    rvDServiceList.setAdapter(serviceMultiListAdapter);

                                }
                                else
                                {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ServiceList> call, Throwable t) {
                                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                            }
                        });
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
                                txtDServiceName.setText(BookinglistString);
                                dial.dismiss();
                            }
                        });

                        dial.show();
                    }
                });

                final Spinner spDTimeSlot = (Spinner)dialog.findViewById(R.id.spDTimeSlot);
                TextView txtBtnDCancel = (TextView)dialog.findViewById(R.id.txtBtnDCancel);
                TextView txtBtnDUpdate = (TextView)dialog.findViewById(R.id.txtBtnDUpdate);

                txtBtnDCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                /*Call<ServiceList> ServiceListCall = productDataService.getServiceData();
                ServiceListCall.enqueue(new Callback<ServiceList>() {
                    @Override
                    public void onResponse(Call<ServiceList> call, Response<ServiceList> response) {
                        String status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if(status.equals("1"))
                        {
                            ServiceDListArray = response.body().getServiceList();
                            for (int i=0;i<ServiceDListArray.size();i++)
                            {
                                String service_name = ServiceDListArray.get(i).getService_name();
                                ServiceDNameArray.add(service_name);
                                String service_id = ServiceDListArray.get(i).getService_id();
                                ServiceDIdArray.add(service_id);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ServiceDNameArray);
                            spDService.setAdapter(adapter);
                            //spDService.setSelection(booking_service_id-1);
                        }
                        else
                        {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServiceList> call, Throwable t) {
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });*/

                Call<TimeslotList> BookingTimeslotListCall = productDataService.getBookingTimeslotData(booking_date,booking_service_opt);
                BookingTimeslotListCall.enqueue(new Callback<TimeslotList>() {
                    @Override
                    public void onResponse(Call<TimeslotList> call, Response<TimeslotList> response) {
                        String status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if(status.equals("1"))
                        {
                            BookingTimeslotDListArray = response.body().getTimeslotList();
                            for (int i=0;i<BookingTimeslotDListArray.size();i++)
                            {
                                String b_t_Id = BookingTimeslotDListArray.get(i).getT_id();
                                BookingTimeslotDIdArray.add(b_t_Id);
                                String b_timeslot = BookingTimeslotDListArray.get(i).getT_timeslot();
                                BookingTimeslotDArray.add(b_timeslot);
                            }

                        }
                        else
                        {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeslotList> call, Throwable t) {
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

                Call<TimeslotList> TimeslotListCall = productDataService.getTimeslotData();
                TimeslotListCall.enqueue(new Callback<TimeslotList>() {
                    @Override
                    public void onResponse(Call<TimeslotList> call, Response<TimeslotList> response) {
                        String status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if(status.equals("1"))
                        {
                            TimeslotDListArray = response.body().getTimeslotList();
                            for (int i=0;i<TimeslotDListArray.size();i++)
                            {
                                String t_Id = TimeslotDListArray.get(i).getT_id();
                                TimeslotDIdArray.add(t_Id);
                                String timeslot = TimeslotDListArray.get(i).getT_timeslot();
                                TimeslotDArray.add(timeslot);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, TimeslotDArray);
                            spDTimeSlot.setAdapter(adapter);
                            spDTimeSlot.setSelection(booking_t_id-1);

                        }
                        else
                        {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeslotList> call, Throwable t) {
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

                spDTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int po = spDTimeSlot.getSelectedItemPosition();
                        TimeSlotId = TimeslotDIdArray.get(po);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                txtBtnDUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ProgressDialog d = new ProgressDialog(context);
                        d.setMessage("Loading...");
                        d.setCancelable(true);
                        d.show();

                        Call<Message> EditBookingCall = productDataService.getEditBookingData(booking_id,BookinglistString,TimeSlotId);
                        EditBookingCall.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                dialog.dismiss();
                                d.dismiss();
                                String status = response.body().getStatus();
                                String message = response.body().getMessage();
                                if (status.equals("1"))
                                {
                                    Intent i = new Intent(context,ManageBookingActivity.class);
                                    context.startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
                                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMBName,txtMBDate,txtMBServiceName,txtMBTimeslot,txtMBAddress;
        LinearLayout llDelete,llEdit;

        public ViewHolder(View itemView) {
            super(itemView);

            txtMBName = (TextView)itemView.findViewById(R.id.txtMBName);
            txtMBDate = (TextView)itemView.findViewById(R.id.txtMBDate);
            txtMBServiceName = (TextView)itemView.findViewById(R.id.txtMBServiceName);
            txtMBTimeslot = (TextView)itemView.findViewById(R.id.txtMBTimeslot);
            txtMBAddress = (TextView)itemView.findViewById(R.id.txtMBAddress);
            llDelete = (LinearLayout) itemView.findViewById(R.id.llDelete);
            llEdit = (LinearLayout) itemView.findViewById(R.id.llEdit);
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

            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

            String Service_id = serviceListArray.get(position).getService_id();
            final String Service_name = serviceListArray.get(position).getService_name();

            viewHolder.txtSMlName.setText(Service_name);

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
