package com.kortain.upsc.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.internal.BaselineLayout;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by satiswardash on 22/12/17.
 */

public class BottomNavigationBar extends BottomNavigationView {

    private Typeface fontFace = null;
    private Context context;

    public BottomNavigationBar(Context context) {
        super(context);
        this.context = context;
    }

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        final ViewGroup bottomMenu = (ViewGroup) getChildAt(0);
        final int bottomMenuChildCount = bottomMenu.getChildCount();
        BottomNavigationItemView item;
        View itemTitle;
        Field shiftingMode;
        try {
            //if you want to disable shiftingMode:
            //shiftingMode is a private member variable so you have to get access to it like this:
            shiftingMode = bottomMenu.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(bottomMenu, false);
            shiftingMode.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < bottomMenuChildCount; i++) {
            item = (BottomNavigationItemView) bottomMenu.getChildAt(i);
            //this shows all titles of items
            item.setChecked(true);
            //every BottomNavigationItemView has two children, first is an itemIcon and second is an itemTitle
            itemTitle = item.getChildAt(1);
            //every itemTitle has two children, first is a smallLabel and second is a largeLabel. these two are type of AppCompatTextView
            TextView textView = ((TextView) ((BaselineLayout) itemTitle).getChildAt(1));
            textView.setTextSize(11.0f);

        }
    }

    @Override
    public void setItemIconTintList(@Nullable ColorStateList tint) {
        super.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#9b9b9b")));
    }

}
