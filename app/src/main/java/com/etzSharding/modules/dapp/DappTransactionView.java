package com.etzSharding.modules.dapp;

import com.etzSharding.base.BaseView;

public interface DappTransactionView extends BaseView{

    void onGasEstimate(String gas);
    void onGasPrice(String price);
    void onError(String err);
}
