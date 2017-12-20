package com.kortain.upsc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kortain.upsc.models.ApplicationData;

public class MainActivity extends AppCompatActivity {

    boolean auth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, App.user.getUid(), Toast.LENGTH_LONG).show();


        ApplicationData data = SampleDataProvider.getInstance().generateSampleData();
        Gson gson = new Gson();
        Log.i("ApplicationJSONData", "onCreate: "+gson.toJson(data));

    }
}
