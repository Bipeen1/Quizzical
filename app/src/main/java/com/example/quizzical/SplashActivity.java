package com.example.quizzical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.example.quizzical.util.Constants;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLogin =baseActivityPreferenceHelper.getBoolean(Constants.PREF_LOGIN,false);
                Log.d(TAG, "run: boolean value" + isLogin);
                if(isLogin)
                {
                    Intent intent=new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);


    }

    @Override
    protected void init() {

    }


}
