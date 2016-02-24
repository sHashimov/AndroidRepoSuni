package com.sunay.moony.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.sunay.moony.R;
import com.sunay.moony.adapters.HomeScreenPagerAdapter;
import com.sunay.moony.ui.activitties.BaseActivity;

import butterknife.Bind;

/**
 * Created by sunay on 16-2-19.
 */
public class MainFragment extends BaseFragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager viewPager;

    public static MainFragment getInstance(Bundle args) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getBackstackTag() {
        return TAG;
    }

    @Override
    protected void retryQuery() {

    }

    @Override
    protected void initUI() {
        setupViewPager();
        if (tabLayout != null) {
            tabLayout.setPadding(0, ((BaseActivity) getContext()).getStatusBarHeight(), 0, 0);
        }
    }


    private void setupViewPager() {
        HomeScreenPagerAdapter adapter =
            new HomeScreenPagerAdapter(getActivity(), getFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected String getTitle() {
        return "";
    }

    @Override
    protected void customizeActionBar() {
    }

    @Override
    public String getScreenNameForAnalytics() {
        return null;
    }

    @Override
    protected boolean handleMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    protected boolean hasFragmentOptionsMenu() {
        return false;
    }
}