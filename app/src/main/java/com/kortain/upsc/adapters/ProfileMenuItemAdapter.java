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
import com.kortain.upsc.models.MenuItem;

import java.util.List;

/**
 * Created by satiswardash on 26/12/17.
 */

public class ProfileMenuItemAdapter extends ArrayAdapter<MenuItem> {

    Context mContext;
    List<MenuItem> mItemList;

    public ProfileMenuItemAdapter(@NonNull Context context, @NonNull List<MenuItem> menuItems) {
        super(context, R.layout.layout_profile_bottom_menuitem, menuItems);

        mContext = context;
        mItemList = menuItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rootView = null;
        rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_profile_bottom_menuitem, parent, false);

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
