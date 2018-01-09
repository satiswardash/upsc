package com.kortain.upsc.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kortain.upsc.R;
import com.kortain.upsc.models.ProfileMenuItem;

import java.util.List;

/**
 * Created by satiswardash on 26/12/17.
 */

public class ProfileMenuItemAdapter extends ArrayAdapter<ProfileMenuItem> {

    Context mContext;
    List<ProfileMenuItem> mItemList;

    public ProfileMenuItemAdapter(@NonNull Context context, @NonNull List<ProfileMenuItem> profileMenuItems) {
        super(context, R.layout.viewgroup_profile_bottom_menuitem, profileMenuItems);

        mContext = context;
        mItemList = profileMenuItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rootView = null;
        rootView = LayoutInflater.from(mContext).inflate(R.layout.viewgroup_profile_bottom_menuitem, parent, false);

        ImageView imageView = rootView.findViewById(R.id.cl_profile_imageView);
        TextView textView = rootView.findViewById(R.id.cl_profile_menuItem_textView);

        imageView.setImageResource(mItemList.get(position).getImage());
        textView.setText(mItemList.get(position).getTitle());



        return rootView;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }


}
