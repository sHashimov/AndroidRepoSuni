package com.sunay.moony.ui.fragments.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.sunay.moony.R;


/**
 * Created by krasimir.karamazov on 7/17/2015.
 */
public class BasicMessageDialog extends BaseDialog {

    public static final String TAG = BasicMessageDialog.class.getSimpleName();

    private DialogButtonClickListener mListener;
    public static BasicMessageDialog getInstance(Bundle args, DialogButtonClickListener listener) {
        BasicMessageDialog fragment = new BasicMessageDialog();
        fragment.setArguments(args);
        fragment.setDialogButtonClickLIstener(listener);
        return fragment;
    }

    @Override
    protected void initUI(View view, AlertDialog.Builder builder) {

    }

    protected final void setDialogButtonClickLIstener(DialogButtonClickListener listener){
        mListener = listener;
    }

    @Override
    protected void setupDialog(AlertDialog.Builder dialogBuilder) {
        String title = getString(R.string.error);
        String message = getString(R.string.server_error);
        if(getArguments() != null) {
            title = getArguments().getString(TITLE_ARG_KEY, getString(R.string.error));
            message = getArguments().getString(MESSAGE_ARG_KEY, getString(R.string.server_error));
        }
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(mListener != null) {
                    mListener.onDialogButtonClick(i);
                }
            }
        });
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
        return null;
    }

    public interface DialogButtonClickListener {
        void onDialogButtonClick(int whichButton);
    }
}
