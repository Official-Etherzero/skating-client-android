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
import com.etzSharding.utils.ToastUtils;


public class RechargeDialog extends Dialog implements View.OnClickListener {
    private EditText count;
    private TextView btnCancel;
    private TextView btnConfirm;

    private OnInputDialogButtonClickListener onInputDialogButtonClickListener;

    public void setOnInputDialogButtonClickListener(OnInputDialogButtonClickListener onInputDialogButtonClickListener) {
        this.onInputDialogButtonClickListener = onInputDialogButtonClickListener;
    }


    public RechargeDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    public RechargeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    private void initView() {
        count = findViewById(R.id.input_count);
        btnCancel = findViewById(R.id.recharge_cancel);
        btnConfirm = findViewById(R.id.recharge_confirm);
    }


    private void initEvent() {
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (onInputDialogButtonClickListener != null) {
            switch (v.getId()) {
                case R.id.recharge_cancel:// 取消
                    onInputDialogButtonClickListener.onCancel();
                    break;
                case R.id.recharge_confirm:// 确定
                    String pwd = count.getText().toString().trim();
                    if (TextUtils.isEmpty(pwd)) {
                        ToastUtils.showLongToast(R.string.input_pwd_dialog_tip);
                        return;
                    }
                    onInputDialogButtonClickListener.onConfirm(pwd);
                    break;
            }
        }
    }

    public interface OnInputDialogButtonClickListener {
        void onCancel();

        void onConfirm(String pwd);
    }

    @Override
    protected void onStop() {
        super.onStop();
        count.setText("");
    }
}
