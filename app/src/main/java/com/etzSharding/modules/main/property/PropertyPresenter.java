package com.etzSharding.modules.main.property;

import com.etzSharding.base.BasePresent;
import com.etzSharding.bean.MyNodeDataBean;
import com.etzSharding.bean.TeamNodeDataBean;
import com.etzSharding.bean.TixanBean;
import com.etzSharding.bean.UserBean;
import com.etzSharding.bean.WalletBean;
import com.etzSharding.data.DataSource;
import com.etzSharding.data.RemoteDataSource;
import com.etzSharding.http.HttpRequets;
import com.etzSharding.sqlite.BalanceDataSource;
import com.etzSharding.utils.Md5Utils;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.SharedPrefsUitls;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PropertyPresenter extends BasePresent<PropertyView> {


    public String getWalletBalance(WalletBean wallet) {
        Map<String, String> balances = BalanceDataSource.getInstance().getWalletTokensBalance(wallet.getId());
        return balances.get("ETZ");
    }

    public void getUserInfo(String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getUserInfo(object.toString(), new DataSource.DataCallback<UserBean>() {
            @Override
            public void onDataLoaded(UserBean obj) {

                view.userInfo(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                if (code == 401)
                    view.requestFail(code, toastMessage);
            }
        });
    }

    public void withdraw(String Passwd, String Access_Token, String Amount, String Address) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Passwd", Md5Utils.md5(Passwd));
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("Amount", Amount);
        hashMap.put("toAddr", Address);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().withdraw(object.toString(), new DataSource.DataCallback<TixanBean>() {
            @Override
            public void onDataLoaded(TixanBean bean) {
                view.withdrawSuccess(bean);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });

    }

    public void signin(String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().signin(object.toString(), new DataSource.DataCallback<String>() {
            @Override
            public void onDataLoaded(String obj) {
                view.signinSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.signinFail(code, toastMessage);
            }
        });
    }

    public void getMyNodeList(String PageSize, String CurrentPage, String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("PageSize", PageSize);
        hashMap.put("CurrentPage", CurrentPage);
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getMyNodeList(object.toString(), new DataSource.DataCallback<MyNodeDataBean>() {
            @Override
            public void onDataLoaded(MyNodeDataBean obj) {
                view.MyNodeData(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                if (code == 401)
                    view.requestFail(code, toastMessage);
            }
        });
    }

    public void getTeamNodeList(String PageSize, String CurrentPage, String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("PageSize", PageSize);
        hashMap.put("CurrentPage", CurrentPage);
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getTeamNodeList(object.toString(), new DataSource.DataCallback<TeamNodeDataBean>() {
            @Override
            public void onDataLoaded(TeamNodeDataBean data) {
                view.setTeamNodeData(data);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                if (code == 401)
                    view.requestFail(code, toastMessage);

            }
        });
    }
}
