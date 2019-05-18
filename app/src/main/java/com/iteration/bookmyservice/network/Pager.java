package com.iteration.bookmyservice.network;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.iteration.bookmyservice.activity.HomeActivity;
import com.iteration.bookmyservice.activity.ImgSliderFragment;

import java.util.ArrayList;
import java.util.List;

public class Pager extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public Pager(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        String a= HomeActivity.sliderImgArray.get(position);
        return ImgSliderFragment.newInstance(position,a);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add("");
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}
