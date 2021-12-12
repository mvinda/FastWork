package com.example.fastwork.utils.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.example.fastwork.R;

public class CustomerInputDialog extends BaseCustomerDialog {
    private InputDialogClickListener mInputDialogClick;
    private EditText mInputEdit;

    public CustomerInputDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_custom_input;
    }

    @Override
    protected void initView() {
        message.setVisibility(View.GONE);
        mInputEdit = findViewById(R.id.edit_text);
    }

    public CustomerInputDialog setHintText(String text) {
        if (mInputEdit != null) {
            mInputEdit.setHint(text);
        }
        return this;
    }


    public CustomerInputDialog setPositiveButton(String text, InputDialogClickListener listener) {
        mInputDialogClick = listener;
        if (TextUtils.isEmpty(text)) {
            button2.setVisibility(View.GONE);
        } else {
            button2.setText(text);
            button2.setVisibility(View.VISIBLE);
        }
        layoutButtons();
        return this;
    }


    public interface InputDialogClickListener {
        void getMessage(CustomerInputDialog customerInputDialog,String message);
    }


    @Override
    public void onClick(View v) {
        if (v == button1 && mOnClickListener1 != null) {
            mOnClickListener1.onClick(CustomerInputDialog.this, 1);
        } else if (v == button2) {
            if (mOnClickListener2 != null) {
                mOnClickListener2.onClick(CustomerInputDialog.this, 2);
            }
            if (mInputDialogClick != null) {
                String inputMessage = mInputEdit.getText().toString();
                mInputDialogClick.getMessage(CustomerInputDialog.this,inputMessage);
            }
        }

        if (v == button1) cancel();
    }
}
