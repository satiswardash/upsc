package com.kortain.upsc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kortain.upsc.adapters.ProfileMenuItemAdapter;
import com.kortain.upsc.helpers.App;
import com.kortain.upsc.helpers.LayoutHelper;
import com.kortain.upsc.models.ConstraintRules;
import com.kortain.upsc.widgets.BottomNavigationBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String[] MAIN_MENU_ITEMS = {"Category", "Archives", "Exam Tips", "FAQs", "Settings", "About"};
    public static final int[] MENU_IMAGES = {R.drawable.ic_collection, R.drawable.ic_archive, R.drawable.ic_tips, R.drawable.ic_faq, R.drawable.ic_settings, R.drawable.ic_info};
    public static final int INDEX_COUNT = 6;
    public static final int VISIBLE = 1;
    public static final int INVISIBLE = 0;

    AppBarLayout mAppBarLayout;
    FrameLayout mFrameLayout;
    TextView mFrameTitleTextView;
    SearchView mSearchView;
    BottomNavigationBar.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationBar.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return initializeFrames(item);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrameLayout = findViewById(R.id.ah_frame_layout);
        mAppBarLayout = findViewById(R.id.ah_appBarLayout);
        mSearchView = findViewById(R.id.ah_search_box);
        mFrameTitleTextView = (TextView) findViewById(R.id.ah_frame_title_textView);
        BottomNavigationBar navigation = (BottomNavigationBar) findViewById(R.id.ah_bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * Initialize activity frames {Home, Objective, Subjective, Exam, Menu}
     *
     * @param item
     * @return
     */
    private boolean initializeFrames(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_home: {
                initNonMenuFrame();
                View homeView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.frame_layout_home, (ViewGroup) mFrameLayout.getRootView(), false);

                mFrameTitleTextView.setText(R.string.home);
                mFrameLayout.addView(homeView);
                return true;
            }

            case R.id.action_objective: {
                initNonMenuFrame();
                View objView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.frame_layout_objective, (ViewGroup) mFrameLayout.getRootView(), false);

                mFrameTitleTextView.setText(R.string.obj_gallery);
                mFrameLayout.addView(objView);
                return true;
            }

            case R.id.action_subjective: {
                initNonMenuFrame();
                View subView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.frame_layout_subjective, (ViewGroup) mFrameLayout.getRootView(), false);

                mFrameTitleTextView.setText(R.string.sub_gallery);
                mFrameLayout.addView(subView);
                return true;
            }

            case R.id.action_exam: {
                initNonMenuFrame();
                View examView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.frame_layout_exam, (ViewGroup) mFrameLayout.getRootView(), false);

                mFrameTitleTextView.setText(R.string.exam_center);
                mFrameLayout.addView(examView);
                return true;
            }

            case R.id.action_menu: {
                List<ConstraintRules> constraintRules;
                ConstraintLayout constraintLayout = findViewById(R.id.ah_container);

                constraintRules = new ArrayList<>();
                constraintRules.add(new ConstraintRules(R.id.ah_frame_layout, ConstraintSet.BOTTOM, R.id.ah_bottom_navigation, ConstraintSet.TOP, 0));
                constraintRules.add(new ConstraintRules(R.id.ah_frame_layout, ConstraintSet.TOP, R.id.ah_appBarLayout, ConstraintSet.TOP, 0));

                mFrameLayout.removeAllViewsInLayout();
                LayoutHelper.getInstance().resetAppBarLayoutVisibility(LayoutHelper.INVISIBLE, mAppBarLayout);
                LayoutHelper.getInstance().resetLayoutConstraints(constraintLayout, constraintRules);

                View rootView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.frame_layout_menu, (ViewGroup) mFrameLayout.getRootView(), false);

                TextView tv = rootView.findViewById(R.id.m_profileName_textView);
                TextView viewProfile = rootView.findViewById(R.id.m_viewProfile_textView);
                ImageView iv = rootView.findViewById(R.id.m_profile_imageView);
                ListView listView = rootView.findViewById(R.id.m_menu_listView);

                Picasso.with(getApplicationContext()).load(App.user.getPhotoUrl()).into(iv);
                tv.setText(App.user.getDisplayName());
                viewProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                });

                listView.setAdapter(new ProfileMenuItemAdapter(getApplicationContext(), getMenuItems()));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        com.kortain.upsc.models.MenuItem menuItem = (com.kortain.upsc.models.MenuItem) adapterView.getItemAtPosition(i);
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });

                mFrameLayout.addView(rootView);
                return true;
            }

        }
        return false;
    }

    /**
     * Initialize non-menu frame layout
     */
    private void initNonMenuFrame() {
        List<ConstraintRules> constraintRules;
        ConstraintLayout constraintLayout = findViewById(R.id.ah_container);

        constraintRules = new ArrayList<>();
        constraintRules.add(new ConstraintRules(R.id.ah_frame_layout, ConstraintSet.BOTTOM, R.id.ah_bottom_navigation, ConstraintSet.TOP, 0));
        constraintRules.add(new ConstraintRules(R.id.ah_frame_layout, ConstraintSet.TOP, R.id.ah_appBarLayout, ConstraintSet.BOTTOM, 0));

        LayoutHelper.getInstance().resetAppBarLayoutVisibility(LayoutHelper.VISIBLE, mAppBarLayout);
        LayoutHelper.getInstance().resetLayoutConstraints(constraintLayout, constraintRules);

        mFrameLayout.removeAllViewsInLayout();
    }

    /**
     * Get Main Menu Items Data
     *
     * @return
     */
    private List<com.kortain.upsc.models.MenuItem> getMenuItems() {

        List<com.kortain.upsc.models.MenuItem> items = new ArrayList<>();

        for (int index = 0; index < INDEX_COUNT; index++) {
            items.add(new com.kortain.upsc.models.MenuItem(null, MENU_IMAGES[index], MAIN_MENU_ITEMS[index]));
        }

        return items;
    }


}
