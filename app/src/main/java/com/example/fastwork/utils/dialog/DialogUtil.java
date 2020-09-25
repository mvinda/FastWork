package com.example.fastwork.utils.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;



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

    public CustomAlertDialog showAlertDialog(int title, int msg, int negative, int positive, final DialogInterface.OnClickListener negativeListener, final DialogInterface.OnClickListener positiveListener) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext);
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

    public void showAlertDialog(String title, String msg, String negative, String positive, final DialogInterface.OnClickListener negativeListener, final DialogInterface.OnClickListener positiveListener) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext);
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
    }
}
