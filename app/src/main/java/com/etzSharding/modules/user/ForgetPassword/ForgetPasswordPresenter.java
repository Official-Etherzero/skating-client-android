package com.etzSharding.modules.user.ForgetPassword;

import com.etzSharding.base.BasePresent;
import com.etzSharding.data.DataSource;
import com.etzSharding.data.RemoteDataSource;
import com.etzSharding.utils.Md5Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class ForgetPasswordPresenter extends BasePresent<ForgetPasswordView> {
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

    public void verificationPhoneCode(String AreaCode, String MobilePhone, String VerifyCode) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("AreaCode", AreaCode);
        hashMap.put("MobilePhone", MobilePhone);
        hashMap.put("VerifyCode", VerifyCode);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().verifySMSCode(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.verificationSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });
    }

    public void verificationEmailCode(String Mail, String VerifyCode) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Mail", Mail);
        hashMap.put("VerifyCode", VerifyCode);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().verifyMailCode(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.verificationSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });
    }

    public void resetPhonePWD(String AreaCode, String MobilePhone, String NewPasswd) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("AreaCode", AreaCode);
        hashMap.put("MobilePhone", MobilePhone);
        hashMap.put("NewPasswd", Md5Utils.md5(NewPasswd));
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().retsetPWDbyPhone(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.resetSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });
    }

    public void resetMailPWD(String Mail, String NewPasswd) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Mail", Mail);
        hashMap.put("NewPasswd", Md5Utils.md5(NewPasswd));
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().retsetPWDbyMail(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.resetSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });
    }

}
