package com.etzSharding.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.etzSharding.R;
import com.etzSharding.adapter.baseAdapter.base.MyBaseViewHolder;
import com.etzSharding.app.MyApp;
import com.etzSharding.bean.TeamNodeBean;
import com.etzSharding.utils.Util;

import java.util.List;

public class TeamNodeAdapter extends BaseQuickAdapter<TeamNodeBean, MyBaseViewHolder> {


    public TeamNodeAdapter(int layoutResId, @Nullable List<TeamNodeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyBaseViewHolder helper, TeamNodeBean item) {
        ImageView team_calss = helper.getView(R.id.team_calss);
        int level = Integer.valueOf(item.getTeamLevel());
//        if (level > 0) {
//            team_calss.setVisibility(View.VISIBLE);
//        } else {
//            team_calss.setVisibility(View.GONE);
//        }
        switch (level) {
            case 0:
                team_calss.setImageResource(0);
                break;
            case 1:
                team_calss.setImageResource(R.mipmap.team_1);
                break;
            case 2:
                team_calss.setImageResource(R.mipmap.team_2);
                break;
            case 3:
                team_calss.setImageResource(R.mipmap.team_3);
                break;
            case 4:
                team_calss.setImageResource(R.mipmap.team_4);
                break;
        }
        TextView youxiao = helper.getView(R.id.team_is_effect);
        if (item.getIsEffect().equals("0")) {
            youxiao.setText(R.string.team_wuxiao);
            youxiao.setTextColor(MyApp.getmInstance().getResources().getColor(R.color.zt_601a));
        } else {
            youxiao.setText(R.string.team_youxiao);
            youxiao.setTextColor(MyApp.getmInstance().getResources().getColor(R.color.zt_lu));
        }
        helper.setText(R.id.team_acc, Util.isNullOrEmpty(item.getPhone()) ? item.getEmail() : item.getPhone());
        helper.setText(R.id.team_node_total, String.format(MyApp.getmInstance().getString(R.string.team_node_item_num), item.getTeamNodeCount()));


    }

    public void setTokens(List<TeamNodeBean> tokens) {
        setNewData(tokens);
    }

}
