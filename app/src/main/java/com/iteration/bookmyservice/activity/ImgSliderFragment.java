package com.iteration.bookmyservice.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.network.RetrofitInstance;
import com.squareup.picasso.Picasso;


public class ImgSliderFragment extends Fragment {

    private String title;

    public ImgSliderFragment() {
        // Required empty public constructor
    }

    public static ImgSliderFragment newInstance(int page, String title) {
        ImgSliderFragment fragment = new ImgSliderFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_img_slider, container, false);
        ImageView ivImgSlider = (ImageView)view.findViewById(R.id.ivBannerImg);
        Picasso.with(getActivity()).load(RetrofitInstance.BASE_URL +title).into(ivImgSlider);
        return view;

    }

}
