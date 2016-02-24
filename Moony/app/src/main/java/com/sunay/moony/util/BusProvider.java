package com.sunay.moony.util;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.sunay.moony.R;
import com.sunay.moony.navigation.NavigationEvent;
import com.sunay.moony.ui.fragments.BaseFragment;

/**
 * Created by krasimir.karamazov on 7/31/2014.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();
    private BaseFragment fragment;
    private boolean shouldPopBackstack;
    private boolean shouldAnimateTransition;
    private boolean shouldChangeHomeButtonToBackArrow;

    public static Bus getInstance(){
        return BUS;
    }

    public static BusProvider getInstanceProvider(){
        return new BusProvider();
    }

    private BusProvider(){}

    public void navigateToFragment(BaseFragment fragment,  boolean shouldPopBackstack, boolean shouldAnimateTransition, boolean shouldChangeHomeButtonToBackArrow) {
        this.fragment = fragment;
        this.shouldPopBackstack = shouldPopBackstack;
        this.shouldAnimateTransition = shouldAnimateTransition;
        this.shouldChangeHomeButtonToBackArrow = shouldChangeHomeButtonToBackArrow;
        BusProvider.getInstance().post(getNavigationEvent());
    }

    @Produce
    public NavigationEvent getNavigationEvent() {
        return new NavigationEvent(fragment, shouldPopBackstack, shouldAnimateTransition, shouldChangeHomeButtonToBackArrow, R.id.fl_fragment_container);
    }
}

