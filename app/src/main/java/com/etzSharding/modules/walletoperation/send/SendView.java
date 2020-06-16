package com.etzSharding.modules.walletoperation.send;

import com.etzSharding.base.BaseView;

public interface SendView extends BaseView{

    void sendGasLimitSuccess(String to,String gas,String data);
    void sendViewError(String str);
    void sendGasPriceSuccess(String price);
}
