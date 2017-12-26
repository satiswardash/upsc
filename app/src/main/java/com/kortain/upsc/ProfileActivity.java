package com.kortain.upsc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.kortain.upsc.adapters.ProfileMenuItemAdapter;
import com.kortain.upsc.helpers.App;
import com.kortain.upsc.models.MenuItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {


    public static final String[] PROFILE_MENU_ITEMS = {"Edit Profile", "Scoreboard", "My Collections", "Bookmarks", "Preferences"};
    public static final int[] MENU_IMAGES = {R.drawable.ic_users_profile, R.drawable.ic_scoreboard, R.drawable.ic_collection, R.drawable.ic_bookmark, R.drawable.ic_preference};
    public static final int INDEX_COUNT = 5;

    ImageView mBackNavigation;
    ListView mProfileMenuListView;
    ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        init();

        mBackNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mProfileMenuListView.setAdapter(new ProfileMenuItemAdapter(this, getMenuItems()));
        mProfileMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuItem menuItem = (MenuItem) adapterView.getItemAtPosition(i);
                Toast.makeText(ProfileActivity.this, menuItem.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.with(getApplicationContext()).load(App.user.getPhotoUrl()).into(profileImageView);
    }

    private void init() {

        mBackNavigation = findViewById(R.id.ll_profile_navigation_back);
        mProfileMenuListView = findViewById(R.id.fl_profile_menu_listView);
        profileImageView = findViewById(R.id.fl_profile_imageView);

    }

    /**
     * Get the Profile Menu Item Data
     *
     * @return
     */
    private List<MenuItem> getMenuItems() {

        List<MenuItem> items = new ArrayList<>();
        for (int index = 0; index < INDEX_COUNT; index++) {
            items.add(new MenuItem(null, MENU_IMAGES[index], PROFILE_MENU_ITEMS[index]));
        }

        return items;
    }
}
