package com.example.quizzical;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.quizzical.util.Constants;
import com.google.android.material.snackbar.Snackbar;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        LinearLayout ly = findViewById(R.id.linear);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isOnline()) {
                    Snackbar snackbar = Snackbar.make(ly, "No Internet Access", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    recreate();
                                }
                            });
                    snackbar.setActionTextColor(Color.parseColor("#B22222"));
                    snackbar.show();
                } else {
                    boolean isLogin = baseActivityPreferenceHelper.getBoolean(Constants.PREF_LOGIN, false);
                    Log.d(TAG, "run: boolean value" + isLogin);
                    if (isLogin) {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, 2000);
    }

    @Override
    protected void init() {

    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private boolean isNetworkAvailable() {
//
//        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo nInfo = cm.getActiveNetworkInfo();
//        return nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
//    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
