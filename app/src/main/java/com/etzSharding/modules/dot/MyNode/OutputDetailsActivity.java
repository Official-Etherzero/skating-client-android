package com.etzSharding.modules.dot.MyNode;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.adapter.RevenueRecordAdapter;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.MyNodeBean;
import com.etzSharding.bean.MyNodeDataBean;
import com.etzSharding.bean.NodeRevenueDataBean;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.SharedPrefsUitls;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutputDetailsActivity extends BaseActivity<MyNodeView, MyNodePresenter> implements MyNodeView {


    @BindView(R.id.my_node_class)
    TextView myNodeClass;
    @BindView(R.id.node_ljsy)
    TextView nodeLjsy;
    @BindView(R.id.node_zrsy)
    TextView nodeZrsy;
    @BindView(R.id.node_jldq)
    TextView nodeJldq;
    @BindView(R.id.output_details_list)
    RecyclerView outputDetailsList;
    @BindView(R.id.mynode_bg)
    ConstraintLayout mynodeBg;

    private RevenueRecordAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private MyNodeBean bean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_output_details;
    }

    @Override
    public MyNodePresenter initPresenter() {
        return new MyNodePresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.output_details));
        bean = getIntent().getParcelableExtra("MyNode");
        int miniId = Integer.valueOf(bean.getMiniID());
        switch (miniId) {
            case 1:
                mynodeBg.setBackgroundResource(R.mipmap.tiyan_geren);
                myNodeClass.setText(R.string.tiyan_node);
                break;
            case 2:
                mynodeBg.setBackgroundResource(R.mipmap.tiyan_geren);
                myNodeClass.setText(R.string.novice_node);
                break;
            case 3:
                mynodeBg.setBackgroundResource(R.mipmap.chuji);
                myNodeClass.setText(R.string.primary_node);
                break;
            case 4:
                mynodeBg.setBackgroundResource(R.mipmap.zhongji);
                myNodeClass.setText(R.string.intermediate_node);
                break;
            case 5:
                mynodeBg.setBackgroundResource(R.mipmap.gaoji);
                myNodeClass.setText(R.string.advanced_node);
                break;
        }
        nodeLjsy.setText(bean.getReward());
        nodeZrsy.setText("+" + bean.getRewardYesterday());
        nodeJldq.setText(String.format(getString(R.string.day), bean.getRestOfDay()));
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        outputDetailsList.setLayoutManager(linearLayoutManager);
        adapter = new RevenueRecordAdapter(R.layout.daily_earnings_item, new ArrayList<>(),0);
        outputDetailsList.setAdapter(adapter);
        MyLog.i("NodeID==========="+bean.getNodeID());
        MyLog.i("NodeID=1=========="+ SharedPrefsUitls.getInstance().getUserToken());
        presenter.getNodeRevenueList(bean.getNodeID(),"10","1", SharedPrefsUitls.getInstance().getUserToken());

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
    public void MyNodeData(MyNodeDataBean data) {

    }

    @Override
    public void NodeRevenueData(NodeRevenueDataBean data) {
        MyLog.i("NodeRevenueDataBean="+data.toString());
        if (data!=null)
            adapter.setTokens(data.getUserNodeList());


    }
}
