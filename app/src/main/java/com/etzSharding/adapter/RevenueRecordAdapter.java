package com.etzSharding.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.etzSharding.R;
import com.etzSharding.adapter.baseAdapter.base.MyBaseViewHolder;
import com.etzSharding.app.MyApp;
import com.etzSharding.bean.NodeRevenueBean;
import com.etzSharding.bean.TeamNodeBean;

import java.util.List;

public class RevenueRecordAdapter extends BaseQuickAdapter<NodeRevenueBean, MyBaseViewHolder> {

    private int type;

    public RevenueRecordAdapter(int layoutResId, @Nullable List<NodeRevenueBean> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(MyBaseViewHolder helper, NodeRevenueBean item) {
        TextView tv = helper.getView(R.id.earvings_type);
        TextView etz=helper.getView(R.id.earvings_ubi);
        if (type == 1) {
            tv.setVisibility(View.VISIBLE);
            if (item.getInout().equals("1")) {
                etz.setText( "+"+item.getAmount() + " ETZ");
                etz.setTextColor(MyApp.getmInstance().getResources().getColor(R.color.red));
            } else {
                etz.setText( "-"+item.getAmount() + " ETZ");
                etz.setTextColor(MyApp.getmInstance().getResources().getColor(R.color.zt_801a));
            }
        } else {
            tv.setVisibility(View.GONE);
            etz.setText( "+"+item.getAmount() + " ETZ");
        }
        tv.setText(item.getRemark());
        helper.setText(R.id.earvings_date, item.getWriteTime());


    }

    public void setTokens(List<NodeRevenueBean> tokens) {
        setNewData(tokens);
    }

}
