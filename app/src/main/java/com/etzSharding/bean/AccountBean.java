package com.etzSharding.bean;

public class AccountBean {
    private int accId;
    private String accName;

    public AccountBean(int accId, String accName) {
        this.accId = accId;
        this.accName = accName;
    }

    public int getAccId() {
        return accId;
    }

    public String getAccName() {
        return accName;
    }
}
