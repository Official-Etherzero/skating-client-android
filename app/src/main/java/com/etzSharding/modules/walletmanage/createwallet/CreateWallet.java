package com.etzSharding.modules.walletmanage.createwallet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.base.Constants;
import com.etzSharding.bean.WalletBean;
import com.etzSharding.data.RemoteDataSource;
import com.etzSharding.modules.walletmanage.WalletsMaster;
import com.etzSharding.modules.walletmanage.importwallet.ImportWallet;
import com.etzSharding.modules.walletoperation.wallet.WalletActivity;
import com.etzSharding.modules.walletsetting.BackupMnemonic.MnemonicBackupActivity;
import com.etzSharding.modules.walletsetting.WalletSetting;
import com.etzSharding.utils.ETZKeyStore;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;
import com.etzSharding.view.LoadingDialog;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateWallet extends BaseActivity<CreateWalletView, CreateWalletPresenter> implements CreateWalletView {
//public class CreateWallet extends BaseActivity<CreateWalletView, CreateWalletPresenter> implements CreateWalletView{

    private static final int CREATE_WALLET_RESULT = 2202;
    private static final int LOAD_WALLET_REQUEST = 1101;
    private static final int REQUEST_WRITE_STORAGE = 112;
    @BindView(R.id.et_wallet_name)
    EditText etWalletName;
    @BindView(R.id.et_wallet_pwd)
    EditText etWalletPwd;
    @BindView(R.id.et_wallet_pwd_again)
    EditText etWalletPwdAgain;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;
    @BindView(R.id.lly_wallet_agreement)
    LinearLayout llyWalletAgreement;
    @BindView(R.id.btn_create_wallet)
    TextView btnCreateWallet;
    @BindView(R.id.btn_input_wallet)
    TextView btnInputWallet;

    private CreateWalletInteract createWalletInteract;
    private LoadingDialog loadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_wallet;
    }

    @Override
    public CreateWalletPresenter initPresenter() {
        return new CreateWalletPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.create_recovery_create));

        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnCreateWallet.setEnabled(isChecked);
            }
        });

    }

    @Override
    protected void initData() {
        createWalletInteract = new CreateWalletInteract();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void getWalletSuccess(String address) {

    }

    @Override
    public void getWalletFail(String msg) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.cb_agreement, R.id.btn_create_wallet, R.id.btn_input_wallet, R.id.lly_wallet_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_agreement:
                break;
            case R.id.btn_create_wallet:
                String walletName = etWalletName.getText().toString().trim();
                String walletPwd = etWalletPwd.getText().toString().trim();
                String confirmPwd = etWalletPwdAgain.getText().toString().trim();
                boolean verifyWalletInfo = presenter.verifyInfo(activity, walletName, walletPwd, confirmPwd);
                if (verifyWalletInfo) {
                    loadingDialog = new LoadingDialog(activity);
                    loadingDialog.show();
                    createWalletInteract.create(walletName, walletPwd, confirmPwd).subscribe(this::jumpToWalletBackUp, this::showError);
                }
                break;
            case R.id.btn_input_wallet:
                Intent intent = new Intent(CreateWallet.this, ImportWallet.class);
                intent.putExtra(Constants.WALLET_TYPE, "ETZ");
                intent.putExtra("from", 1);
                startActivity(intent);
                break;
            case R.id.lly_wallet_agreement:
                if (cbAgreement.isChecked()) {
                    cbAgreement.setChecked(false);
                } else {
                    cbAgreement.setChecked(true);
                }
                break;
        }
    }

    public void showError(Throwable errorInfo) {
        loadingDialog.dismiss();
        MyLog.i("---showError----" + errorInfo.getMessage());
        ToastUtils.showLongToast(activity, errorInfo.getMessage());
    }

    public void jumpToWalletBackUp(WalletBean wallet) {
        ToastUtils.showLongToast(activity, R.string.create_wallet_ok);
        loadingDialog.dismiss();
//        if (getIntent().getIntExtra("from", 0) != 0) {
        String wid = wallet.getId();
        WalletsMaster.getInstance().getWalletByIso(activity, wid.substring(0, wid.indexOf("-"))).setmAddress(wallet.getAddress());
        String phrase = "";
        try {
            phrase = ETZKeyStore.decodetData(wallet.getMnemonic());
            MyLog.i("DeterministicSeed=====" + phrase);

        } catch (Exception e) {
            e.printStackTrace();
        }
        bindWallet(wallet);
        Intent intent = new Intent(this, MnemonicBackupActivity.class);
        intent.putExtra("from", getIntent().getIntExtra("from", 0));
        intent.putExtra("walletId", wallet.getId());
        intent.putExtra("walletMnemonic", phrase);
        startActivity(intent);
//        }
        finish();
    }

    private void bindWallet(WalletBean wallet) {
        String userToken = SharedPrefsUitls.getInstance().getUserToken();
        if (!Util.isNullOrEmpty(userToken)) {
            presenter.bindWallet(userToken, wallet.getAddress());
        }
    }
}
