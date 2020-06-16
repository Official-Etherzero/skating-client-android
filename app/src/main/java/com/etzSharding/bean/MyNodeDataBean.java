package com.etzSharding.bean;

import java.util.List;

public class MyNodeDataBean {
    private List<MyNodeBean> UserNodeList;
    private String TotalCount;//"integer,记录总条数",
    private String TotalReward;//"integer,累计收益",
    private String TotalRewardYesterday;//"integer,昨天收益"

    public List<MyNodeBean> getUserNodeList() {
        return UserNodeList;
    }

    public String getTotalReward() {
        return TotalReward;
    }

    public String getTotalRewardYesterday() {
        return TotalRewardYesterday;
    }

    public String getTotalCount() {
        return TotalCount;
    }
}
