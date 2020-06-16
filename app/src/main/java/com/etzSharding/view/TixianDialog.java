package com.etzSharding.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.bean.UserBean;
import com.etzSharding.utils.ToastUtils;


public class TixianDialog extends Dialog implements View.OnClickListener {
    private EditText count;
    private EditText pwd;
    private TextView btnCancel;
    private TextView withdraw_hint;
    private TextView btnConfirm;
    private EditText address;

    private OnConfirmButtonClickListener listener;
    private UserBean user;

    public void setOnConfirmButtonClickListener(OnConfirmButtonClickListener listener) {
        this.listener = listener;
    }


    public TixianDialog(@NonNull Context context, UserBean user) {
        super(context, R.style.MyDialog);
        this.user = user;
    }

    public TixianDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tixian_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    private void initView() {
        count = findViewById(R.id.tixian_count);
        pwd = findViewById(R.id.tixian_pwd);
        btnCancel = findViewById(R.id.tixian_cancel);
        btnConfirm = findViewById(R.id.tixian_confirm);
        address = findViewById(R.id.tixian_address);
        withdraw_hint = findViewById(R.id.withdraw_hint);
        withdraw_hint.setText(String.format(getContext().getString(R.string.withdraw_hint), user.getFee()));
    }


    private void initEvent() {
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (listener != null) {
            switch (v.getId()) {
                case R.id.tixian_cancel:// 取消
                    dismiss();
                    break;
                case R.id.tixian_confirm:// 确定
                    String num = count.getText().toString().trim();
                    String password = pwd.getText().toString().trim();
                    String addr = address.getText().toString().trim();
                    if (TextUtils.isEmpty(num)) {
                        ToastUtils.showLongToast(getContext(), R.string.input_count_hint);
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        ToastUtils.showLongToast(getContext(), R.string.login_err_pwd);
                        return;
                    }
                    if (TextUtils.isEmpty(addr)) {
                        ToastUtils.showLongToast(getContext(), R.string.send_input_address);
                        return;
                    }
                    listener.onConfirm(password, num, addr);
                    break;
            }
        }
    }

    public interface OnConfirmButtonClickListener {
        void onConfirm(String pwd, String count, String address);
    }

    @Override
    protected void onStop() {
        super.onStop();
        count.setText("");
    }
}
