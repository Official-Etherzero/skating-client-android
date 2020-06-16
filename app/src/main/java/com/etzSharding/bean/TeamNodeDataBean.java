package com.etzSharding.bean;

import java.util.List;

public class TeamNodeDataBean {
    private List<TeamNodeBean> UserNodeList;
    private String TeamNodeCount;//"integer,节点数"
    private String TeamNodeAmount;//"integer,业绩（锁币总数）"
    private String RewardYesterday;//"string,团队昨日收益"
    private String TeamReward;//"integer,总奖励"
    private String TeamLevel;//"integer,团队级别"
    private String PeopleCount;//"integer,有效的直推人数"
    private String PeopleCount2;//"integer,直推人数"


    public List<TeamNodeBean> getUserNodeList() {
        return UserNodeList;
    }

    public String getTeamNodeCount() {
        return TeamNodeCount;
    }

    public String getTeamNodeAmount() {
        return TeamNodeAmount;
    }

    public String getTeamReward() {
        return TeamReward;
    }

    public String getTeamLevel() {
        return TeamLevel;
    }

    public String getPeopleCount() {
        return PeopleCount;
    }

    public String getRewardYesterday() {
        return RewardYesterday;
    }

    public String getPeopleCount2() {
        return PeopleCount2;
    }

    @Override
    public String toString() {
        return "TeamNodeDataBean{" +
                "UserNodeList=" + UserNodeList +
                ", TeamNodeCount='" + TeamNodeCount + '\'' +
                ", TeamNodeAmount='" + TeamNodeAmount + '\'' +
                ", RewardYesterday='" + RewardYesterday + '\'' +
                ", TeamReward='" + TeamReward + '\'' +
                ", TeamLevel='" + TeamLevel + '\'' +
                ", PeopleCount='" + PeopleCount + '\'' +
                '}';
    }
}
