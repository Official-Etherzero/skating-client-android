package com.etzSharding.modules.dot.Team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.adapter.RevenueRecordAdapter;
import com.etzSharding.base.BaseFragment;
import com.etzSharding.bean.NodeRevenueBean;
import com.etzSharding.bean.TeamNodeDataBean;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentDetailsList extends BaseFragment<TeamView, TeamPresenter> implements TeamView {


    @BindView(R.id.rc_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.list_nologs)
    TextView listNologs;

    private RevenueRecordAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public TeamPresenter initPresenter() {
        return new TeamPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RevenueRecordAdapter(R.layout.daily_earnings_item, new ArrayList<>(), 1);
        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//            }
//        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.listview_layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!Util.isNullOrEmpty(type)) {
            presenter.getTeamRewardList("100", "1", SharedPrefsUitls.getInstance().getUserToken(), type);
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

    @Override
    public void requestFail(int code, String msg) {
        if (code == 401) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void setTeamNodeData(TeamNodeDataBean data) {

    }

    @Override
    public void setTeamRewardList(List<NodeRevenueBean> list) {

        if (list != null && list.size() > 0) {
            listNologs.setVisibility(View.GONE);
            adapter.setTokens(list);
        } else {
            listNologs.setVisibility(View.VISIBLE);
        }
    }
}
