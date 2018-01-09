package com.kortain.upsc.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.kortain.upsc.models.HomeMenuItem;
import com.kortain.upsc.models.HomeSubMenuItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by satiswardash on 27/12/17.
 */

public class MenuHomeExpandableListAdapter extends BaseExpandableListAdapter {

    Context mContext;
    ExpandableListView mExpandableListView;
    List<HomeMenuItem> mMenuItemList;
    HashMap<HomeMenuItem, List<HomeSubMenuItem>> mItemListMap;

    public MenuHomeExpandableListAdapter (Context context, ExpandableListView listView, List<HomeMenuItem> menuItemList, HashMap<HomeMenuItem, List<HomeSubMenuItem>> subMenuItemList) {
        mContext = context;
        mExpandableListView = listView;
        mMenuItemList = menuItemList;
        mItemListMap = subMenuItemList;
    }

    @Override
    public int getGroupCount() {
        return mMenuItemList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mItemListMap.get(this.mMenuItemList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
