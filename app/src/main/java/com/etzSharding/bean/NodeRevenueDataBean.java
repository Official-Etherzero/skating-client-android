package com.etzSharding.bean;

import java.util.List;

public class NodeRevenueDataBean {
    private List<NodeRevenueBean> UserNodeList;
    private String TotalCount;
    private String TotalReward;
    private String TotalRewardYesterday;

    public List<NodeRevenueBean> getUserNodeList() {
        return UserNodeList;
    }

    public String getTotalCount() {
        return TotalCount;
    }


    public String getTotalReward() {
        return TotalReward;
    }

    public String getTotalRewardYesterday() {
        return TotalRewardYesterday;
    }

    @Override
    public String toString() {
        return "NodeRevenueDataBean{" +
                "UserNodeList=" + UserNodeList +
                ", TotalCount='" + TotalCount + '\'' +
                ", TotalReward='" + TotalReward + '\'' +
                ", TotalRewardYesterday='" + TotalRewardYesterday + '\'' +
                '}';
    }
}
