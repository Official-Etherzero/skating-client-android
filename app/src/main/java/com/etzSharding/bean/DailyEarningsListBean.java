package com.etzSharding.bean;

import java.util.List;

public class DailyEarningsListBean {
    private List<TeamNodeBean> MiningEarningList;
    private String Total;
    private String TotalEarning;

    public List<TeamNodeBean> getMiningEarningList() {
        return MiningEarningList;
    }

    public String getTotal() {
        return Total;
    }

    public String getTotalEarning() {
        return TotalEarning;
    }
}
