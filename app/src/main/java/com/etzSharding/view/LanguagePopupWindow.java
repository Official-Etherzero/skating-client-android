package com.etzSharding.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.etzSharding.R;
import com.etzSharding.adapter.PopListAdapter;
import com.etzSharding.app.ActivityUtils;
import com.etzSharding.app.AppManager;
import com.etzSharding.bean.languageEntity;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.utils.LocalManageUtil;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.Util;

import java.util.List;

public class LanguagePopupWindow extends PopupWindow {

    private RecyclerView rList;
    Context context;
    private View mPopView;
    private onPopupWindowListener listener;
    private LinearLayoutManager linearLayoutManager;
    private PopListAdapter adapter;
    private List<languageEntity> lists;
    private int type;
    private languageEntity bean;

    public LanguagePopupWindow(Activity context, int type, List<languageEntity> lists) {
        super(context);
        this.context = context;
        this.type = type;
        this.lists = lists;
        init(context);
        setPopupWindow();
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(final Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.language_set_layout, null);
        rList = mPopView.findViewById(R.id.lpop_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rList.setLayoutManager(linearLayoutManager);
        adapter = new PopListAdapter(R.layout.pop_list_item, lists, type);
        rList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bean = (languageEntity) adapter.getItem(position);
                updateBeans(bean.getLid());
            }
        });
        mPopView.findViewById(R.id.lpop_affirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean != null && listener != null) {

                    if (type == 1) {
                        LocalManageUtil.saveSelectLanguage(context, bean.getLid());
                        AppManager.getAppManager().finishAllActivity();
                        ActivityUtils.next((Activity) context, MainActivity.class);
                    } else if (type == 2) {
                        SharedPrefsUitls.getInstance().putFiatCurrency(bean.getLid());
                        listener.popOnClick(bean);
                    }
                }
                dismiss();
            }
        });
        mPopView.findViewById(R.id.lpop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setBackgroundDrawable(new ColorDrawable(0x30000000));// 设置背景透明
        this.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        this.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        this.setFocusable(true);
    }

    public void setOnPopupListener(onPopupWindowListener listener) {
        this.listener = listener;
    }

    public interface onPopupWindowListener {
        void popOnClick(languageEntity bean);
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void updateBeans(int id) {
        for (languageEntity entity : lists) {
            if (entity.getLid() == id) {
                entity.setSelect(true);
            } else {
                entity.setSelect(false);
            }
            adapter.setTokens(lists);
        }

    }
}
