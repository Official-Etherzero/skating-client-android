package com.etzSharding.modules.user.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.security.biometrics.build.U;
import com.etzSharding.R;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.DailyEarningsListBean;
import com.etzSharding.bean.UserBean;
import com.etzSharding.bean.WalletBean;
import com.etzSharding.blockchain.walletutils.WalletUtils;
import com.etzSharding.modules.createrecovery.CreateRecoveryActivity;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.modules.user.ForgetPassword.ForgetPasswordActivity;
import com.etzSharding.modules.user.rigest.RegisterActivity;
import com.etzSharding.modules.walletmanage.WalletsMaster;
import com.etzSharding.modules.walletsetting.WalletSetting;
import com.etzSharding.sqlite.WalletDataStore;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;

import org.web3j.crypto.Wallet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {


    @BindView(R.id.l_email)
    TextView lEmail;
    @BindView(R.id.l_phone)
    TextView lPhone;
    @BindView(R.id.login_area)
    TextView loginArea;
    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_phone_err_hint)
    TextView loginPhoneErrHint;
    @BindView(R.id.cl_phone_acc)
    ConstraintLayout clPhoneAcc;
    @BindView(R.id.login_email)
    EditText loginEmail;
    @BindView(R.id.login_email_err_hint)
    TextView loginEmailErrHint;
    @BindView(R.id.cl_email_acc)
    ConstraintLayout clEmailAcc;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_pwd_err_hint)
    TextView loginPwdErrHint;
    @BindView(R.id.login_pwd_show)
    CheckBox loginPwdShow;
    @BindView(R.id.btn_login)
    TextView btnLogin;

    private boolean isEmail;
    private String phone;
    private String email;
    private String pwd;
    private String area;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        selectorIsPhone(true);
        isEmail = false;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        loginPhone.addTextChangedListener(watcher);
        loginEmail.addTextChangedListener(watcher);
        loginPwd.addTextChangedListener(watcher);
        loginPwdShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    loginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    @Override
    public void requestFail(String msg) {

        ToastUtils.showLongToast(activity, msg);
    }

    @Override
    public void login(UserBean user) {
        if (user != null) {
            SharedPrefsUitls.getInstance().putCurrentAccount(user.getUserID());
//            String address = SharedPrefsUitls.getInstance().getCurrentWalletAddress();
//            if (!Util.isNullOrEmpty(address) && !user.getWalletAddr().equals(address)) {
//                String wid = SharedPrefsUitls.getInstance().getCurrentWallet();
//                if (Util.isNullOrEmpty(wid)) {
//                    SharedPrefsUitls.getInstance().putCurrentWalletAddress("");
//                } else {
//                    WalletBean walletBean = WalletDataStore.getInstance().queryWallet(wid);
//                    SharedPrefsUitls.getInstance().putCurrentWalletAddress(walletBean.getAddress());
//                    WalletsMaster.getInstance().getWalletByIso(activity, wid.substring(0, wid.indexOf("-"))).setmAddress(walletBean.getAddress());
//                }
//
//            }
            if (MainActivity.getApp() != null) MainActivity.getApp().setIndex(0);
            SharedPrefsUitls.getInstance().putUserToken(user.getAccess_Token());
            startActivity(new Intent(activity, MainActivity.class));
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.l_email, R.id.l_phone, R.id.login_del_pwd, R.id.btn_login, R.id.login_forget_pwd, R.id.login_reg, R.id.login_del_phone, R.id.login_del_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.l_email:
                isEmail = true;
                selectorIsPhone(false);
                isBtnEnabled();
                break;
            case R.id.l_phone:
                isEmail = false;
                selectorIsPhone(true);
                isBtnEnabled();
                break;
            case R.id.login_del_phone:
                loginPhone.setText("");
                break;
            case R.id.login_del_email:
                loginEmail.setText("");
                break;
            case R.id.login_del_pwd:
                loginPwd.setText("");
                break;
            case R.id.btn_login:
                phone = loginPhone.getText().toString().trim();
                email = loginEmail.getText().toString().trim();
                pwd = loginPwd.getText().toString().trim();
                area = loginArea.getText().toString();
                if (verifyRegistration(phone, email, pwd)) {
                    if (isEmail) {
                        presenter.loginEmall(email, pwd);
                    } else {
                        presenter.loginPhone(area, phone, pwd);
                    }
                }
                break;
            case R.id.login_forget_pwd:
                startActivity(new Intent(activity, ForgetPasswordActivity.class));
                break;
            case R.id.login_reg:
                startActivity(new Intent(activity, RegisterActivity.class));
                break;
        }
    }

    private void isBtnEnabled() {
        String phone = loginPhone.getText().toString().trim();
        String email = loginEmail.getText().toString().trim();
        String pwd = loginPwd.getText().toString().trim();
        if (!Util.isNullOrEmpty(pwd) && (!Util.isNullOrEmpty(phone) || !Util.isNullOrEmpty(email))) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
    }

    private void selectorIsPhone(boolean isPhone) {
        if (isPhone) {
            lPhone.setSelected(true);
            lEmail.setSelected(false);
            lPhone.setTextSize(24);
            lEmail.setTextSize(15);
            clPhoneAcc.setVisibility(View.VISIBLE);
            clEmailAcc.setVisibility(View.GONE);
        } else {
            lPhone.setSelected(false);
            lEmail.setSelected(true);
            lPhone.setTextSize(15);
            lEmail.setTextSize(24);
            clPhoneAcc.setVisibility(View.GONE);
            clEmailAcc.setVisibility(View.VISIBLE);
        }
    }

    private boolean verifyRegistration(String phone, String email, String pwd) {
        boolean isOk = false;
        if (!isEmail && phone.length() < 4) {
            loginPhoneErrHint.setVisibility(View.VISIBLE);
        } else if (isEmail && !Util.isEmail(email)) {
            loginEmailErrHint.setVisibility(View.VISIBLE);
        } else if (!Util.isPassword(pwd)) {
            loginPhoneErrHint.setVisibility(View.GONE);
            loginEmailErrHint.setVisibility(View.GONE);
            loginPwdErrHint.setVisibility(View.VISIBLE);
        } else {
            loginPhoneErrHint.setVisibility(View.GONE);
            loginEmailErrHint.setVisibility(View.GONE);
            loginPwdErrHint.setVisibility(View.GONE);
            isOk = true;
        }

        return isOk;
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

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
