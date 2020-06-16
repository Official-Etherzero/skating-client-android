package com.etzSharding.adapter.baseAdapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.etzSharding.R;
import com.etzSharding.adapter.baseAdapter.base.MyBaseViewHolder;
import com.etzSharding.bean.WalletBean;
import com.etzSharding.modules.walletsetting.WalletSetting;
import com.etzSharding.sqlite.BalanceDataSource;
import com.etzSharding.utils.CurrencyUtils;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class WalletManageAdapter extends BaseQuickAdapter<WalletBean, MyBaseViewHolder> {

    public WalletManageAdapter(int layoutResId, @Nullable List<WalletBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyBaseViewHolder helper, WalletBean item) {
        Map<String, String> balances = BalanceDataSource.getInstance().getWalletTokensBalance(item.getId());
        helper.setText(R.id.wallet_item_name, item.getName());
        helper.setText(R.id.wallet_item_address, item.getAddress());

        if (balances.containsKey("ETZ")) {
            String cryptoBalance = CurrencyUtils.getFormattedAmount(mContext, "ETZ", new BigDecimal(Util.isNullOrEmpty(balances.get("ETZ")) ? "0" : balances.get("ETZ")));
            helper.setText(R.id.wallet_item_balance, cryptoBalance);
        }
        else {
            helper.setText(R.id.wallet_item_balance, "0 ETZ");
        }
        helper.getView(R.id.wallet_manager_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WalletSetting.class);
                intent.putExtra("wallet", item);
                mContext.startActivity(intent);
            }
        });


    }

    public void setTokens(List<WalletBean> tokens) {
        setNewData(tokens);
    }
}
