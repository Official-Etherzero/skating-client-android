package com.etzSharding.modules.user.Login;


import com.etzSharding.base.BasePresent;
import com.etzSharding.bean.UserBean;
import com.etzSharding.data.DataSource;
import com.etzSharding.data.RemoteDataSource;
import com.etzSharding.utils.Md5Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginPresenter extends BasePresent<LoginView> {

    public void loginPhone(String AreaCode, String MobilePhone, String Passwd) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("AreaCode", AreaCode);
        hashMap.put("MobilePhone", MobilePhone);
        hashMap.put("Passwd", Md5Utils.md5(Passwd));
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().phoneLogin(object.toString(), new DataSource.DataCallback<UserBean>() {
            @Override
            public void onDataLoaded(UserBean obj) {
                view.login(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {

                view.requestFail(toastMessage);
            }
        });
    }

    public void loginEmall(String Mail, String Passwd) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Mail", Mail);
        hashMap.put("Passwd", Md5Utils.md5(Passwd));
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().emailLogin(object.toString(), new DataSource.DataCallback<UserBean>() {
            @Override
            public void onDataLoaded(UserBean obj) {
                view.login(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {

                view.requestFail(toastMessage);
            }
        });
    }
}
