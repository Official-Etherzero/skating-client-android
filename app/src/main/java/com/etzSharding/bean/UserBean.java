package com.etzSharding.bean;

import com.etzSharding.utils.Util;

import java.io.Serializable;

public class UserBean implements Serializable {
    private String UserID;
    private String ETZ;
    private String WalletAddr;
    private String RechargeAddr;
    private String IsTrueName;
    private String InviteCode;
    private String Access_Token;
    private String Phone;
    private String Email;
    private String fee;

    public String getFee() {
        return fee;
    }

    public String getUserID() {
        return UserID;
    }


    public String getETZ() {
        return Util.isNullOrEmpty(ETZ) ? "0" : ETZ;
    }

    public String getAccess_Token() {
        return Access_Token;
    }

    public String getIsTrueName() {
        return IsTrueName;
    }

    public String getInviteCode() {
        return InviteCode;
    }

    public String getWalletAddr() {
        return WalletAddr;
    }

    public String getRechargeAddr() {
        return RechargeAddr;
    }

    public String getAccount() {
        String acc;
        if (!Util.isNullOrEmpty(Phone) && !Phone.equals("0")) {
            acc = Phone.substring(0, 3) + "****" + Phone.substring(Phone.length() - 4, Phone.length());
        } else {
            acc = Email.substring(0, 1) + "****" + Email.substring(Email.indexOf("@"), Email.length());
        }
        return acc;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "UserID='" + UserID + '\'' +
                ", ETZ='" + ETZ + '\'' +
                ", WalletAddr='" + WalletAddr + '\'' +
                ", RechargeAddr='" + RechargeAddr + '\'' +
                ", IsTrueName='" + IsTrueName + '\'' +
                ", InviteCode='" + InviteCode + '\'' +
                ", Access_Token='" + Access_Token + '\'' +
                '}';
    }
}
