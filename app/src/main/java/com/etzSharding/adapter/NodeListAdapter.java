package com.etzSharding.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.etzSharding.R;
import com.etzSharding.adapter.baseAdapter.base.MyBaseViewHolder;
import com.etzSharding.app.MyApp;
import com.etzSharding.bean.NodeBean;

import java.util.List;

public class NodeListAdapter extends BaseQuickAdapter<NodeBean, MyBaseViewHolder> {

    private ItemButtonClickListener listener;

    public NodeListAdapter(int layoutResId, @Nullable List<NodeBean> data, ItemButtonClickListener listener) {
        super(layoutResId, data);
        this.listener = listener;
    }

    @Override
    protected void convert(MyBaseViewHolder helper, NodeBean item) {
        ConstraintLayout mynodeBg = helper.getView(R.id.node_item_cl);
        TextView myNodeClass = helper.getView(R.id.node_class);

        int miniId = Integer.valueOf(item.getMType());
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

        helper.setText(R.id.node_snsyl, item.getEarnings()+"%");
        helper.setText(R.id.node_cycle,  String.format(MyApp.getmInstance().getResources().getString(R.string.day), item.getPeriod()));
        helper.setText(R.id.node_sj, String.format(MyApp.getmInstance().getResources().getString(R.string.ETZ), item.getInput()));
    }

    public void setTokens(List<NodeBean> tokens) {
        setNewData(tokens);
    }

    public interface ItemButtonClickListener {
        void setItem(NodeBean item);
    }
}
