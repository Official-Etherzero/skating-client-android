package com.etzSharding.modules.dot.Team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.etzSharding.R;
import com.etzSharding.adapter.RevenueRecordAdapter;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.NodeRevenueBean;
import com.etzSharding.bean.TeamNodeDataBean;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.utils.SharedPrefsUitls;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardDetailsActivity extends BaseActivity<TeamView, TeamPresenter> implements TeamView {


    @BindView(R.id.reward_details_list)
    RecyclerView rewardDetailsList;

    private RevenueRecordAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reward_details;
    }

    @Override
    public TeamPresenter initPresenter() {
        return new TeamPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        int type = getIntent().getIntExtra("Type", 0);
        if (type == 0) {
            setCenterTitle(getString(R.string.node_ctmx));
        } else if (type == 6) {
            setCenterTitle(getString(R.string.node_qdjl));
        }
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rewardDetailsList.setLayoutManager(linearLayoutManager);
        adapter = new RevenueRecordAdapter(R.layout.daily_earnings_item, new ArrayList<>(), 1);
        rewardDetailsList.setAdapter(adapter);
        String token = SharedPrefsUitls.getInstance().getUserToken();
        presenter.getTeamRewardList("100", "1", token, type + "");

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
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void setTeamNodeData(TeamNodeDataBean data) {

    }

    @Override
    public void setTeamRewardList(List<NodeRevenueBean> list) {
        adapter.setTokens(list);
    }
}
