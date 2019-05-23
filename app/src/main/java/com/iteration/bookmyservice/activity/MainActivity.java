package com.iteration.bookmyservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.network.SessionAdminManager;

public class MainActivity extends AppCompatActivity {

    SessionAdminManager session;
    int flag = 0;
    LinearLayout lnSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionAdminManager(getApplicationContext());
        flag = session.checkLogin();

        Thread background = new Thread()
        {
            public void run()
            {
                try {
                    sleep(5*1000);

                    if (flag == 1)
                    {
                        Intent i = new Intent(getApplicationContext(), AdminBookingActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);
                        finish();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();

    }
}
