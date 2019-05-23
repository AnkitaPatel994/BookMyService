package com.iteration.bookmyservice.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.Calendar;

import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.TextView;
import android.widget.Toast;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.network.AdminPager;
import com.iteration.bookmyservice.network.SessionAdminManager;

public class AdminBookingActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager vpPager;
    TextView txtBDate;
    public static String tvBDate;
    SessionAdminManager session;
    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionAdminManager(getApplicationContext());
        session.checkLogin();

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        vpPager = (ViewPager) findViewById(R.id.vpPager);

        txtBDate = (TextView) findViewById(R.id.txtBDate);

        setupViewPager(vpPager);

        tabLayout.setupWithViewPager(vpPager);

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        txtBDate.setText(sdfDate.format(new Date()));
        tvBDate = txtBDate.getText().toString();

        txtBDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AdminBookingActivity.this,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;

                        if(selectedmonth < 10 && selectedday < 10)
                        {
                            txtBDate.setText("0"+selectedday + "-" + "0"+selectedmonth + "-" + selectedyear);

                        }
                        else if(selectedmonth < 10)
                        {
                            txtBDate.setText(selectedday + "-" + "0"+selectedmonth + "-" + selectedyear);

                        }
                        else if(selectedday < 10)
                        {
                            txtBDate.setText("0"+selectedday + "-" + selectedmonth + "-" + selectedyear);

                        }
                        else
                        {
                            txtBDate.setText(selectedday + "-" + selectedmonth + "-" + selectedyear);

                        }
                        tvBDate = txtBDate.getText().toString();
                        setupViewPager(vpPager);
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();


            }
        });

    }

    private void setupViewPager(ViewPager vpPager) {
        AdminPager adapter = new AdminPager(getSupportFragmentManager());

        adapter.addFrag(new YourPlaceFragment(),"Your Place");
        adapter.addFrag(new OurPlaceFragment(),"Our Place");

        vpPager.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.adminbooking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_logout)
        {
            session.logoutAdmin();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
