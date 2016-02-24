package com.sunay.moony.ui.activitties;

import android.os.Bundle;

import com.squareup.otto.Subscribe;
import com.sunay.moony.R;
import com.sunay.moony.navigation.NavigationEvent;
import com.sunay.moony.ui.fragments.MainFragment;
import com.sunay.moony.util.BusProvider;
import com.sunay.moony.util.Logger;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        BusProvider.getInstanceProvider().navigateToFragment(MainFragment.getInstance(null), true,
            true, false);
    }


    @Subscribe
    public void onNavigationEvent(NavigationEvent event) {
        Logger.d("Navigation called on MainActivity");
        consumeNavigationEvent(event);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean hasDrawer() {
        return false;
    }

    @Override
    protected void handleDrawerSpecificLogic() {

    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_main;
    }

    @Override
    protected boolean handleMenuClick(int menuItemId) {
        return false;
    }
}
