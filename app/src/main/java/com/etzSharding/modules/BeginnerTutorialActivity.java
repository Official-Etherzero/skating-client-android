package com.etzSharding.modules;

import android.os.Bundle;
import android.view.View;

import com.etzSharding.R;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeginnerTutorialActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_beginner_tutorial;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.beginner_tutorial));

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

    @OnClick({R.id.app_use_tutorial, R.id.etz_deposit_tutorial, R.id.revenue_analysis, R.id.promotion_tutorial, R.id.common_problem})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_use_tutorial:
                break;
            case R.id.etz_deposit_tutorial:
                break;
            case R.id.revenue_analysis:
                break;
            case R.id.promotion_tutorial:
                break;
            case R.id.common_problem:
                break;
        }
    }
}
