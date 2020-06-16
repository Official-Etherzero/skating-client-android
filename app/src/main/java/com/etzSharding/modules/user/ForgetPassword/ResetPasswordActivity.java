package com.etzSharding.modules.user.ForgetPassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.base.Constants;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity<ForgetPasswordView, ForgetPasswordPresenter> implements ForgetPasswordView {


    @BindView(R.id.reset_pwd_1)
    EditText resetPwd1;
    @BindView(R.id.reset_pwd_2)
    EditText resetPwd2;
    @BindView(R.id.reset_affirm)
    TextView resetAffirm;

    private String account;
    private boolean isEmail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public ForgetPasswordPresenter initPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.reset_pwd));
        isEmail = getIntent().getBooleanExtra("isEmail", false);
        account = getIntent().getStringExtra("account");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        resetPwd1.addTextChangedListener(watcher);
        resetPwd2.addTextChangedListener(watcher);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.reset_affirm)
    public void onViewClicked() {
        String pwd1 = resetPwd1.getText().toString().trim();
        String pwd2 = resetPwd2.getText().toString().trim();
        if (!Util.isPassword(pwd1)) {
            ToastUtils.showLongToast(activity, R.string.pwd_style_hint);
        } else if (!pwd1.equals(pwd2)) {
            ToastUtils.showLongToast(activity, R.string.pwds_alien);
        } else {
            if (isEmail) {
                presenter.resetMailPWD(account, pwd1);
            } else {
                presenter.resetPhonePWD("86", account, pwd1);
            }
        }
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (!Util.isNullOrEmpty(resetPwd1.getText().toString()) && !Util.isNullOrEmpty(resetPwd2.getText().toString())) {
                resetAffirm.setEnabled(true);
            } else {
                resetAffirm.setEnabled(false);
            }

        }
    };

    @Override
    public void requestFail(String msg) {

    }

    @Override
    public void codeSuccess(int code) {

    }

    @Override
    public void verificationSuccess(int code) {

    }

    @Override
    public void resetSuccess(int code) {
        if (code == 0) {
            ToastUtils.showLongToast(activity, R.string.find_pwd_succ);
            setResult(Activity.RESULT_OK);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }
    }


}
