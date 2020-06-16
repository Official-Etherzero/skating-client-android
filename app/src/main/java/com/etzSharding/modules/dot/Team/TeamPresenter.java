package com.etzSharding.modules.dot.Team;


import com.etzSharding.base.BasePresent;
import com.etzSharding.bean.NodeBean;
import com.etzSharding.bean.NodeRevenueBean;
import com.etzSharding.bean.TeamNodeDataBean;
import com.etzSharding.data.DataSource;
import com.etzSharding.data.RemoteDataSource;
import com.etzSharding.modules.dot.Node.NodeView;
import com.etzSharding.utils.Md5Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class TeamPresenter extends BasePresent<TeamView> {

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
                view.requestFail(code, toastMessage);

            }
        });
    }

    public void getTeamRewardList(String PageSize, String CurrentPage, String Access_Token,String Type) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("PageSize", PageSize);
        hashMap.put("CurrentPage", CurrentPage);
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("Type", Type);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getDetailedList(object.toString(), new DataSource.DataCallback<List<NodeRevenueBean>>() {
            @Override
            public void onDataLoaded(List<NodeRevenueBean> list) {
                view.setTeamRewardList(list);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);

            }
        });
    }
}
