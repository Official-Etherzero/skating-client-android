package com.etzSharding.modules.user.VerifiedName;


import com.etzSharding.base.BasePresent;
import com.etzSharding.bean.AuthenticationBean;
import com.etzSharding.data.DataSource;
import com.etzSharding.data.RemoteDataSource;

import org.json.JSONObject;

import java.util.HashMap;

public class VerfiiedPresenter extends BasePresent<VerifiedView> {

    public void getAliDescribeVerifyToken(String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getAliDescribeVerifyToken(object.toString(), new DataSource.DataCallback<AuthenticationBean>() {
            @Override
            public void onDataLoaded(AuthenticationBean obj) {
                view.setVerifyToken(obj);
            }
            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {view.requestFail(code,toastMessage); }
        });
    }

    public void getAliDescribeVerifyResult(String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getAliDescribeVerifyResult(object.toString(), new DataSource.DataCallback<AuthenticationBean>() {
            @Override
            public void onDataLoaded(AuthenticationBean obj) {
                view.setVerifyResult(obj);
            }
            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) { view.requestFail(code,toastMessage); }
        });
    }
}
