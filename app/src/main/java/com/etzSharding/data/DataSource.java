package com.etzSharding.data;


import com.etzSharding.bean.AuthenticationBean;
import com.etzSharding.bean.BaseEtzBean;
import com.etzSharding.bean.MyNodeDataBean;
import com.etzSharding.bean.NodeBean;
import com.etzSharding.bean.NodeRevenueBean;
import com.etzSharding.bean.NodeRevenueDataBean;
import com.etzSharding.bean.TeamNodeDataBean;
import com.etzSharding.bean.TixanBean;
import com.etzSharding.bean.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface DataSource {
    void getPhoneCode(String json, DataCallback<Integer> dataCallback);
    void getEmailCode(String json, DataCallback<Integer> dataCallback);
    void phoneRegister(String json, DataCallback<UserBean> dataCallback);
    void EmailRegister(String json, DataCallback<UserBean> dataCallback);
    void phoneLogin(String json, DataCallback<UserBean> dataCallback);
    void emailLogin(String json, DataCallback<UserBean> dataCallback);
    void verifySMSCode(String json, DataCallback<Integer> dataCallback);
    void verifyMailCode(String json, DataCallback<Integer> dataCallback);
    void retsetPWDbyPhone(String json, DataCallback<Integer> dataCallback);
    void retsetPWDbyMail(String json, DataCallback<Integer> dataCallback);
    void getUserInfo(String json, DataCallback<UserBean> dataCallback);
    void bindWallet(String json,DataCallback<Integer> dataCallback);
    void withdraw(String json, DataCallback<TixanBean> dataCallback);
    void getMyNodeList(String json, DataCallback<MyNodeDataBean> dataCallback);
    void getMyNodeRevenueList(String json, DataCallback<NodeRevenueDataBean> dataCallback);
    void getNodeList(String json,DataCallback<List<NodeBean>> dataCallback);
    void buyNode(String json, DataCallback<Integer> dataCallback);
    void getTeamNodeList(String json, DataCallback<TeamNodeDataBean> dataCallback);
    void getTeamRewardList(String json, DataCallback<List<NodeRevenueBean>> dataCallback);
    void signin(String json, DataCallback<String> dataCallback);
    void getAliDescribeVerifyToken(String json, DataCallback<AuthenticationBean> dataCallback);
    void getAliDescribeVerifyResult(String json, DataCallback<AuthenticationBean> dataCallback);
    void getDetailedList(String json, DataCallback<List<NodeRevenueBean>> dataCallback);




    interface DataCallback<T> {

        void onDataLoaded(T obj);

        void onDataNotAvailable(Integer code, String toastMessage);
    }

    void getDepositList(String token, DataCallback dataCallback);

    void commitSellerApply(String token, String coinId, String json, DataCallback dataCallback);

}
