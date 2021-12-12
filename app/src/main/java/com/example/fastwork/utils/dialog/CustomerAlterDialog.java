package com.example.fastwork.utils.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import com.example.fastwork.R;

public class CustomerAlterDialog extends BaseCustomerDialog {


    public CustomerAlterDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_custom_common;
    }

    @Override
    protected void initView() {
        EditText edit_text = findViewById(R.id.edit_text);
        edit_text.setVisibility(View.GONE);
    }

}
