package com.etzSharding.modules.user.rigest;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.UserBean;
import com.etzSharding.blockchain.walletutils.WalletUtils;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterView, RegisterPresenter> implements RegisterView {


    @BindView(R.id.r_phone)
    TextView rPhone;
    @BindView(R.id.r_email)
    TextView rEmail;
    @BindView(R.id.reg_area)
    TextView regArea;
    @BindView(R.id.reg_phone)
    EditText regPhone;
    @BindView(R.id.phone_err_hint)
    TextView phoneErrHint;
    @BindView(R.id.cl_phone)
    ConstraintLayout clPhone;
    @BindView(R.id.reg_email)
    EditText regEmail;
    @BindView(R.id.email_err_hint)
    TextView emailErrHint;
    @BindView(R.id.cl_email)
    ConstraintLayout clEmail;
    @BindView(R.id.reg_pwd)
    EditText regPwd;
    @BindView(R.id.pwd_err_hint)
    TextView pwdErrHint;
    @BindView(R.id.pwd_show)
    CheckBox pwdShow;
    @BindView(R.id.reg_affirm_pwd)
    EditText regAffirmPwd;
    @BindView(R.id.affirm_pwd_show)
    CheckBox affirmPwdShow;
    @BindView(R.id.pwd_affirm_err_hint)
    TextView pwdAffirmErrHint;
    @BindView(R.id.reg_auth_code)
    EditText regAuthCode;
    @BindView(R.id.auth_code_err_hint)
    TextView authCodeErrHint;
    @BindView(R.id.tv_auth_code)
    TextView tvAuthCode;
    @BindView(R.id.reg_invitation_code)
    EditText regInvitationCode;
    @BindView(R.id.btn_register)
    TextView btnRegister;


    private boolean isEmail;
    public TimeCount time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rigest;
    }

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        selectorIsPhone(true);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        regPhone.addTextChangedListener(watcher);
        regEmail.addTextChangedListener(watcher);
        regPwd.addTextChangedListener(watcher);
        regAffirmPwd.addTextChangedListener(watcher);
        regAuthCode.addTextChangedListener(watcher);

        pwdShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    regPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    regPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });
        affirmPwdShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    regAffirmPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    regAffirmPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.r_close, R.id.r_phone, R.id.r_email, R.id.reg_area, R.id.tv_auth_code, R.id.btn_register, R.id.reg_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.r_close:
                finish();
                break;
            case R.id.r_phone:
                isEmail = false;
                selectorIsPhone(true);
                isBtnEnabled();
                break;
            case R.id.r_email:
                isEmail = true;
                selectorIsPhone(false);
                isBtnEnabled();
                break;
            case R.id.reg_area:
                break;
            case R.id.tv_auth_code:
                if (isEmail) {
                    String mail = regEmail.getText().toString().trim();
                    if (!Util.isEmail(mail)) {
                        ToastUtils.showLongToast(RegisterActivity.this, R.string.input_email_err);
                        emailErrHint.setVisibility(View.VISIBLE);
                        return;
                    }
                    emailErrHint.setVisibility(View.GONE);
                    presenter.getEmailCode(mail);

                } else {
                    String area = regArea.getText().toString();
                    String phone = regPhone.getText().toString().trim();
                    if (phone.length() < 4) {
                        ToastUtils.showLongToast(RegisterActivity.this, R.string.input_phone_err);
                        phoneErrHint.setVisibility(View.VISIBLE);
                        return;
                    }
                    phoneErrHint.setVisibility(View.GONE);
                    presenter.getPhoneCode(area, phone);
                }

                break;
            case R.id.btn_register:
                String area = regArea.getText().toString();
                String phone = regPhone.getText().toString().trim();
                String email = regEmail.getText().toString().trim();
                String pwd = regPwd.getText().toString().trim();
                String affirmPwd = regAffirmPwd.getText().toString().trim();
                String code = regAuthCode.getText().toString().trim();
                String invite_code = regInvitationCode.getText().toString().trim();
                if (verifyRegistration(phone, email, pwd, affirmPwd, code)) {
                    if (isEmail) {
                        presenter.emailRegister(pwd, invite_code, email, code);
                    } else {
                        presenter.phoneRegister(pwd, invite_code, phone, code);
                    }
                }
                break;
            case R.id.reg_login:
                startActivity(new Intent(activity, LoginActivity.class));
                finish();
                break;
        }
    }

    private void selectorIsPhone(boolean isPhone) {
        if (isPhone) {
            rPhone.setSelected(true);
            rEmail.setSelected(false);
            rPhone.setTextSize(24);
            rEmail.setTextSize(15);
            clPhone.setVisibility(View.VISIBLE);
            clEmail.setVisibility(View.GONE);
        } else {
            rPhone.setSelected(false);
            rEmail.setSelected(true);
            rPhone.setTextSize(15);
            rEmail.setTextSize(24);
            clPhone.setVisibility(View.GONE);
            clEmail.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void requestFail(String msg) {
        ToastUtils.showLongToast(activity, msg);
    }

    @Override
    public void codeSuccess(int code) {
        if (code == 0) {
            ToastUtils.showLongToast(activity, R.string.phone_sms_send);
            time = new TimeCount(60000, 1000);
            time.start();// 开始计时
        }

    }

    @Override
    public void RegisterSuccess(UserBean user) {
        if (user != null) {
            SharedPrefsUitls.getInstance().putCurrentAccount(user.getUserID());
//            SharedPrefsUitls.getInstance().putCurrentWalletAddress("");
            SharedPrefsUitls.getInstance().putUserToken(user.getAccess_Token());
            startActivity(new Intent(activity, MainActivity.class));
            finish();
        }

    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tvAuthCode.setTextColor(getResources().getColor(
                    R.color.zt_main));
            tvAuthCode.setText("获取验证码");
            tvAuthCode.setClickable(true);

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            tvAuthCode.setClickable(false);
            tvAuthCode.setTextColor(getResources().getColor(
                    R.color.zt_black_30));
            tvAuthCode.setText(millisUntilFinished / 1000 + " s");
        }
    }

    @Override
    protected void onStop() {
        if (time != null)
            time.cancel();
        super.onStop();

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
            isBtnEnabled();
        }
    };

    private void isBtnEnabled() {
        String phone = regPhone.getText().toString().trim();
        String email = regEmail.getText().toString().trim();
        String pwd = regPwd.getText().toString().trim();
        String affirmPwd = regAffirmPwd.getText().toString().trim();
        String code = regAuthCode.getText().toString().trim();
        if (!Util.isNullOrEmpty(pwd) && !Util.isNullOrEmpty(affirmPwd) && !Util.isNullOrEmpty(code)
                && ((!Util.isNullOrEmpty(phone) && !isEmail) || (!Util.isNullOrEmpty(email) && isEmail))) {
            btnRegister.setEnabled(true);
        } else {
            btnRegister.setEnabled(false);
        }
    }

    private boolean verifyRegistration(String phone, String email, String pwd, String affirmPwd, String code) {
        boolean isOk = false;
        if (!isEmail && phone.length() < 4) {
            phoneErrHint.setVisibility(View.VISIBLE);
        } else if (isEmail && !Util.isEmail(email)) {
            emailErrHint.setVisibility(View.VISIBLE);
        } else if (!Util.isPassword(pwd)) {
            phoneErrHint.setVisibility(View.GONE);
            emailErrHint.setVisibility(View.GONE);
            pwdErrHint.setVisibility(View.VISIBLE);
        } else if (!pwd.equals(affirmPwd)) {
            phoneErrHint.setVisibility(View.GONE);
            emailErrHint.setVisibility(View.GONE);
            pwdErrHint.setVisibility(View.GONE);
            pwdAffirmErrHint.setVisibility(View.VISIBLE);
        } else if (code.length() < 6) {
            phoneErrHint.setVisibility(View.GONE);
            emailErrHint.setVisibility(View.GONE);
            pwdErrHint.setVisibility(View.GONE);
            pwdAffirmErrHint.setVisibility(View.GONE);
            authCodeErrHint.setVisibility(View.VISIBLE);
        } else {
            phoneErrHint.setVisibility(View.GONE);
            emailErrHint.setVisibility(View.GONE);
            pwdErrHint.setVisibility(View.GONE);
            pwdAffirmErrHint.setVisibility(View.GONE);
            authCodeErrHint.setVisibility(View.GONE);
            isOk = true;
        }

        return isOk;
    }
}
