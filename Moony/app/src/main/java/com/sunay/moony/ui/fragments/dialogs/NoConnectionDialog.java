package com.sunay.moony.ui.fragments.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;

import com.sunay.moony.R;

/**
 * Created by krasimir.karamazov on 7/16/2015.
 */
public class NoConnectionDialog extends BaseDialog {

    public static final String TAG = NoConnectionDialog.class.getSimpleName();

    public static NoConnectionDialog getInstance(Bundle args) {
        NoConnectionDialog fragment = new NoConnectionDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initUI(View view, AlertDialog.Builder builder) {

    }

    @Override
    protected void setupDialog(AlertDialog.Builder dialogBuilder) {
        dialogBuilder.setTitle(R.string.error);
        dialogBuilder.setMessage(R.string.no_internet_connection_);
        dialogBuilder.setPositiveButton(R.string.settings, getOnSettingsClickListener());
    }

    private DialogInterface.OnClickListener getOnSettingsClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(
                        WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        };
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected boolean getIsCustomDialog() {
        return false;
    }

    @Override
    protected String getDialogTag() {
        return TAG;
    }
}
