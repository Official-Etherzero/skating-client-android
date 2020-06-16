package com.etzSharding.modules.dot.Node;


import com.etzSharding.base.BasePresent;
import com.etzSharding.bean.NodeBean;
import com.etzSharding.data.DataSource;
import com.etzSharding.data.RemoteDataSource;
import com.etzSharding.utils.Md5Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class NodePresenter extends BasePresent<NodeView> {

    public void getNodeList() {
        RemoteDataSource.getInstance().getNodeList("", new DataSource.DataCallback<List<NodeBean>>() {
            @Override
            public void onDataLoaded(List<NodeBean> data) {
                view.NodeData(data);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {

            }
        });
    }

    public void BuyNode(String Access_Token, String NodeID, String Passwd) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("NodeID", NodeID);
        hashMap.put("Passwd", Md5Utils.md5(Passwd));
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().buyNode(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.buyNode(obj);
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
}
