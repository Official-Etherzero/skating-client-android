package com.etzSharding.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.etzSharding.R;
import com.etzSharding.adapter.HomePagerAdapter;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.base.Constants;
import com.etzSharding.bean.UserBean;
import com.etzSharding.modules.main.market.DotFragment;
import com.etzSharding.modules.main.my.FragmentMy;
import com.etzSharding.modules.main.property.PropertyFragment;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;
import com.etzSharding.modules.walletoperation.send.SendActivity;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;
import com.etzSharding.utils.sysinfo.QMUIStatusBarHelper;
import com.etzSharding.view.NoScrollViewPager;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {

    @BindView(R.id.vp_home)
    NoScrollViewPager vpHome;
    @BindView(R.id.main_daohanglan)
    LinearLayout mainDaohanglan;
    private int index = 0;
    private UserBean user;

    protected static MainActivity mActivity = null;
    private HomePagerAdapter homePagerAdapter;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static MainActivity getApp() {
        return mActivity;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mActivity = this;
        vpHome.setOffscreenPageLimit(2);
        List<Fragment> fragmentList = new ArrayList<>();
//        fragmentList.add(new PropertyFragment());
        fragmentList.add(new DotFragment());
        fragmentList.add(new FragmentMy());
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), fragmentList);
        vpHome.setAdapter(homePagerAdapter);
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

    @OnClick({R.id.main_ll_wallet, R.id.main_ll_dot, R.id.main_ll_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_ll_wallet:
//                index = 0;
//                isClickLL(true, false, false);
//                vpHome.setCurrentItem(0, false);
                break;
            case R.id.main_ll_dot:
                index=0;
                isClickLL( true, false);
                vpHome.setCurrentItem(0, false);
                break;
            case R.id.main_ll_my:
                index = 1;
                isClickLL( false, true);
                vpHome.setCurrentItem(1, false);
                break;
        }
    }

    public void isClickLL(boolean tab01, boolean tab02) {
//        if (tab01) {
//            QMUIStatusBarHelper.setStatusBarDarkMode(this);
//        } else {
            QMUIStatusBarHelper.setStatusBarLightMode(this);
//        }
//        findViewById(R.id.main_ll_wallet).setSelected(tab01);
        findViewById(R.id.main_ll_dot).setSelected(tab01);
        findViewById(R.id.main_ll_my).setSelected(tab02);
    }

    public void isShowNavigationBar(boolean isShow) {
        if (isShow) {
            mainDaohanglan.setVisibility(View.VISIBLE);
        } else {
            mainDaohanglan.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        vpHome.setCurrentItem(index, false);
        switch (index) {
            case 0:
                isClickLL(true, false);
                break;
            case 1:
                isClickLL(false, true);
                break;
//            case 2:
//                isClickLL(false, false, true);
//                break;
        }

    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        MyLog.i("**************" + requestCode);
        if (data == null) return;
        switch (requestCode) {
            case Constants.SCANNER_REQUEST:
                String result = data.getStringExtra("result");
                MyLog.i("**************" + result);

                if (!Util.isNullOrEmpty(result) && Util.isAddressValid(result)) {
                    Intent intent = new Intent(this, SendActivity.class);
                    intent.putExtra("toAddress", result);
                    intent.putExtra("iso", "ETZ");
                    startActivity(intent);
                } else {
                    ToastUtils.showLongToast(activity, R.string.Send_invalidAddressTitle);
                }


                break;
        }
    }


}
