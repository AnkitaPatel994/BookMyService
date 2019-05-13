package com.iteration.bookmyservice.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.model.Slider;
import com.iteration.bookmyservice.model.SliderList;
import com.iteration.bookmyservice.network.GetProductDataService;
import com.iteration.bookmyservice.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SliderLayout slBannerSlider;
    ArrayList<Slider> sliderListArray = new ArrayList<>();
    ArrayList<String> sliderImgArray = new ArrayList<>();
    LinearLayout llAboutUs,llOurService,llBookMyService,llFAQ,llContactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        slBannerSlider = (SliderLayout)findViewById(R.id.slBannerSlider);

        Call<SliderList> sliderListCall = productDataService.getSliderData();

        sliderListCall.enqueue(new Callback<SliderList>() {
            @Override
            public void onResponse(Call<SliderList> call, Response<SliderList> response) {
                String status = response.body().getStatus();
                String message = response.body().getMessage();
                if(status.equals("1"))
                {
                    sliderListArray = response.body().getSliderList();
                    for (int i=0;i<sliderListArray.size();i++)
                    {
                        String banner = sliderListArray.get(i).getBanner();
                        String banner_path = RetrofitInstance.BASE_URL +banner;
                        Log.d("banner_path",banner_path);
                        sliderImgArray.add(banner_path);
                    }

                    for (String name : sliderImgArray) {
                        DefaultSliderView textSliderView = new DefaultSliderView(HomeActivity.this);
                        // initialize a SliderLayout
                        textSliderView
                                .image(String.valueOf(name))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(BaseSliderView slider) {
                                        slBannerSlider.startAutoCycle();
                                    }
                                });

                        slBannerSlider.addSlider(textSliderView);
                    }
                    slBannerSlider.setCustomAnimation(new DescriptionAnimation());
                    slBannerSlider.setDuration(5000);

                }
                else
                {
                    Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SliderList> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        llAboutUs = (LinearLayout)findViewById(R.id.llAboutUs);
        llAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,AboutUsActivity.class);
                startActivity(i);
            }
        });

        llOurService = (LinearLayout)findViewById(R.id.llOurService);
        llOurService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,OurServiceActivity.class);
                startActivity(i);
            }
        });

        llBookMyService = (LinearLayout)findViewById(R.id.llBookMyService);
        llBookMyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,BookMyServiceActivity.class);
                startActivity(i);
            }
        });

        llFAQ = (LinearLayout)findViewById(R.id.llFAQ);
        llFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,FAQActivity.class);
                startActivity(i);
            }
        });

        llContactus = (LinearLayout)findViewById(R.id.llContactus);
        llContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ContactUsActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_aboutus)
        {
            Intent i = new Intent(getApplicationContext(),AboutUsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_ourservice)
        {
            Intent i = new Intent(getApplicationContext(),OurServiceActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_bookmyservice)
        {
            Intent i = new Intent(getApplicationContext(),BookMyServiceActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_managebooking)
        {
            Intent i = new Intent(getApplicationContext(),ManageBookingActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_contactus)
        {
            Intent i = new Intent(getApplicationContext(),ContactUsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_faq)
        {
            Intent i = new Intent(getApplicationContext(),FAQActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_admin)
        {
            Intent i = new Intent(getApplicationContext(),AdminLoginActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_rate)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.bookmyservice"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.bookmyservice"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="https://play.google.com/store/apps/details?id=com.iteration.bookmyservice";
            i.putExtra(Intent.EXTRA_SUBJECT,body);
            i.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(i,"Share using"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean MyStartActivity(Intent i) {
        try
        {
            startActivity(i);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

}
