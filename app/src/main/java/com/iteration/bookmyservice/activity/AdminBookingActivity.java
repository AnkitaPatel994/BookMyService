package com.iteration.bookmyservice.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.network.AdminPager;

public class AdminBookingActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        vpPager = (ViewPager) findViewById(R.id.vpPager);

        setupViewPager(vpPager);

        tabLayout.setupWithViewPager(vpPager);

    }

    private void setupViewPager(ViewPager vpPager) {
        AdminPager adapter = new AdminPager(getSupportFragmentManager());

        adapter.addFrag(new YourPlaceFragment(),"Your Place");
        adapter.addFrag(new OurPlaceFragment(),"Our Place");

        vpPager.setAdapter(adapter);

    }

}
