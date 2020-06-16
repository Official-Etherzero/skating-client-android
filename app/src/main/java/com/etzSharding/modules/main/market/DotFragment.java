package com.etzSharding.modules.main.market;

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
import com.etzSharding.modules.BeginnerTutorialActivity;
import com.etzSharding.modules.dot.InviteFriendsActivity;
import com.etzSharding.modules.dot.MyNode.MyNodeActivity;
import com.etzSharding.modules.dot.Node.AddNodeActivity;
import com.etzSharding.modules.dot.Team.RewardDetailsActivity;
import com.etzSharding.modules.dot.Team.TeamNodeActivity;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.modules.main.property.PropertyPresenter;
import com.etzSharding.modules.main.property.PropertyView;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.modules.walletoperation.receive.ReceiveQrCodeActivity;
import com.etzSharding.modules.walletoperation.send.SendActivity;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;
import com.etzSharding.view.MText;
import com.etzSharding.view.RechargeDialog;
import com.etzSharding.view.TixianDialog;


import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DotFragment extends BaseFragment<PropertyView, PropertyPresenter> implements PropertyView,
        RechargeDialog.OnInputDialogButtonClickListener, TixianDialog.OnConfirmButtonClickListener {


    @BindView(R.id.jiedian_total)
    MText jiedianTotal;
    @BindView(R.id.personal_node_num)
    TextView personalNodeNum;
    @BindView(R.id.personal_node_output)
    TextView personalNodeOutput;
    @BindView(R.id.team_node_num)
    TextView teamNodeNum;
    @BindView(R.id.team_node_output)
    TextView teamNodeOutput;
    Unbinder unbinder;
    private RechargeDialog rechargeDialog;
    private TixianDialog tixianDialog;

    boolean isShow;
    String userToken;
    UserBean user;

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
        return R.layout.fragment_dot;
    }

    @Override
    public void onResume() {
        super.onResume();
        userToken = SharedPrefsUitls.getInstance().getUserToken();
        isShow = true;
        updataNode();

    }

    private void updataNode() {
        presenter.getMyNodeList("10", "1", userToken);
        presenter.getTeamNodeList("10", "1", userToken);
        presenter.getUserInfo(userToken);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser && isShow) {
            updataNode();
        }
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

    @OnClick({R.id.jiedian_cz, R.id.jiedian_tx, R.id.jiedian_banner, R.id.dot_add_node, R.id.dot_invite_friends,
            R.id.dot_personal, R.id.dot_team, R.id.dot_ctmx, R.id.beginner_tutorial})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jiedian_cz:
                if (user == null) return;
                Intent intent = new Intent(getActivity(), ReceiveQrCodeActivity.class);
                intent.putExtra("Address", user.getRechargeAddr());
                startActivity(intent);
                break;
            case R.id.jiedian_tx:
                if (user == null) return;
                tixianDialog = new TixianDialog(getActivity(), user);
                tixianDialog.setOnConfirmButtonClickListener(this);
                tixianDialog.show();
                break;
            case R.id.jiedian_banner:
                if (user == null) return;
                if (!user.getIsTrueName().equals("1")) {
                    ToastUtils.showLongToast(getActivity(), R.string.verified_name_wrz);
                } else {
                    presenter.signin(userToken);
                }

                break;
            case R.id.dot_personal:
                getActivity().startActivity(new Intent(getActivity(), MyNodeActivity.class));
                break;
            case R.id.dot_team:
                getActivity().startActivity(new Intent(getActivity(), TeamNodeActivity.class));
                break;
            case R.id.dot_add_node:
                startActivity(new Intent(getActivity(), AddNodeActivity.class));
                break;
            case R.id.dot_invite_friends:
                getActivity().startActivity(new Intent(getActivity(), InviteFriendsActivity.class));
                break;
            case R.id.dot_ctmx:
                getActivity().startActivity(new Intent(getActivity(), RewardDetailsActivity.class).putExtra("Type", 0));
                break;
            case R.id.beginner_tutorial:
                getActivity().startActivity(new Intent(getActivity(), BeginnerTutorialActivity.class));
                break;
        }
    }

    @Override
    public void onCancel() {
        rechargeDialog.dismiss();
    }

    /**
     * 提现
     *
     * @param pwd
     * @param count
     */
    @Override
    public void onConfirm(String pwd, String count, String address) {
        presenter.withdraw(pwd, userToken, count, address);
        tixianDialog.dismiss();
    }

    /**
     * 充值对话框监听
     *
     * @param count
     */
    @Override
    public void onConfirm(String count) {
        Intent intent = new Intent(getActivity(), SendActivity.class);
        intent.putExtra("iso", getString(R.string.Etz));
        intent.putExtra("toAddress", user.getRechargeAddr());
        intent.putExtra("money", count);
        startActivity(intent);

        rechargeDialog.dismiss();

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
        this.user = user;
        MainActivity.getApp().setUser(user);
        jiedianTotal.setText(user.getETZ());
    }

    @Override
    public void withdrawSuccess(TixanBean bean) {

        jiedianTotal.setText(bean.getETZ());
    }

    @Override
    public void signinFail(int code, String msg) {
        if (code == 1) {
            getActivity().startActivity(new Intent(getActivity(), RewardDetailsActivity.class).putExtra("Type", 6));
        } else if (code == 401) {
            ToastUtils.showLongToast(getActivity(), msg);
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void signinSuccess(String etz) {
        ToastUtils.showLongToast(getActivity(), String.format(getString(R.string.signin_toast), etz));
    }

    @Override
    public void MyNodeData(MyNodeDataBean data) {
        personalNodeNum.setText(data.getTotalCount());
        personalNodeOutput.setText(String.format(getString(R.string.personal_node_zr_output), data.getTotalRewardYesterday()));
    }

    @Override
    public void setTeamNodeData(TeamNodeDataBean data) {
        teamNodeNum.setText(data.getTeamNodeCount());
        teamNodeOutput.setText(String.format(getString(R.string.team_node_zr_oreward), data.getRewardYesterday()));
    }
}
