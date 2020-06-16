package com.etzSharding.modules.main.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.base.BaseFragment;
import com.etzSharding.bean.MyNodeDataBean;
import com.etzSharding.bean.TeamNodeDataBean;
import com.etzSharding.bean.TixanBean;
import com.etzSharding.bean.UserBean;
import com.etzSharding.modules.dot.InviteFriendsActivity;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.modules.main.property.PropertyPresenter;
import com.etzSharding.modules.main.property.PropertyView;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.modules.user.Seting.UserSetingActivity;
import com.etzSharding.modules.user.VerifiedName.VerifiedNameActivity;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentMy extends BaseFragment<PropertyView, PropertyPresenter> implements PropertyView {

    @BindView(R.id.my_user_account)
    TextView myUserAccount;
    @BindView(R.id.my_user_uid)
    TextView myUserUid;
    @BindView(R.id.my_tv_shiming_status)
    TextView myTvShimingStatus;
    Unbinder unbinder;
    UserBean userBean;
    boolean isShow;

    @Override
    public PropertyPresenter initPresenter() {
        return new PropertyPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {


    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
        presenter.getUserInfo(SharedPrefsUitls.getInstance().getUserToken());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.my_rl_smrz, R.id.my_rl_aqzx, R.id.my_rl_phsz, R.id.my_rl_yqhy, R.id.my_rl_ggzx, R.id.exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_rl_smrz:
                if (userBean.getIsTrueName().equals("1")) return;
                startActivity(new Intent(getActivity(), VerifiedNameActivity.class));
                break;
            case R.id.my_rl_aqzx:
                break;
            case R.id.my_rl_phsz:
                startActivity(new Intent(getActivity(), UserSetingActivity.class));
                break;
            case R.id.my_rl_yqhy:
                startActivity(new Intent(getActivity(), InviteFriendsActivity.class));
                break;
            case R.id.my_rl_ggzx:
                break;
            case R.id.exit_login:
                SharedPrefsUitls.getInstance().putUserToken("");
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser && isShow) {
            if (userBean != null) {
                myUserAccount.setText(userBean.getAccount());
                myUserUid.setText("UID：" + userBean.getUserID());
                if (userBean.getIsTrueName().equals("0")) {
                    myTvShimingStatus.setText(R.string.my_name_cunverified);
                } else {
                    myTvShimingStatus.setText(R.string.my_name_authenticated);
                }
            }
        }
    }

    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(getActivity(), msg);
        if (code == 401) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void userInfo(UserBean user) {
        userBean=user;
        if (user.getIsTrueName().equals("0")) {
            myTvShimingStatus.setText(R.string.my_name_cunverified);
        } else {
            myTvShimingStatus.setText(R.string.my_name_authenticated);
        }

    }

    @Override
    public void withdrawSuccess(TixanBean bean) {

    }

    @Override
    public void signinFail(int code, String msg) {

    }

    @Override
    public void signinSuccess(String etz) {

    }

    @Override
    public void MyNodeData(MyNodeDataBean data) {

    }

    @Override
    public void setTeamNodeData(TeamNodeDataBean data) {

    }
}
