package com.etzSharding.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MyNodeBean implements Parcelable {
    private String NodeID;//"integer,节点ID"
    private String OAmount;//integer,节点原价
    private String UserID;//"integer,用户ID"
    private String MiniID;//"integer,节点ID",
    private String StartTime;//"date-time,开始时间"
    private String ExpireTime;//"date-time,到期时间"
    private String RestOfDay;//"integer,剩余天数"
    private String Reward;//"integer,累计收益"
    private String RewardYesterday;//"integer,昨日收益"


    public MyNodeBean() {
    }


    protected MyNodeBean(Parcel in) {
        NodeID = in.readString();
        OAmount = in.readString();
        UserID = in.readString();
        MiniID = in.readString();
        StartTime = in.readString();
        ExpireTime = in.readString();
        RestOfDay = in.readString();
        Reward = in.readString();
        RewardYesterday = in.readString();
    }

    public static final Creator<MyNodeBean> CREATOR = new Creator<MyNodeBean>() {
        @Override
        public MyNodeBean createFromParcel(Parcel in) {
            return new MyNodeBean(in);
        }

        @Override
        public MyNodeBean[] newArray(int size) {
            return new MyNodeBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NodeID);
        dest.writeString(OAmount);
        dest.writeString(UserID);
        dest.writeString(MiniID);
        dest.writeString(StartTime);
        dest.writeString(ExpireTime);
        dest.writeString(RestOfDay);
        dest.writeString(Reward);
        dest.writeString(RewardYesterday);
    }

    public String getNodeID() {
        return NodeID;
    }

    public String getOAmount() {
        return OAmount;
    }

    public String getUserID() {
        return UserID;
    }

    public String getMiniID() {
        return MiniID;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getExpireTime() {
        return ExpireTime;
    }

    public String getRestOfDay() {
        return RestOfDay;
    }

    public String getReward() {
        return Reward;
    }

    public String getRewardYesterday() {
        return RewardYesterday;
    }
}
