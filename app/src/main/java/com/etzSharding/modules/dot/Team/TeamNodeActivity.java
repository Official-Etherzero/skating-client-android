package com.etzSharding.modules.dot.Team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.adapter.TeamNodeAdapter;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.NodeRevenueBean;
import com.etzSharding.bean.TeamNodeBean;
import com.etzSharding.bean.TeamNodeDataBean;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;
import com.etzSharding.view.MText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeamNodeActivity extends BaseActivity<TeamView, TeamPresenter> implements TeamView {


    @BindView(R.id.team_total)
    MText teamTotal;
    @BindView(R.id.team_personal_num)
    TextView teamPersonalNum;
    @BindView(R.id.team_node_num)
    TextView teamNodeNum;
    @BindView(R.id.team_zrjl)
    TextView teamZrjl;
    @BindView(R.id.team_leve)
    TextView leve;
    @BindView(R.id.team_ztjl)
    TextView ztjl;
    @BindView(R.id.team_node_list)
    RecyclerView teamNodeList;

    private TeamNodeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_node;
    }

    @Override
    public TeamPresenter initPresenter() {
        return new TeamPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.team_node));
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        teamNodeList.setLayoutManager(linearLayoutManager);
        adapter = new TeamNodeAdapter(R.layout.team_node_item, new ArrayList<>());
        teamNodeList.setAdapter(adapter);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                MyNodeBean bean = (MyNodeBean) adapter.getItem(position);
////                startActivity(new Intent(activity, RobotInfoActivity.class).putExtra("robot", bean));
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String token = SharedPrefsUitls.getInstance().getUserToken();
        presenter.getTeamNodeList("100", "1", token);
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

    @OnClick(R.id.team_jlmx)
    public void onViewClicked() {
        startActivity(new Intent(TeamNodeActivity.this, TeamRewardDetailsActivity.class));
    }

    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(activity, msg);
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void setTeamNodeData(TeamNodeDataBean data) {
        MyLog.i("TeamNodeDataBean=" + data.toString());
        if (data != null) {
            teamTotal.setText(data.getTeamReward());
            teamPersonalNum.setText(data.getPeopleCount() + "/" + data.getPeopleCount2());
            teamNodeNum.setText(data.getTeamNodeAmount());
            teamZrjl.setText(data.getRewardYesterday());
            leve.setText(String.format(getString(R.string.team_leve), data.getTeamLevel()));
            adapter.setTokens(data.getUserNodeList());
            int count = Integer.valueOf(Util.isNullOrEmpty(data.getPeopleCount()) ? "0" : data.getPeopleCount());
            switch (count) {
                case 0:
                case 1:
                    ztjl.setText("6%");
                    break;
                case 2:
                    ztjl.setText("7%");
                    break;
                case 3:
                    ztjl.setText("8%");
                    break;
                case 4:
                    ztjl.setText("9%");
                    break;
                case 5:
                    ztjl.setText("10%");
                    break;
                default:
                    ztjl.setText("10%");
                    break;
            }
        }

    }

    @Override
    public void setTeamRewardList(List<NodeRevenueBean> list) {

    }
}
