package com.etzSharding.modules.walletoperation.wallet;

import com.etzSharding.base.BaseView;
import com.etzSharding.bean.TransactionRecords;

import java.util.List;

public interface WalletView extends BaseView {

    void walletTransactions(List<TransactionRecords> list);
    void showError(String err);

}
