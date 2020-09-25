package com.example.fastwork.utils.dialog;

import android.app.Dialog;
import android.content.Context;

public class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void dismiss() {

        try {
            super.dismiss();
        } catch (Exception e) {

        }
    }
}
