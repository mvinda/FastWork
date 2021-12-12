package com.example.fastwork.utils.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;


public class DialogUtil {

    private final Context mContext;

    private ProgressDialog mProgressDialog;

    public DialogUtil(Context context) {
        mContext = context;
    }

    public void showProgressDialog(DialogInterface.OnCancelListener listener) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.setCancelable(listener != null);
        mProgressDialog.setOnCancelListener(listener);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public CustomerAlterDialog showAlertDialog(String title, String msg, String negative, String positive, final DialogInterface.OnClickListener negativeListener, final DialogInterface.OnClickListener positiveListener) {
        CustomerAlterDialog customAlertDialog = new CustomerAlterDialog(mContext);
        customAlertDialog.setTitle(title);
        customAlertDialog.setMessage(msg);
        customAlertDialog.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (negativeListener != null)
                    negativeListener.onClick(dialog, which);
            }
        });
        customAlertDialog.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (positiveListener != null) {
                    positiveListener.onClick(dialog, which);
                }
            }
        });
        customAlertDialog.show();
        return customAlertDialog;
    }


    public CustomerInputDialog showInputDialog(String title, String msg, String negativeMessage, String positiveMessage, String hint, final DialogInterface.OnClickListener negativeListener, final CustomerInputDialog.InputDialogClickListener comfirmDialog) {
        CustomerInputDialog inputDialog = new CustomerInputDialog(mContext);
        if (!TextUtils.isEmpty(hint)) {
            inputDialog.setHintText(hint);
        }
        inputDialog.setTitle(title);
        inputDialog.setNegativeButton(negativeMessage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                negativeListener.onClick(dialog, which);
            }
        });

        inputDialog.setPositiveButton(positiveMessage, new CustomerInputDialog.InputDialogClickListener() {
            @Override
            public void getMessage(CustomerInputDialog customerInputDialog, String message) {
                comfirmDialog.getMessage(customerInputDialog,message);
            }
        });


        inputDialog.show();
        return inputDialog;
    }

}
