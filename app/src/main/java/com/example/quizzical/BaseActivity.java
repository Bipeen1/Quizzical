package com.example.quizzical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quizzical.util.PreferenceHelper;

public abstract class BaseActivity extends AppCompatActivity {
    protected static PreferenceHelper baseActivityPreferenceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        baseActivityPreferenceHelper = PreferenceHelper.getInstance(this);
    }

    protected abstract void init();
}