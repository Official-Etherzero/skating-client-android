package com.etzSharding.modules.dot.MyNode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.etzSharding.R;
import com.etzSharding.adapter.MyNodeListAdapter;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.MyNodeBean;
import com.etzSharding.bean.MyNodeDataBean;
import com.etzSharding.bean.NodeRevenueDataBean;
import com.etzSharding.modules.dot.Node.AddNodeActivity;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.view.MText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyNodeActivity extends BaseActivity<MyNodeView, MyNodePresenter> implements MyNodeView {


    @BindView(R.id.jiedian_total)
    MText jiedianTotal;
    @BindView(R.id.node_total)
    TextView nodeTotal;
    @BindView(R.id.node_zr_output)
    TextView nodeZrOutput;
    @BindView(R.id.my_node_list)
    RecyclerView myNodeList;
    @BindView(R.id.my_add_node)
    TextView myAddNode;
    private MyNodeListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_node;
    }

    @Override
    public MyNodePresenter initPresenter() {
        return new MyNodePresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.personal_node));
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myNodeList.setLayoutManager(linearLayoutManager);
        adapter = new MyNodeListAdapter(R.layout.my_node_item, new ArrayList<>());
        myNodeList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyNodeBean bean = (MyNodeBean) adapter.getItem(position);
                startActivity(new Intent(activity, OutputDetailsActivity.class).putExtra("MyNode", bean));
            }
        });
        MyLog.i("token="+SharedPrefsUitls.getInstance().getUserToken());


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getMyNodeList("100","1", SharedPrefsUitls.getInstance().getUserToken());
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

    @OnClick(R.id.my_add_node)
    public void onViewClicked() {
        startActivity(new Intent(MyNodeActivity.this, AddNodeActivity.class));
    }

    @Override
    public void requestFail(int code, String msg) {
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void MyNodeData(MyNodeDataBean data) {
        if (data != null) {
            jiedianTotal.setText(data.getTotalReward());
            nodeZrOutput.setText(data.getTotalRewardYesterday());
            nodeTotal.setText(data.getTotalCount());
            adapter.setTokens(data.getUserNodeList());
        }

    }

    @Override
    public void NodeRevenueData(NodeRevenueDataBean data) {

    }
}
