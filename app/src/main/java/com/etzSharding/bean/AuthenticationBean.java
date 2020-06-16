package com.etzSharding.bean;

public class AuthenticationBean {
    private String RequestId;
    private String VerifyToken;
    private String VerifyStatus;

    public String getRequestId() {
        return RequestId;
    }

    public String getVerifyToken() {
        return VerifyToken;
    }

    public String getVerifyStatus() {
        return VerifyStatus;
    }
}
