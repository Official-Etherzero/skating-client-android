package com.etzSharding.bean;

import com.etzSharding.utils.Util;

public class TeamNodeBean {
    private String Phone;
    private String Email;
    private String TeamLevel;//0，1，2，3，4无个级别
    private String TeamNodeCount;
    private String IsEffect;

    public String getPhone() {
        return Util.isNullOrEmpty(Phone) ? "" : Phone.substring(0, 3) + "****" + Phone.substring(Phone.length() - 4, Phone.length());
    }

    public String getEmail() {
        return Util.isNullOrEmpty(Email) ? "" : (Email.substring(0, 1) + "****" + Email.substring(Email.indexOf("@"), Email.length()));
    }

    public String getTeamLevel() {
        return TeamLevel;
    }

    public String getTeamNodeCount() {
        return TeamNodeCount;
    }

    public String getIsEffect() {
        return IsEffect;
    }
}
