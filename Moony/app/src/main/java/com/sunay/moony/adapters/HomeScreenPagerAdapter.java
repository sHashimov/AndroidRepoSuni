package com.sunay.moony.adapters;

/**
 * Created by sunay on 16-2-19.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sunay.moony.ui.fragments.ForecastFragment;
import com.sunay.moony.ui.fragments.HomeFragment;


/**
 * Created by krasi on 1/18/2016.
 */
public class HomeScreenPagerAdapter extends FragmentPagerAdapter {
    private static final int COUNT = 2;
    private Context context;

    public HomeScreenPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        if (position == 1) {
            return ForecastFragment.getInstance(args);
        } else {
            return HomeFragment.getInstance(args);
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 1) {
            return "FORECAST";
        } else {
            return "TODAY";
        }
    }
}