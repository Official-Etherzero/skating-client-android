package com.etzSharding.base;

import com.etzSharding.bean.WalletBean;
import com.etzSharding.sqlite.WalletDataStore;
import com.etzSharding.utils.SharedPrefsUitls;

public abstract class BasePresent<T>{
    public T view;

    public void attach(T view){
        this.view = view;
    }

    public void detach(){
        this.view = null;
    }

    public WalletBean getCurrentWallet() {
        String wid = SharedPrefsUitls.getInstance().getCurrentWallet();
        return WalletDataStore.getInstance().queryWallet(wid);
    }
}