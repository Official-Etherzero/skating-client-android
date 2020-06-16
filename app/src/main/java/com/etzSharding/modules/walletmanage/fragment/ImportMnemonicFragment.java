package com.etzSharding.modules.walletmanage.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.app.MyApp;
import com.etzSharding.base.BaseFragment;
import com.etzSharding.base.Constants;
import com.etzSharding.bean.WalletBean;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.modules.walletmanage.WalletsMaster;
import com.etzSharding.modules.walletmanage.createwallet.CreateWalletInteract;
import com.etzSharding.modules.walletmanage.createwallet.CreateWalletPresenter;
import com.etzSharding.modules.walletmanage.createwallet.CreateWalletView;
import com.etzSharding.sqlite.WalletDataStore;
import com.etzSharding.utils.MnemonicUtils;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;
import com.etzSharding.view.CustomToast;
import com.etzSharding.view.LoadingDialog;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ImportMnemonicFragment extends BaseFragment<CreateWalletView, CreateWalletPresenter> implements CreateWalletView {

    @BindView(R.id.et_mnemonic)
    EditText etMnemonic;
    @BindView(R.id.et_wallet_pwd)
    EditText etWalletPwd;
    @BindView(R.id.et_wallet_pwd_again)
    EditText etWalletPwdAgain;
    Unbinder unbinder;
    @BindView(R.id.rw_wallet_name)
    EditText rwWalletName;
    @BindView(R.id.btn_load_wallet)
    TextView btnLoadWallet;

    private String walletType;
    private CreateWalletInteract createWalletInteract;
    private LoadingDialog loadingDialog;

    @Override
    public CreateWalletPresenter initPresenter() {
        return new CreateWalletPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        walletType = getActivity().getIntent().getStringExtra(Constants.WALLET_TYPE);
        createWalletInteract = new CreateWalletInteract();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        etMnemonic.addTextChangedListener(textWatcher);
        etWalletPwd.addTextChangedListener(textWatcher);
        etWalletPwdAgain.addTextChangedListener(textWatcher);
        rwWalletName.addTextChangedListener(textWatcher);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_import_mnemonic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("CheckResult")
    @OnClick({ R.id.btn_load_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load_wallet:
                String mnemonic = etMnemonic.getText().toString().trim();
                String walletName = rwWalletName.getText().toString().trim();
                String walletPwd = etWalletPwd.getText().toString().trim();
                String confirmPwd = etWalletPwdAgain.getText().toString().trim();
                if (!MnemonicUtils.validateMnemonic(mnemonic)) {
                    ToastUtils.showLongToast(getActivity(),getResources().getString(R.string.load_wallet_mnemonic_err));
                    return;
                }
                if (!Util.isNullOrEmpty(mnemonic) && WalletDataStore.getInstance().mnemonicQueryWallet(walletType,mnemonic)) {
                    ToastUtils.showLongToast(getActivity(),getResources().getString(R.string.load_wallet_exist));
                    return;
                }
                List<String> list = Arrays.asList(mnemonic.split(" "));
                boolean verifyWalletInfo = presenter.verifyInfo(getActivity(),walletName, walletPwd, confirmPwd);
                boolean verifyMnemonic = presenter.verifyMnemonic(getActivity(),list);
                if (verifyWalletInfo && verifyMnemonic) {
                    loadingDialog = new LoadingDialog(getActivity());
                    loadingDialog.show();
                    createWalletInteract.loadWalletByMnemonic("", list, walletPwd, walletName, walletType).subscribe(this::jumpToWalletBackUp, this::showError);
                }
                break;
        }
    }

    public void showError(Throwable errorInfo) {
        loadingDialog.dismiss();
        ToastUtils.showLongToast(getActivity(),errorInfo.toString());
    }

    public void jumpToWalletBackUp(WalletBean wallet) {
        loadingDialog.dismiss();
        ToastUtils.showLongToast(getActivity(),getResources().getString(R.string.load_wallet_ok));

        WalletsMaster.getInstance().getWalletByIso(getActivity(), wallet.getId().substring(0,wallet.getId().indexOf("-") )).setmAddress(wallet.getAddress());
        if (getActivity().getIntent().getIntExtra("from", 0) != 0) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        getActivity().finish();
    }

    @Override
    public void getWalletSuccess(String address) {

    }

    @Override
    public void getWalletFail(String msg) {

    }
    TextWatcher textWatcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!Util.isNullOrEmpty(etMnemonic.getText().toString())
                    &&!Util.isNullOrEmpty(etWalletPwd.getText().toString())
                    &&!Util.isNullOrEmpty(etWalletPwdAgain.getText().toString())
                    &&!Util.isNullOrEmpty(rwWalletName.getText().toString())){
                btnLoadWallet.setEnabled(true);
            }else {
                btnLoadWallet.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
