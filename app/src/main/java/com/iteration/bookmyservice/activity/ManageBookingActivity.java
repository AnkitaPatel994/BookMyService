package com.iteration.bookmyservice.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.adapter.ManageBookingListAdapter;
import com.iteration.bookmyservice.adapter.ServiceListAdapter;
import com.iteration.bookmyservice.model.Booking;
import com.iteration.bookmyservice.model.BookingList;
import com.iteration.bookmyservice.model.ServiceList;
import com.iteration.bookmyservice.network.GetProductDataService;
import com.iteration.bookmyservice.network.RetrofitInstance;
import com.iteration.bookmyservice.network.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBookingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager session;
    RecyclerView rvManageBooking;
    ArrayList<Booking> BookingListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        session = new SessionManager(ManageBookingActivity.this);

        HashMap<String,String> user = session.getUserDetails();
        String user_email = user.get(SessionManager.user_email);

        GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        rvManageBooking = (RecyclerView)findViewById(R.id.rvManageBooking);
        rvManageBooking.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rvManageBooking.setLayoutManager(manager);

        Call<BookingList> BookingListCall = productDataService.getManageBookingData(user_email);
        BookingListCall.enqueue(new Callback<BookingList>() {
            @Override
            public void onResponse(Call<BookingList> call, Response<BookingList> response) {
                String status = response.body().getStatus();
                String message = response.body().getMessage();
                if (status.equals("1"))
                {
                    BookingListArray = response.body().getBookingList();
                    ManageBookingListAdapter manageBookingListAdapter = new ManageBookingListAdapter(ManageBookingActivity.this, BookingListArray);
                    rvManageBooking.setAdapter(manageBookingListAdapter);
                }
                else
                {
                    Toast.makeText(ManageBookingActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingList> call, Throwable t) {
                Toast.makeText(ManageBookingActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_aboutus)
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
