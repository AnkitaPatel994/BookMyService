package com.iteration.bookmyservice.activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iteration.bookmyservice.R;
import com.iteration.bookmyservice.model.TokenUpdateResponse;
import com.iteration.bookmyservice.network.Config;
import com.iteration.bookmyservice.network.GetProductDataService;
import com.iteration.bookmyservice.network.RetrofitInstance;
import com.iteration.bookmyservice.network.SessionAdminManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SessionAdminManager session;
    int flag = 0;
    LinearLayout lnSnackbar;
    private static final String TAGs = MainActivity.class.getSimpleName();
    private GetProductDataService productDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        lnSnackbar = (LinearLayout)findViewById(R.id.lnSnackbar);

        lnSnackbar.setVisibility(View.GONE);

        session = new SessionAdminManager(getApplicationContext());
        flag = session.checkLogin();

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            displayFirebaseRegId();
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
        else
        {
            lnSnackbar.setVisibility(View.VISIBLE);
            lnSnackbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }

    private void displayFirebaseRegId() {
        String regId = Config.getToken(MainActivity.this);
        if (!TextUtils.isEmpty(regId)) {
            if (!Config.uploadToken(MainActivity.this)) {
                updateToken(regId);
            }
        } else {
            Toast.makeText(MainActivity.this, "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateToken(String token) {
        String getWifiMac = Config.getWifiMacAddress();
        Log.e(TAGs, "onResponse->" + getWifiMac + " - " + token);

        Call<TokenUpdateResponse> call = productDataService.getUpdateToken("android", getWifiMac, token);
        call.enqueue(new Callback<TokenUpdateResponse>() {
            @Override
            public void onResponse(Call<TokenUpdateResponse> call, Response<TokenUpdateResponse> response) {
                Log.e(TAGs, "onResponse->" + response.body().getStatus());
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        Config.setUploadToken(MainActivity.this, true);
                    } else {
                        Toast.makeText(MainActivity.this, "Token Not Updated..!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenUpdateResponse> call, Throwable t) {
                Log.e(TAGs, "onFailure-> " + t.toString());
                Toast.makeText(MainActivity.this, "Something Wrong!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
