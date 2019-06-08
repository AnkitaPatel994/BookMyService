package com.iteration.bookmyservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.activity.OurServiceActivity;
import com.iteration.bookmyservice.model.Service;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {

    Context context;
    ArrayList<Service> serviceListArray;

    public ServiceListAdapter(Context context, ArrayList<Service> serviceListArray) {
        this.context = context;
        this.serviceListArray = serviceListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        String Service_id = serviceListArray.get(position).getService_id();
        String Service_name = serviceListArray.get(position).getService_name();
        String Service_price = serviceListArray.get(position).getService_price();
        String Service_ex_period = serviceListArray.get(position).getService_ex_period();

        viewHolder.txtServiceName.setText(Service_name);
        viewHolder.txtServicePrice.setText("Starts from $ "+Service_price);


    }

    @Override
    public int getItemCount() {
        return serviceListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtServiceName,txtServicePrice;

        public ViewHolder(View itemView) {
            super(itemView);

            txtServiceName = (TextView)itemView.findViewById(R.id.txtServiceName);
            txtServicePrice = (TextView)itemView.findViewById(R.id.txtServicePrice);
        }
    }
}
