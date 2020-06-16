package com.etzSharding.modules.user.VerifiedName;

import android.os.Bundle;

import com.etzSharding.R;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;

public class OtherUsersVerifiedActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_users_verified;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
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
}
