//package com.sunay.moony.ui.activitties;
//
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import com.sunay.moony.navigation.NavigationDrawerController;
//import com.sunay.moony.util.Logger;
//
///**
// * Created by krasimir.karamazov on 12/9/2015.
// */
//public abstract class BaseDrawerActivity extends BaseActivity {
//    NavigationDrawerController mDrawerController;
//    private boolean isDrawerLocked;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mDrawerController = NavigationDrawerController.getInstance();
//        mDrawerController.init(this);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        mDrawerController.getDrawerToggle().syncState();
//    }
//
//    public final void invalidateSelectedNavigationIndex() {
//        mDrawerController.invalidateSelectedIndex();
//    }
//
//    protected final void changeDrawerIndicator(boolean toggleLock) {
//        isDrawerLocked = toggleLock;
//        mDrawerController.toggleLockDrawer(isDrawerLocked);
//    }
//
//    @Override
//    protected boolean hasDrawer() {
//        return true;
//    }
//
//    @Override
//    protected void handleDrawerSpecificLogic() {
//        if (mDrawerController.isDrawerOpened()) {
//            mDrawerController.toggleDrawer();
//        } else {
//            navigateBackwards();
//            if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
//                mDrawerController.toggleLockDrawer(false);
//            }
//        }
//    }
//
//    public final void toggleDrawer(boolean open) {
//        if (open) {
//            mDrawerController.openDrawer();
//        } else {
//            mDrawerController.closeDrawer();
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (hasDrawer()) {
//            if (!mDrawerController.isLocked() && mDrawerController.getDrawerToggle().onOptionsItemSelected(item)) {
//
//                if (!mDrawerController.isDrawerOpened()) {
//                    Logger.d("OPEN");
//                } else {
//                    Logger.d("CLOSE");
//                }
//                return true;
//            } else if (mDrawerController.isLocked()) {
//                switch (item.getItemId()) {
//                    case android.R.id.home:
//                        onBackPressed();
//                        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
//                            mDrawerController.toggleLockDrawer(false);
//                        }
//                        break;
//                }
//            }
//        } else {
//            switch (item.getItemId()) {
//                case android.R.id.home:
//                    onBackPressed();
//                    break;
//            }
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
