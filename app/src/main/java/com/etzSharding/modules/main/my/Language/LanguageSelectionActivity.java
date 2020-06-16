package com.etzSharding.modules.main.my.Language;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.etzSharding.R;
import com.etzSharding.adapter.LanguageAdapter;
import com.etzSharding.app.ActivityUtils;
import com.etzSharding.app.AppManager;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.languageEntity;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.modules.main.my.displayCurrency.DisplayCurrencyActivity;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;
import com.etzSharding.utils.LocalManageUtil;
import com.etzSharding.utils.SPLUtil;
import com.etzSharding.utils.SharedPrefsUitls;
import com.gyf.barlibrary.ImmersionBar;

public class LanguageSelectionActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {

    private RecyclerView etz_node_lv;
    LanguageAdapter adapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_etznode_selection;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getResources().getString(R.string.my_yysz));
        etz_node_lv = findViewById(R.id.etz_node_lv);
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 1f)
                .init();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        etz_node_lv.setLayoutManager(linearLayoutManager);
        adapter = new LanguageAdapter(R.layout.node_list_item, LocalManageUtil.getLanguageList(this));
        etz_node_lv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                languageEntity item = (languageEntity) adapter.getItem(position);
                LocalManageUtil.saveSelectLanguage(activity, item.getLid());
                AppManager.getAppManager().finishAllActivity();
                ActivityUtils.next(activity, MainActivity.class);
//                adapter.notifyDataSetChanged();
            }

        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
