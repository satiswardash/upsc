package com.kortain.upsc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kortain.upsc.helpers.App;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.authenticate(this);
        finishAffinity();
    }
}
