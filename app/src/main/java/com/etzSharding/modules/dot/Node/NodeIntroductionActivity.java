package com.etzSharding.modules.dot.Node;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.app.MyApp;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.NodeBean;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.view.NodeBuyDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NodeIntroductionActivity extends BaseActivity<NodeView, NodePresenter> implements NodeView, NodeBuyDialog.OnConfirmClickListener {


    @BindView(R.id.node_class)
    TextView nodeClass;
    @BindView(R.id.node_snsyl)
    TextView nodeSnsyl;
    @BindView(R.id.node_cycle)
    TextView nodeCycle;
    @BindView(R.id.node_sj)
    TextView nodeSj;
    @BindView(R.id.node_item_cl)
    ConstraintLayout nodeItemCl;

    private NodeBean node;
    private NodeBuyDialog buyDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_node_introduction;
    }

    @Override
    public NodePresenter initPresenter() {
        return new NodePresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle("");
        node = getIntent().getParcelableExtra("Node");
        int miniId = Integer.valueOf(node.getMType());
        switch (miniId) {
            case 1:
                nodeItemCl.setBackgroundResource(R.mipmap.tiyan_geren);
                nodeClass.setText(R.string.tiyan_node);
                break;
            case 2:
                nodeItemCl.setBackgroundResource(R.mipmap.tiyan_geren);
                nodeClass.setText(R.string.novice_node);
                break;
            case 3:
                nodeItemCl.setBackgroundResource(R.mipmap.chuji);
                nodeClass.setText(R.string.primary_node);
                break;
            case 4:
                nodeItemCl.setBackgroundResource(R.mipmap.zhongji);
                nodeClass.setText(R.string.intermediate_node);
                break;
            case 5:
                nodeItemCl.setBackgroundResource(R.mipmap.gaoji);
                nodeClass.setText(R.string.advanced_node);
                break;
        }
        nodeSnsyl.setText(node.getEarnings() + "%");
        nodeCycle.setText(String.format(getString(R.string.day), node.getPeriod()));
        nodeSj.setText(String.format(getString(R.string.ETZ), node.getInput()));
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

    @OnClick(R.id.node_add_btn)
    public void onViewClicked() {
        buyDialog = new NodeBuyDialog(activity, node);
        buyDialog.show();
        buyDialog.setOnConfirmClickListener(this);
    }

    @Override
    public void setConfirmClick(String pwd, String miniId) {
        String token = SharedPrefsUitls.getInstance().getUserToken();
        presenter.BuyNode(token, node.getMiniID(), pwd);
    }

    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(activity, msg);
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }

    }

    @Override
    public void NodeData(List<NodeBean> datas) {

    }

    @Override
    public void buyNode(int code) {
        buyDialog.dismiss();
        finish();
    }

    @Override
    public void signinSuccess(String etz) {

    }

    @Override
    public void signinFail(int code, String msg) {

    }
}
