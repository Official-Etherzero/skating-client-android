package com.etzSharding.modules.user.ForgetPassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.base.Constants;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;
import com.etzSharding.view.VerificationCodePopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordView, ForgetPasswordPresenter> implements ForgetPasswordView,
        View.OnClickListener, VerificationCodePopupWindow.onPopupWindowListener {


    @BindView(R.id.for_email)
    TextView forEmail;
    @BindView(R.id.for_email_v)
    View forEmailV;
    @BindView(R.id.for_phone)
    TextView forPhone;
    @BindView(R.id.for_phone_v)
    View forPhoneV;
    @BindView(R.id.for_area)
    TextView forArea;
    @BindView(R.id.find_pwd_phone)
    EditText findPwdPhone;
    @BindView(R.id.for_phone_err_hint)
    TextView forPhoneErrHint;
    @BindView(R.id.for_phone_acc)
    ConstraintLayout forPhoneAcc;
    @BindView(R.id.find_pwd_email)
    EditText findPwdEmail;
    @BindView(R.id.for_email_err_hint)
    TextView forEmailErrHint;
    @BindView(R.id.for_email_acc)
    ConstraintLayout forEmailAcc;
    @BindView(R.id.find_pwd_next)
    TextView findPwdNext;

    private VerificationCodePopupWindow vcpop;

    private boolean isEmail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public ForgetPasswordPresenter initPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.find_password));
        selectorIsPhone(false);
        isEmail = true;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        findPwdEmail.addTextChangedListener(watcher);
        findPwdPhone.addTextChangedListener(watcher);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.for_email, R.id.for_phone, R.id.for_area, R.id.find_pwd_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.for_email:
                isEmail = true;
                selectorIsPhone(false);
                break;
            case R.id.for_phone:
                isEmail = false;
                selectorIsPhone(true);
                break;
            case R.id.for_area:
                break;
            case R.id.find_pwd_next:
                String email = findPwdEmail.getText().toString().trim();
                String phone = findPwdPhone.getText().toString().trim();
                String area = forArea.getText().toString().trim();
                vcpop = new VerificationCodePopupWindow(activity, this);
                vcpop.isShowVerification(isEmail, isEmail ? email : phone);
                vcpop.showAtLocation(findViewById(R.id.forget_activity), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                vcpop.setOnPopupListener(this);
                if (isEmail) {
                    presenter.getEmailCode(email);
                } else {
                    presenter.getPhoneCode(area, phone);
                }
                break;
        }
    }

    private void selectorIsPhone(boolean isPhone) {
        if (isPhone) {
            forPhone.setSelected(true);
            forEmail.setSelected(false);
            forPhoneV.setVisibility(View.VISIBLE);
            forEmailV.setVisibility(View.INVISIBLE);
            forPhoneAcc.setVisibility(View.VISIBLE);
            forEmailAcc.setVisibility(View.GONE);
        } else {
            forPhone.setSelected(false);
            forEmail.setSelected(true);
            forPhoneV.setVisibility(View.INVISIBLE);
            forEmailV.setVisibility(View.VISIBLE);
            forPhoneAcc.setVisibility(View.GONE);
            forEmailAcc.setVisibility(View.VISIBLE);
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

            if ((isEmail && !Util.isNullOrEmpty(findPwdEmail.getText().toString().trim())) || (!isEmail && !Util.isNullOrEmpty(findPwdPhone.getText().toString()))) {
                findPwdNext.setEnabled(true);
            } else {
                findPwdNext.setEnabled(false);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RESET_PASSWORD) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        String email = findPwdEmail.getText().toString().trim();
        String phone = findPwdPhone.getText().toString().trim();
        String area = forArea.getText().toString().trim();
        switch (v.getId()) {
            case R.id.pop_eamail_send:
                vcpop.startTime(false);
                presenter.getEmailCode(email);
                break;
            case R.id.pop_send:
                vcpop.startTime(true);
                presenter.getPhoneCode(area, phone);
                break;
        }
    }

    @Override
    public void requestFail(String msg) {
        ToastUtils.showLongToast(activity, msg);

    }

    @Override
    public void codeSuccess(int code) {
        ToastUtils.showLongToast(activity, R.string.phone_sms_send);
    }

    @Override
    public void verificationSuccess(int code) {
        if (code == 0) {
            String email = findPwdEmail.getText().toString().trim();
            String phone = findPwdPhone.getText().toString().trim();
            Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
            intent.putExtra("isEmail", isEmail);
            intent.putExtra("account", isEmail ? email : phone);
            startActivityForResult(intent, Constants.RESET_PASSWORD);
        }
    }

    @Override
    public void resetSuccess(int code) {

    }

    @Override
    public void setAuthCode(String pCode, String eCode) {
        String email = findPwdEmail.getText().toString().trim();
        String phone = findPwdPhone.getText().toString().trim();
        if (isEmail) {
            presenter.verificationEmailCode(email, eCode);
        } else {
            presenter.verificationPhoneCode("86", phone, pCode);
        }
    }
}
