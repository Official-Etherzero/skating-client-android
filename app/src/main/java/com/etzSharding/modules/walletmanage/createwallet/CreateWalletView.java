package com.etzSharding.modules.walletmanage.createwallet;

import com.etzSharding.base.BaseView;

public interface CreateWalletView extends BaseView {


    void getWalletSuccess(String address);
    void getWalletFail(String msg);

}
