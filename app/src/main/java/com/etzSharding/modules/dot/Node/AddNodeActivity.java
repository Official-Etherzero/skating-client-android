package com.etzSharding.modules.dot.Node;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.etzSharding.R;
import com.etzSharding.adapter.NodeListAdapter;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.NodeBean;
import com.etzSharding.modules.dot.Team.RewardDetailsActivity;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.utils.Md5Utils;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;
import com.etzSharding.view.NodeBuyDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNodeActivity extends BaseActivity<NodeView, NodePresenter> implements NodeView, NodeBuyDialog.OnConfirmClickListener {


    @BindView(R.id.first_year_yield)
    TextView firstYearYield;
    @BindView(R.id.node_cycle)
    TextView nodeCycle;
    @BindView(R.id.add_node_list)
    RecyclerView addNodeList;
    @BindView(R.id.tiyan_btn)
    TextView tiyanBtn;

    private NodeListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private NodeBean node;
    private NodeBuyDialog dialog;

    @Override

    protected int getLayoutId() {
        return R.layout.activity_add_node;
    }

    @Override
    public NodePresenter initPresenter() {
        return new NodePresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.add_node));
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        addNodeList.setLayoutManager(linearLayoutManager);
        adapter = new NodeListAdapter(R.layout.node_item_layout, new ArrayList<>(), null);
        addNodeList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NodeBean bean = (NodeBean) adapter.getItem(position);
                startActivity(new Intent(activity, NodeIntroductionActivity.class).putExtra("Node", bean));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getNodeList();
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

    @OnClick({R.id.tiyan_btn, R.id.add_bnaner})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tiyan_btn:
                if (node != null) {
                    dialog = new NodeBuyDialog(activity, node);
                    dialog.setOnConfirmClickListener(this);
                    dialog.show();
                }
                break;
            case R.id.add_bnaner:
                String token = SharedPrefsUitls.getInstance().getUserToken();
                presenter.signin(token);
                break;
        }
    }

    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(activity, msg);
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }

    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void NodeData(List<NodeBean> datas) {
        if (datas.get(0).getMType().equals("2")) {
            node = datas.get(0);
            firstYearYield.setText(node.getEarnings() + "%");
            nodeCycle.setText(String.format(getString(R.string.day), node.getPeriod()));
            tiyanBtn.setText(String.format(getString(R.string.node_1000_tiyan), node.getInput()));
            datas.remove(0);
        }
        adapter.setTokens(datas);

    }

    @Override
    public void buyNode(int code) {
        dialog.dismiss();
    }

    @Override
    public void signinSuccess(String etz) {
        ToastUtils.showLongToast(activity, String.format(getString(R.string.signin_toast), etz));
    }

    @Override
    public void signinFail(int code, String msg) {
        if (code == 1) {
            startActivity(new Intent(activity, RewardDetailsActivity.class).putExtra("Type", 6));
        } else if (code == 401) {
            ToastUtils.showLongToast(activity, msg);
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void setConfirmClick(String pwd, String miniId) {
        String token = SharedPrefsUitls.getInstance().getUserToken();
        presenter.BuyNode(token, node.getMiniID(), pwd);
    }
}
