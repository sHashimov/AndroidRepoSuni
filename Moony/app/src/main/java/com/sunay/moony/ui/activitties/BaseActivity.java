package com.sunay.moony.ui.activitties;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.sunay.moony.R;
import com.sunay.moony.navigation.NavigationEvent;
import com.sunay.moony.ui.fragments.dialogs.BaseDialog;
import com.sunay.moony.ui.fragments.dialogs.ProgressDialog;
import com.sunay.moony.util.BusProvider;
import com.sunay.moony.util.Logger;

import butterknife.ButterKnife;

/**
 * Created by krasimir.karamazov on 12/9/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private SharedPreferences mPrefs;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(getLayoutId());
        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        //StatusBarCompat.setUpActivity(this);


        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        BusProvider.getInstance().register(this);
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        Logger.d("NullState");
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    public final SharedPreferences getSharedPreferences() {
        if (mPrefs == null) {
            mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        }
        return mPrefs;
    }

    protected final void showProgress() {

        try {
            FragmentManager fm = this.getSupportFragmentManager();
            if (fm.findFragmentByTag(ProgressDialog.TAG) == null) {
                Bundle args = new Bundle();
                args.putString(BaseDialog.MESSAGE_ARG_KEY, getString(R.string.please_wait));
                BaseDialog progressDialog = ProgressDialog.getInstance(args);
                progressDialog.show(this.getSupportFragmentManager(), ProgressDialog.TAG);
            }
        } catch (NullPointerException e) {
        }
    }

    protected final boolean isProgressShowing() {
        try {
            BaseDialog dialog = (ProgressDialog) this.getSupportFragmentManager().findFragmentByTag(ProgressDialog.TAG);
            if (dialog != null) {
                return dialog.isVisible();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    protected abstract int getLayoutId();

    protected final void hideProgress() {
        try {
            BaseDialog dialog = (ProgressDialog) this.getSupportFragmentManager().findFragmentByTag(ProgressDialog.TAG);
            if (dialog != null) {
                Logger.d("Dialog dismissed");
                dialog.dismiss();
            }
        } catch (Exception e) {
        }
    }

    protected final void consumeNavigationEvent(NavigationEvent event) {
        Logger.d("Navigation called on BaseDrawerActivity");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (event.getShouldAnimateTransaction()) {
            transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.left_in, R.anim.left_out);
        }

        if (event.getShouldPopBackStack()) {
            try {
                popBackStack();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

//        if (event.shouldChangeDrawerToBackArrow()) {
//            if (hasDrawer()) {
//                ((BaseDrawerActivity) this).changeDrawerIndicator(true);
//            }
//        }
        int containerId;
        if (event.getContainerId() != 0) {
            containerId = event.getContainerId();
            transaction.addToBackStack(event.getFragment().getBackstackTag());
        } else {
            containerId = R.id.fl_fragment_container;
            transaction.addToBackStack(event.getFragment().getBackstackTag());
        }

        transaction.replace(containerId, event.getFragment(), event.getFragment().getBackstackTag());
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    public final void popBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStackImmediate();
        }
    }

    @Override
    public void onBackPressed() {
        if (hasDrawer()) {
            handleDrawerSpecificLogic();
        } else {
            navigateBackwards();
        }
    }

    protected void navigateBackwards() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(getMenuResourceId(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return handleMenuClick(id);
    }

    protected abstract boolean hasDrawer();
    protected abstract void handleDrawerSpecificLogic();
    protected abstract int getMenuResourceId();
    protected abstract boolean handleMenuClick(int menuItemId);
}
