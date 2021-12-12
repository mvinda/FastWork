package com.example.fastwork.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.fastwork.R;
import com.example.fastwork.utils.value.ValueUtil;



public abstract class BaseCustomerDialog extends BaseDialog implements View.OnClickListener {
    protected TextView button1;
    protected TextView button2;
    protected TextView message;
    protected TextView titleView;

    protected DialogInterface.OnClickListener mOnClickListener1;
    protected DialogInterface.OnClickListener mOnClickListener2;

    public BaseCustomerDialog(Context context) {
        super(context, R.style.dialog);
        setContentView(getLayoutId());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().getAttributes();
    }

    protected abstract int getLayoutId();

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        button1 = (TextView) findViewById(R.id.button1);
        button2 = (TextView) findViewById(R.id.button2);

        titleView = (TextView) findViewById(R.id.title);
        message = (TextView) findViewById(R.id.message);
//        closeIcon = findViewById(R.id.buttonClose);
//        closeIcon.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        setNegativeButton(null, null);
        setPositiveButton(null, null);
        initView();
    }

    protected abstract void initView();

    public BaseCustomerDialog setMessage(String msg) {
        message.setText(msg);
        return this;
    }

    public BaseCustomerDialog setMessage(int msg) {
        message.setText(msg);
        return this;
    }

    public void setTitle(CharSequence title) {
        super.setTitle(title);
        titleView.setText(title);
    }

    public BaseCustomerDialog setDialogTitle(String titile) {
        setTitle(titile);
        return this;
    }

    public BaseCustomerDialog setNegativeButton(String text, DialogInterface.OnClickListener listener) {
        mOnClickListener1 = listener;

        if (TextUtils.isEmpty(text)) {
            button1.setVisibility(View.GONE);
            button1.setText(null);
        } else {

            button1.setText(text);
            button1.setVisibility(View.VISIBLE);
        }
        layoutButtons();
        return this;
    }

    public BaseCustomerDialog setPositiveButton(String text, DialogInterface.OnClickListener listener) {
        mOnClickListener2 = listener;
        if (TextUtils.isEmpty(text)) {
            button2.setVisibility(View.GONE);
        } else {
            button2.setText(text);
            button2.setVisibility(View.VISIBLE);
        }
        layoutButtons();
        return this;
    }

    protected void layoutButtons() {
        if (button1.getVisibility() == View.VISIBLE) {

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button1.getLayoutParams();
            if (button2.getVisibility() == View.VISIBLE) {
                params.weight = 1;
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;

                params = (LinearLayout.LayoutParams) button2.getLayoutParams();
                params.weight = 1;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                params.weight = 0;
                params.width = ValueUtil.dip2px(getContext(), 150);
            }

        } else if (button2.getVisibility() == View.VISIBLE) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button2.getLayoutParams();
            params.weight = 1;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
    }



    @Override
    public void onClick(View v) {
        if (v == button1 && mOnClickListener1 != null) {
            mOnClickListener1.onClick(BaseCustomerDialog.this, 1);
        } else if (v == button2 && mOnClickListener2 != null) {
            mOnClickListener2.onClick(BaseCustomerDialog.this, 2);
        }

        if (v == button1) cancel();
        else if (v == button2) dismiss();
    }
}
