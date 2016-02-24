package com.sunay.moony.navigation;

import com.sunay.moony.ui.fragments.BaseFragment;

/**
 * Created by krasimir.karamazov on 7/7/2015.
 */
public class NavigationEvent {
    private BaseFragment mFragment;
    private boolean mShouldPopBackStack;
    private boolean mShouldAnimateTransaction;
    private boolean mShouldChangeDrawerToBackArrow;
    private int containerId;

    public NavigationEvent(BaseFragment fragment, boolean shouldPopBackStack) {
        mFragment = fragment;
        mShouldPopBackStack = shouldPopBackStack;
        mShouldAnimateTransaction = true;
        mShouldChangeDrawerToBackArrow = false;
    }

    public NavigationEvent(BaseFragment fragment, boolean shouldPopBackStack, boolean shouldAnimateTransaction) {
        this(fragment, shouldPopBackStack);
        mShouldAnimateTransaction = shouldAnimateTransaction;
        mShouldChangeDrawerToBackArrow = false;
    }

    public NavigationEvent(BaseFragment fragment, boolean shouldPopBackStack, boolean shouldAnimateTransaction, boolean shouldChangeDrawerToBackArrow, int containerId) {
        this(fragment, shouldPopBackStack);
        mShouldAnimateTransaction = shouldAnimateTransaction;
        mShouldChangeDrawerToBackArrow = shouldChangeDrawerToBackArrow;
        this.containerId = containerId;
    }

    /** FOR TABLETS **/
    public NavigationEvent(BaseFragment fragment, boolean shouldPopBackStack, boolean shouldAnimateTransaction, int containerId) {
        this(fragment, shouldPopBackStack);
        mShouldAnimateTransaction = shouldAnimateTransaction;
        this.containerId = containerId;
    }

    public boolean getShouldAnimateTransaction() {
        return mShouldAnimateTransaction;
    }

    public BaseFragment getFragment() {
        return mFragment;
    }
    public boolean getShouldPopBackStack() {
        return mShouldPopBackStack;
    }

    public boolean shouldChangeDrawerToBackArrow() {
        return mShouldChangeDrawerToBackArrow;
    }

    public void shouldChangeDrawerToBackArrow(boolean shouldChangeDrawerToBackArrow) {
        this.mShouldChangeDrawerToBackArrow = shouldChangeDrawerToBackArrow;
    }

    public int getContainerId() {
        return containerId;
    }
}

