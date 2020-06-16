package com.etzSharding.bean;

import com.etzSharding.utils.Util;

public class NodeRevenueBean {
    private String Amount;
    private String WriteTime;
    private String Inout;//"integer,1表示入，2表示出"
    private String Remark;//"string,备注"

    public String getAmount() {
        return Amount;
    }

    public String getWriteTime() {
        return WriteTime;
    }

    public String getInout() {
        return Inout;
    }

    public String getRemark() {
        return Remark;
    }
}
