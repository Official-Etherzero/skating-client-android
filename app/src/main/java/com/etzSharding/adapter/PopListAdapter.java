package com.etzSharding.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.etzSharding.R;
import com.etzSharding.adapter.baseAdapter.base.MyBaseViewHolder;
import com.etzSharding.bean.languageEntity;

import java.util.List;

public class PopListAdapter extends BaseQuickAdapter<languageEntity, MyBaseViewHolder> {

    private int type;

    public PopListAdapter(int layoutResId, @Nullable List<languageEntity> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(MyBaseViewHolder helper, languageEntity item) {
        TextView content = helper.getView(R.id.pop_item_str);
//        content.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//        content.getPaint().setAntiAlias(true);//抗锯齿
        if (item.isSelect()) {
            content.setTextColor(mContext.getResources().getColor(R.color.zt_lu));
            content.setBackgroundResource(R.color.zt_lu05);
        } else {
            content.setTextColor(mContext.getResources().getColor(R.color.zt_black_50));
            content.setBackgroundResource(R.color.white);
        }
        content.setText(item.getLanguage());
    }

    public void setTokens(List<languageEntity> tokens) {
        setNewData(tokens);
    }
}
