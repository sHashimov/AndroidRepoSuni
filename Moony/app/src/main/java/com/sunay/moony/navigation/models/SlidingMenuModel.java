package com.sunay.moony.navigation.models;

import android.content.Context;
import android.content.res.TypedArray;

import com.sunay.moony.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by krasimir.karamazov on 12/17/2014.
 */
public class SlidingMenuModel {
    private List<SlidingMenuItemModel> menuList = new LinkedList<SlidingMenuItemModel>();
    private Context mContext;
    private static SlidingMenuModel sInstance;

    public enum NAV_NAMES {
        profile, vuz_feed, my_videos, my_favorites, my_subscriptions, search, my_followers, airvuz_information, logout
    }

    public static synchronized SlidingMenuModel getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SlidingMenuModel(context.getApplicationContext());
        }
        return sInstance;
    }

    private SlidingMenuModel(Context context) {
        mContext = context.getApplicationContext();
//        prepareData();
    }

    public void prepareData() {
        String[] labels = mContext.getResources().getStringArray(R.array.nav_drawer_labels);
        TypedArray icons = mContext.getResources().obtainTypedArray(R.array.nav_drawer_icons);
        int[] startsActivity = mContext.getResources().getIntArray(R.array.starts_activity_flags);

        if (labels.length == 0 || icons.length() == 0) {
            throw new IllegalStateException("The labels or icons arrays cannot be empty. Define them in arr.xml with names nav_drawer_labels and nav_drawer_icons");
        } else if (labels.length != icons.length()) {
            throw new IllegalStateException("The labels and icons arrays should have the same number of items");
        }

        for (int i = 0; i < labels.length; i++) {
            menuList.add(new SlidingMenuItemModel(icons.getResourceId(i, -1), labels[i], startsActivity[i]));
        }
    }

    public List<SlidingMenuItemModel> getData() {
        return menuList;
    }

    public SlidingMenuItemModel getItem(int index) {
        return menuList.get(index);
    }
}
