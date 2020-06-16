package com.etzSharding.modules.user.VerifiedName;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.security.cloud.CloudRealIdentityTrigger;
import com.alibaba.security.realidentity.ALRealIdentityCallback;
import com.alibaba.security.realidentity.ALRealIdentityResult;
import com.etzSharding.R;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.AuthenticationBean;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;
import com.etzSharding.view.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifiedNameActivity extends BaseActivity<VerifiedView, VerfiiedPresenter> implements VerifiedView {


    LoadingDialog loadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verified;
    }

    @Override
    public VerfiiedPresenter initPresenter() {
        return new VerfiiedPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.verified_name));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(activity, msg);
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void setVerifyToken(AuthenticationBean bean) {
        loadingDialog.dismiss();
//        CloudRealIdentityTrigger.startVerifyByNative(activity, bean.getVerifyToken(), getALRealIdentityCallback());
        CloudRealIdentityTrigger.start(activity, bean.getVerifyToken(), getALRealIdentityCallback());
    }

    @Override
    public void setVerifyResult(AuthenticationBean bean) {
        MyLog.i("RPSDKALRealIdentityResult:" + bean.getVerifyStatus());
        ToastUtils.showLongToast(activity, bean.getVerifyStatus());

    }

    @OnClick({R.id.verified_china_users, R.id.verified_other_users})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verified_china_users:
                loadingDialog = new LoadingDialog(activity);
                loadingDialog.show();
                presenter.getAliDescribeVerifyToken(SharedPrefsUitls.getInstance().getUserToken());
                break;
            case R.id.verified_other_users:
                ToastUtils.showLongToast(activity, R.string.other_users_hint);
                break;
        }
    }

    /**
     * 基础回调的方式 TODO
     *
     * @return
     */
    private ALRealIdentityCallback getALRealIdentityCallback() {
        return new ALRealIdentityCallback() {
            @Override
            public void onAuditResult(ALRealIdentityResult alRealIdentityResult, String s) {
                //DO your things
                MyLog.i("RPSDKALRealIdentityResult:" + alRealIdentityResult.audit + "==s=" + s);
                presenter.getAliDescribeVerifyResult(SharedPrefsUitls.getInstance().getUserToken());
                if (alRealIdentityResult.audit == 1) {
                    finish();
                    ToastUtils.showLongToast(activity,R.string.name_renzhen_success);
                }
            }
        };
    }
}
