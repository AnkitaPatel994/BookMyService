package com.iteration.bookmyservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.activity.HomeActivity;
import com.iteration.bookmyservice.model.Service;
import com.iteration.bookmyservice.model.Slider;
import com.iteration.bookmyservice.network.RetrofitInstance;
import com.jackandphantom.circularimageview.RoundedImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderListAdapter extends RecyclerView.Adapter<SliderListAdapter.ViewHolder> {

    Context context;
    ArrayList<Slider> sliderListArray;

    public SliderListAdapter(Context context, ArrayList<Slider> sliderListArray) {
        this.context = context;
        this.sliderListArray = sliderListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        String Banner_id = sliderListArray.get(position).getId();
        String Banner_img = sliderListArray.get(position).getBanner();

        Picasso.with(context).load(RetrofitInstance.BASE_URL +Banner_img).into(viewHolder.ivBannerImg);

    }

    @Override
    public int getItemCount() {
        return sliderListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImage ivBannerImg;

        public ViewHolder(View itemView) {
            super(itemView);

            ivBannerImg = (RoundedImage)itemView.findViewById(R.id.ivBannerImg);

        }
    }
}
