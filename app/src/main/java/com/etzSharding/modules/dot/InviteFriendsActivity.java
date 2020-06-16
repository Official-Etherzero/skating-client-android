package com.etzSharding.modules.dot;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.app.UrlFactory;
import com.etzSharding.base.BaseActivity;
import com.etzSharding.bean.UserBean;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.modules.normalvp.NormalPresenter;
import com.etzSharding.modules.normalvp.NormalView;
import com.etzSharding.utils.ClipboardManager;
import com.etzSharding.utils.QRUtils;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteFriendsActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.my_yqm)
    TextView myYqm;
    @BindView(R.id.my_yqlj)
    ImageView myYqlj;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friends;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        UserBean userBean = MainActivity.getApp().getUser();
        if (userBean != null) {
            myYqm.setText(userBean.getInviteCode());
            String str = UrlFactory.getregisterH5URL() + userBean.getInviteCode();
            Uri uri = Uri.parse(str);
            boolean generated = QRUtils.generateQR(getApplication(), uri.toString(), myYqlj);
            if (!generated)
                throw new RuntimeException("failed to generate qr image for address");
        }

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

    @OnClick({R.id.wallet_back, R.id.copy_yqm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wallet_back:
                finish();
                break;
            case R.id.copy_yqm:
                String yqm = myYqm.getText().toString().trim();
                if (Util.isNullOrEmpty(yqm)) {
                    ToastUtils.showLongToast(activity, R.string.yaoqingma_null);
                } else {
                    String yqUrl = UrlFactory.getregisterH5URL() + yqm;
                    ClipboardManager.putClipboard(InviteFriendsActivity.this, yqUrl);
                }

                break;
        }
    }
}
