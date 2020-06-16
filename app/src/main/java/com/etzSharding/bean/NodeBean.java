package com.etzSharding.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NodeBean implements Parcelable {
    private String MiniID;
    private String MType;//"integer,矿机类型 1-初级，2-中级，3-高级， 4-顶级，5-特级，6-智能",
    private String Name;//"string,矿机名称",
    private String Input;//"integer,投入",
    private String Earnings;//"integer,收益",
    private String Period;// "integer,周期",


    public NodeBean() {
    }
    protected NodeBean(Parcel in) {
        MiniID = in.readString();
        MType = in.readString();
        Name = in.readString();
        Input = in.readString();
        Earnings = in.readString();
        Period = in.readString();
    }

    public static final Creator<NodeBean> CREATOR = new Creator<NodeBean>() {
        @Override
        public NodeBean createFromParcel(Parcel in) {
            return new NodeBean(in);
        }

        @Override
        public NodeBean[] newArray(int size) {
            return new NodeBean[size];
        }
    };

    public String getMiniID() {
        return MiniID;
    }

    public String getMType() {
        return MType;
    }

    public String getName() {
        return Name;
    }


    public String getInput() {
        return Input;
    }

    public String getEarnings() {
        return Earnings;
    }

    public String getPeriod() {
        return Period;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MiniID);
        dest.writeString(MType);
        dest.writeString(Name);
        dest.writeString(Input);
        dest.writeString(Earnings);
        dest.writeString(Period);
    }
}
