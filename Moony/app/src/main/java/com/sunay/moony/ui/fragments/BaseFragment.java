package com.sunay.moony.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.squareup.otto.Subscribe;
import com.sunay.moony.R;
import com.sunay.moony.networking.events.ErrorEvent;
import com.sunay.moony.ui.fragments.dialogs.BaseDialog;
import com.sunay.moony.ui.fragments.dialogs.BasicMessageDialog;
import com.sunay.moony.ui.fragments.dialogs.ProgressDialog;
import com.sunay.moony.util.BusProvider;
import com.sunay.moony.util.Logger;

import butterknife.ButterKnife;

/**
 * Created by krasimir.karamazov on 12/17/2014.
 */
public abstract class BaseFragment extends Fragment {

    private String title;
    private SharedPreferences mPrefs;
    private boolean tracked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(hasFragmentOptionsMenu());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRetainInstance(true);
        title = getTitle();
//        customizeActionBar();
    }

    protected final void setTitle(String title) {
//        if (getActivity() != null) {
//            if (getActivity() instanceof AppCompatActivity) {
//                if (!TextUtils.isEmpty(title)) {
//                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
//                }
//            }
//        }
    }

    protected final void setTitle(int stringResourceId) {
        title = getString(stringResourceId);
        setTitle(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        if (getLayoutId() != 0) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, rootView);
            initUI();
        }

        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
            View view = onCreateView(getActivity().getLayoutInflater(), viewGroup, null);
            viewGroup.addView(view);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(title);
        BusProvider.getInstance().register(this);
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    protected final void setUpActionBarTitle() {
        try {
            if (getActivity() instanceof AppCompatActivity) {
                /*((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='" + ColorUtils.rgbToHexString(DataHolder.getInstance().getSchoolInfo().getSchoolSecondaryColor()) + "'" + ">" + getTitle() + "</font>"));*/
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        int menuId = getMenuId();
        if (menuId != 0) {
            inflater.inflate(menuId, menu);
        }
    }

    protected final SharedPreferences getSharedPreferences() {
        if (mPrefs == null) {
            mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        }
        return mPrefs;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return handleMenuItemClick(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(BaseFragment.this);
    }

    protected final void showProgress() {

        try {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            if (fm.findFragmentByTag(ProgressDialog.TAG) == null) {
                Bundle args = new Bundle();
                args.putString(BaseDialog.MESSAGE_ARG_KEY, getString(R.string.please_wait));
                BaseDialog progressDialog = ProgressDialog.getInstance(args);
                progressDialog.show(getActivity().getSupportFragmentManager(), ProgressDialog.TAG);
            }
        } catch (Exception e) {
        }
    }

    protected final void showProgressCancellable() {

        try {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            if (fm.findFragmentByTag(ProgressDialog.TAG) == null) {
                Bundle args = new Bundle();
                args.putString(BaseDialog.MESSAGE_ARG_KEY, getString(R.string.please_wait));
                BaseDialog progressDialog = ProgressDialog.getInstance(args);
                progressDialog.setCancelable(false);
                progressDialog.show(getActivity().getSupportFragmentManager(), ProgressDialog.TAG);
            }
        } catch (Exception e) {
        }
    }

    protected final void hideProgress() {
        try {
            BaseDialog dialog = (ProgressDialog) getActivity().getSupportFragmentManager().findFragmentByTag(ProgressDialog.TAG);
            if (dialog != null) {
                Logger.d("Dialog dismissed");
                dialog.dismiss();
            }
        } catch (Exception e) {
        }
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent e) {
        handleErrorEvent();
    }

    /*protected final void setTitle(String title) {

    }*/

    protected void handleErrorEvent() {
        hideProgress();
        BasicMessageDialog.getInstance(null, null).show(getActivity().getSupportFragmentManager(), BasicMessageDialog.TAG);
    }

    public abstract String getBackstackTag();

    protected abstract void retryQuery();

    protected abstract void initUI();

    protected abstract int getLayoutId();

    protected abstract int getMenuId();

    protected abstract String getTitle();

    protected abstract void customizeActionBar();

    public abstract String getScreenNameForAnalytics();

    protected abstract boolean handleMenuItemClick(MenuItem item);

    protected abstract boolean hasFragmentOptionsMenu();
}
