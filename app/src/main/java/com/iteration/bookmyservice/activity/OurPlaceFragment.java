package com.iteration.bookmyservice.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iteration.bookmyservice.R;

public class OurPlaceFragment extends Fragment {

    public OurPlaceFragment() {
        // Required empty public constructor
    }

    public static OurPlaceFragment newInstance(String param1, String param2) {
        OurPlaceFragment fragment = new OurPlaceFragment();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);
        args.putString("ARG_PARAM2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_our_place, container, false);

        return view;
    }

}
