package com.sunay.moony.ui.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by krasimir.karamazov on 12/17/2014.
 */
public abstract class BaseDialog extends DialogFragment {

    public static final String TITLE_ARG_KEY = "dialog_title";
    public static final String MESSAGE_ARG_KEY = "dialog_message";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(getIsCustomDialog()){
            View view = inflater.inflate(getLayoutId(), null, false);
            initUI(view, builder);
            builder.setView(view);
        }else{
            setupDialog(builder);
        }

        return builder.show();
    }

    protected abstract void initUI(View view, AlertDialog.Builder builder);
    protected abstract void setupDialog(AlertDialog.Builder dialogBuilder);
    protected abstract int getLayoutId();
    protected abstract boolean getIsCustomDialog();
    protected abstract String getDialogTag();
}
