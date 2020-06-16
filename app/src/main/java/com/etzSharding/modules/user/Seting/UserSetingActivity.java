package com.etzSharding.modules.user.Seting;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.app.MyApp;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.languageEntity;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;
import com.etzSharding.utils.LocalManageUtil;
import com.etzSharding.utils.SPLUtil;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.view.LanguagePopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserSetingActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView, LanguagePopupWindow.onPopupWindowListener {


    @BindView(R.id.set_language)
    TextView setLanguage;
    @BindView(R.id.set_sales_unit)
    TextView setSalesUnit;
    @BindView(R.id.set_version_number)
    TextView setVersionNumber;
    @BindView(R.id.new_version_number_lab)
    View newVersionNumberLab;
    LanguagePopupWindow langPop;
    LanguagePopupWindow bipop;
    List<languageEntity> bis;
    private static final int DEFAULT_VERSION_CODE = 0;
    private static final String DEFAULT_VERSION_NAME = "0";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_seting;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.my_preferences));
        bis = new ArrayList<>();
        int bid = SharedPrefsUitls.getInstance().getFiatCurrency();
        bis.add(new languageEntity(1, getString(R.string.Usdt), (bid == 1)));
        bis.add(new languageEntity(2, getString(R.string.renminbi), (bid == 2)));
        langPop = new LanguagePopupWindow(this, 1, LocalManageUtil.getLanguageList(this));
        bipop = new LanguagePopupWindow(this, 2, bis);
        bipop.setOnPopupListener(this);
        langPop.setOnPopupListener(this);
        int lid = SPLUtil.getInstance(MyApp.getmInstance()).getSelectLanguage();
        String lang = SPLUtil.getInstance(MyApp.getmInstance()).getSystemCurrentLocal().getLanguage();
        if (lid == 0) {
            if (lang.equalsIgnoreCase("zh")) {
                setLanguage.setText(R.string.Chinese_zh);
            } else {
                setLanguage.setText(R.string.English);
            }
        } else {
            switch (lid) {
                case 1:
                    setLanguage.setText(R.string.Chinese_zh);
                    break;
                case 2:
                    setLanguage.setText(R.string.English);
                    break;
                case 7:
                    setLanguage.setText(R.string.Chinese_tw);
                    break;
            }
        }
        int biID = SharedPrefsUitls.getInstance().getFiatCurrency();
        if (biID == 1) {
            setSalesUnit.setText(R.string.Usdt);
        } else {
            setSalesUnit.setText(R.string.renminbi);
        }

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int versionCode = packageInfo != null ? packageInfo.versionCode : DEFAULT_VERSION_CODE;
        String versionName = packageInfo != null ? packageInfo.versionName : DEFAULT_VERSION_NAME;
        setVersionNumber.setText(versionName);
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

    @OnClick({R.id.set_language_ll, R.id.set_sales_unit_ll, R.id.set_version_number_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_language_ll:
//                langPop.showAtLocation(findViewById(R.id.user_seting_grop), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.set_sales_unit_ll:
                bipop.showAtLocation(findViewById(R.id.user_seting_grop), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.set_version_number_ll:
                break;
        }
    }

    @Override
    public void popOnClick(languageEntity bean) {
        int biID = SharedPrefsUitls.getInstance().getFiatCurrency();
        if (biID == 1) {
            setSalesUnit.setText(R.string.Usdt);
        } else {
            setSalesUnit.setText(R.string.renminbi);
        }

    }
}
