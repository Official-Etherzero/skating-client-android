package com.etzSharding.modules.createrecovery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etzSharding.R;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.modules.user.rigest.RegisterActivity;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.Util;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRecoveryActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_recovery;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        String userToken= SharedPrefsUitls.getInstance().getUserToken();
        if (!Util.isNullOrEmpty(userToken)){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
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


    @OnClick({R.id.button_login, R.id.button_rigest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                startActivity(new Intent(this, LoginActivity.class));
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.button_rigest:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
