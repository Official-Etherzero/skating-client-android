package com.etzSharding.modules.dot.MyNode;


import com.etzSharding.base.BasePresent;
import com.etzSharding.bean.MyNodeDataBean;
import com.etzSharding.bean.NodeRevenueDataBean;
import com.etzSharding.bean.UserBean;
import com.etzSharding.data.DataSource;
import com.etzSharding.data.RemoteDataSource;
import com.etzSharding.modules.user.Login.LoginView;
import com.etzSharding.utils.Md5Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class MyNodePresenter extends BasePresent<MyNodeView> {

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

                view.requestFail(code,toastMessage);
            }
        });
    }

    public void getNodeRevenueList(String Node_ID, String PageSize, String CurrentPage, String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("NodeID", Node_ID);
        hashMap.put("PageSize", PageSize);
        hashMap.put("CurrentPage", CurrentPage);
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getMyNodeRevenueList(object.toString(), new DataSource.DataCallback<NodeRevenueDataBean>() {
            @Override
            public void onDataLoaded(NodeRevenueDataBean obj) {
                view.NodeRevenueData(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {

                view.requestFail(code,toastMessage);
            }
        });
    }
}
