package com.etzSharding.app;


/**
 * Created by Administrator on 2018/1/29.
 */

public class UrlFactory {

    public static final String host = "https://skating.wanlege.com";


    //手机注册
    public static String getPhoneRegisterURL() {
        return host + "/ubi/account/registerByPhone";
    }
    //邮箱注册
    public static String getEmailRegisterURL() {
        return host + "/ubi/account/registerByMail";
    }
    //手机册验证码
    public static String getPhoneCodeURL() { return host + "/ubi/account/getSMSCode"; }
    //邮箱验证码
    public static String getEmailCodeURL() {
        return host + "/ubi/account/getMailCode";
    }
    //验证手机验证码
    public static String getVerifySMSCodeURL() { return host + "/ubi/account/verifySMSCode"; }
    //验证邮箱验证码
    public static String getVerifyMailCodeURL() { return host + "/ubi/account/verifyMailCode"; }
    //手机登录
    public static String getLoginPhoneURL() { return host + "/ubi/account/loginByPhone"; }
    //邮箱登录
    public static String getLoginEmailURL() { return host + "/ubi/account/loginByMail"; }
    //用户信息
    public static String getUserInfoURL() { return host + "/ubi/account/getUserInfo"; }
    //提现
    public static String getWithdrawURL() { return host + "/ubi/account/withdraw"; }
    //手机账户重置密码
    public static String getRetsetPWDbyPhoneURL() { return host + "/ubi/account/retsetPWDbyPhone"; }
    //邮箱账户重置密码
    public static String getRetsetPWDbyMailURL() { return host + "/ubi/account/retsetPWDbyMail"; }
    //绑定钱包地址
    public static String getBindWalletURL() { return host + "/ubi/account/bindWallet"; }
    //签到
    public static String getSigninURL() { return host + "/ubi/account/signin"; }
    //实人认正
    public static String getAliDescribeVerifyTokenURL() { return host + "/ubi/account/AliDescribeVerifyToken"; }
    public static String getAliDescribeVerifyResultURL() { return host + "/ubi/account/AliDescribeVerifyResult"; }

    //节点
    //节点列表
    public static String getNodeListURL() { return host + "/ubi/mining/getNodeList"; }
    //购买节点
    public static String buyNodeURL() { return host + "/ubi/mining/buyNode"; }
    //我的节点列表
    public static String getMyNodeListURL() { return host + "/ubi/mining/getMyNodeList"; }
    //我的节点收益列表
    public static String getNodeRewardListURL() { return host + "/ubi/mining/getNodeRewardList"; }
    //团队收益列表
    public static String getTeamRewardListURL() { return host + "/ubi/mining/getTeamRewardList"; }
    //团队节点列表
    public static String getTeamNodeListURL() { return host + "/ubi/mining/getTeamNodeList"; }
    public static String getDetailedListURL() { return host + "/ubi/mining/getDetailedList"; }
    //邀请链接
    public static String getregisterH5URL() { return  "https://sharding.etherzero.com/ETZ/index.html#/register?invitcode="; }
}
