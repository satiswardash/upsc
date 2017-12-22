package com.kortain.upsc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.kortain.upsc.widgets.BottomNavigationBar;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationBar.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationBar.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    mTextMessage.setText("Home");
                    return true;
                case R.id.action_objective:
                    mTextMessage.setText("Objective Gallery");
                    return true;
                case R.id.action_subjective:
                    mTextMessage.setText("Subjective Gallery");
                    return true;
                case R.id.action_exam:
                    mTextMessage.setText("Exam Center");
                    return true;
                case R.id.action_menu:
                    mTextMessage.setText("Menu");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.ah_frame_title_textView);
        BottomNavigationBar navigation = (BottomNavigationBar) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Toast.makeText(this, App.user.getUid(), Toast.LENGTH_LONG).show();


        //ApplicationData data = SampleDataProvider.getInstance().generateSampleData();
        //Gson gson = new Gson();
        //Log.i("ApplicationJSONData", "onCreate: "+gson.toJson(data));
    }

}
