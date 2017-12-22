package com.kortain.upsc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kortain.upsc.widgets.BottomNavigationBar;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private AppBarLayout abl;
    private FrameLayout frameLayout;
    private TextView mframeTitleTextView;

    private BottomNavigationBar.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationBar.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.action_home: {

                    frameLayout.removeAllViewsInLayout();
                    if (abl.getVisibility() == View.INVISIBLE) {
                        abl.setVisibility(View.VISIBLE);
                    }
                    resetConstraintLayout();
                    mframeTitleTextView.setText(R.string.home);
                    View homeView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_home, (ViewGroup) frameLayout.getRootView(), false);
                    frameLayout.addView(homeView);
                    return true;
                }

                case R.id.action_objective: {

                    frameLayout.removeAllViewsInLayout();
                    if (abl.getVisibility() == View.INVISIBLE) {
                        abl.setVisibility(View.VISIBLE);
                    }
                    resetConstraintLayout();
                    mframeTitleTextView.setText(R.string.obj_gallery);
                    View objView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_objective, (ViewGroup) frameLayout.getRootView(), false);
                    frameLayout.addView(objView);
                    return true;
                }

                case R.id.action_subjective: {

                    frameLayout.removeAllViewsInLayout();
                    if (abl.getVisibility() == View.INVISIBLE) {
                        abl.setVisibility(View.VISIBLE);
                    }
                    resetConstraintLayout();
                    mframeTitleTextView.setText(R.string.sub_gallery);
                    View subView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_subjective, (ViewGroup) frameLayout.getRootView(), false);
                    frameLayout.addView(subView);
                    return true;
                }

                case R.id.action_exam: {

                    frameLayout.removeAllViewsInLayout();
                    if (abl.getVisibility() == View.INVISIBLE) {
                        abl.setVisibility(View.VISIBLE);
                    }
                    resetConstraintLayout();
                    mframeTitleTextView.setText(R.string.exam_center);
                    View examView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_exam, (ViewGroup) frameLayout.getRootView(), false);
                    frameLayout.addView(examView);
                    return true;
                }

                case R.id.action_menu: {

                    if (abl.getVisibility() == View.VISIBLE) {
                        abl.setVisibility(View.INVISIBLE);
                    }

                    ConstraintSet constraintSet = new ConstraintSet();
                    ConstraintLayout constraintLayout = findViewById(R.id.ah_container);
                    constraintSet.clone(constraintLayout);
                    constraintSet.connect(R.id.ah_frame_layout, ConstraintSet.BOTTOM, R.id.ah_bottom_navigation, ConstraintSet.TOP, 0);
                    constraintSet.connect(R.id.ah_frame_layout, ConstraintSet.TOP, R.id.ah_appBarLayout, ConstraintSet.TOP, 0);
                    constraintSet.applyTo(constraintLayout);

                    View rootView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_menu, (ViewGroup) frameLayout.getRootView(), false);
                    TextView tv = rootView.findViewById(R.id.m_profileName_textView);
                    ImageView iv = rootView.findViewById(R.id.m_profile_imageView);
                    Picasso.with(getApplicationContext()).load(App.user.getPhotoUrl()).into(iv);
                    tv.setText(App.user.getDisplayName());
                    frameLayout.addView(rootView);
                    return true;
                }

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //TODO
        //App.User --> User Profile Information
    }

    void init() {
        frameLayout = findViewById(R.id.ah_frame_layout);
        abl = findViewById(R.id.ah_appBarLayout);
        mframeTitleTextView = (TextView) findViewById(R.id.ah_frame_title_textView);
        BottomNavigationBar navigation = (BottomNavigationBar) findViewById(R.id.ah_bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void resetConstraintLayout() {
        ConstraintSet constraintSet = new ConstraintSet();
        ConstraintLayout constraintLayout = findViewById(R.id.ah_container);
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.ah_frame_layout, ConstraintSet.BOTTOM, R.id.ah_bottom_navigation, ConstraintSet.TOP, 0);
        constraintSet.connect(R.id.ah_frame_layout, ConstraintSet.TOP, R.id.ah_appBarLayout, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo(constraintLayout);
    }
}
