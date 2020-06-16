package com.etzSharding.modules.user.rigest;

import com.etzSharding.base.BasePresent;
import com.etzSharding.bean.UserBean;
import com.etzSharding.data.DataSource;
import com.etzSharding.data.RemoteDataSource;
import com.etzSharding.utils.AESCBCUtil;
import com.etzSharding.utils.Md5Utils;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.Util;

import org.json.JSONObject;

import java.util.HashMap;

public class RegisterPresenter extends BasePresent<RegisterView> {

    public void getPhoneCode(String AreaCode, String MobilePhone) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("AreaCode", AreaCode);
        hashMap.put("MobilePhone", MobilePhone);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getPhoneCode(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.codeSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });

    }

    public void getEmailCode(String Mail) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Mail", Mail);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getEmailCode(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.codeSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });
    }

    public void phoneRegister(String Passwd, String InviteCode, String MobilePhone, String VerifyCode) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        HashMap<String, String> randMap = new HashMap<>();
        hashMap.put("Passwd", Md5Utils.md5(Passwd));
        hashMap.put("InviteCode", InviteCode);
        hashMap.put("MobilePhone", MobilePhone);
        hashMap.put("VerifyCode", VerifyCode);
        randMap.put("OSType", "2");
        randMap.put("HTCDesire", android.os.Build.MODEL);
        randMap.put("Mac", Util.getNewMac());
        randMap.put("UUID", "");
        hashMap.put("RandNum", AESCBCUtil.encrypt(new JSONObject(randMap).toString()));
        JSONObject object = new JSONObject(hashMap);
        MyLog.i("RandNum="+new JSONObject(randMap).toString());
        MyLog.i("RandNum="+AESCBCUtil.encrypt(new JSONObject(randMap).toString()));
        MyLog.i("RandNum="+object.toString());
        RemoteDataSource.getInstance().phoneRegister(object.toString(), new DataSource.DataCallback<UserBean>() {
            @Override
            public void onDataLoaded(UserBean obj) {
                view.RegisterSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });
    }

    public void emailRegister(String Passwd, String InviteCode, String Mail, String VerifyCode) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        HashMap<String, String> randMap = new HashMap<>();
        hashMap.put("Passwd", Md5Utils.md5(Passwd));
        hashMap.put("InviteCode", InviteCode);
        hashMap.put("Mail", Mail);
        hashMap.put("VerifyCode", VerifyCode);
        randMap.put("OSType", "2");
        randMap.put("HTCDesire", android.os.Build.MODEL);
        randMap.put("Mac", Util.getNewMac());
        randMap.put("UUID", "");
        hashMap.put("RandNum", AESCBCUtil.encrypt(new JSONObject(randMap).toString()));
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().EmailRegister(object.toString(), new DataSource.DataCallback<UserBean>() {
            @Override
            public void onDataLoaded(UserBean obj) {
                view.RegisterSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });
    }

}
