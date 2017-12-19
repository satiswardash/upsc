package com.kortain.upsc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    boolean auth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!auth) {
            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);
    }
}
