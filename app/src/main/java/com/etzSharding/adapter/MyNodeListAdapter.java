package com.etzSharding.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.etzSharding.R;
import com.etzSharding.adapter.baseAdapter.base.MyBaseViewHolder;
import com.etzSharding.app.MyApp;
import com.etzSharding.bean.MyNodeBean;
import com.etzSharding.view.WaveProgressView;

import java.util.List;

public class MyNodeListAdapter extends BaseQuickAdapter<MyNodeBean, MyBaseViewHolder> {


    public MyNodeListAdapter(int layoutResId, @Nullable List<MyNodeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyBaseViewHolder helper, MyNodeBean item) {
        ConstraintLayout mynodeBg = helper.getView(R.id.mynode_bg);
        TextView myNodeClass = helper.getView(R.id.my_node_class);

        int miniId = Integer.valueOf(item.getMiniID());
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

        helper.setText(R.id.node_ljsy, item.getReward());
        helper.setText(R.id.node_zrsy, "+" + item.getRewardYesterday());
        helper.setText(R.id.node_jldq, String.format(MyApp.getmInstance().getResources().getString(R.string.day), item.getRestOfDay()));
    }

    public void setTokens(List<MyNodeBean> tokens) {
        setNewData(tokens);
    }
}
