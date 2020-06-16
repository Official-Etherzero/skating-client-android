package com.etzSharding.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import com.etzSharding.app.MyApp;

import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Util {
    /**
     * 字符串是否为空。空返回true
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty()||str.equalsIgnoreCase("null");
    }
    /**
     * byte[]是否为空。空返回true
     * @param arr
     * @return
     */
    public static boolean isNullOrEmpty(byte[] arr) {
        return arr == null || arr.length == 0;
    }
    /**
     * Collection是否为空。空返回true
     * @param collection
     * @return
     */
    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static int getPixelsFromDps(Context context, int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public static void correctTextSizeIfNeeded(TextView v) {
        int limit = 100;
        int lines = v.getLineCount();
        float px = v.getTextSize();
        while (lines > 1 && !v.getText().toString().contains("\n")) {
            limit--;
            px -= 1;
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, px);
            lines = v.getLineCount();
            if (limit <= 0) {
                MyLog.e( "correctTextSizeIfNeeded: Failed to rescale, limit reached, final: " + px);
                break;
            }
        }
    }
    /**
     * dip 转 px
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        float scale = MyApp.getmInstance().getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }

    /**
     * 把String转化为double
     * @param number
     * @param defaultValue
     * @return
     */
    public static double convertToDouble(String number, double defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(number);
        } catch (Exception e) {
            return defaultValue;
        }

    }
    public static boolean isAddressValid(String address) {
        String regExp="^0[xX][0-9a-fA-F]{40}$";
        return address.matches(regExp);
//        return !isNullOrEmpty(address) && address.startsWith("0x")&&address.length()==42;
    }


    /**
     * 通过网络接口取
     * @return
     */
    public static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 验证密码 至少一个字母和数字8-16位
     */
    public static boolean isPassword(String strs) {
//        String regex = "^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z\\d]{8,16}$";
//        return strs.matches(regex);
        return strs.length()>=6;
    }

    /**
     * 验证邮箱
     */
    public static boolean isEmail(String str) {
        String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
        return str.matches(regex);
    }
    /**
     * UTC时间 ---> 当地时间
     * * @param utcTime
     * UTC时间
     * * @return
     */
    public static String utc2Local(String utcTime, boolean isTime) {
        utcTime = utcTime.replace("T", " ");
        utcTime = utcTime.substring(0, utcTime.indexOf("."));

        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//UTC时间格式2020-03-17T07:26:04.000Z
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            MyLog.i(e.getMessage());
            return "";
        }
        SimpleDateFormat localFormater;
        if (isTime) {
            localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//当地时间格式
        } else {
            localFormater = new SimpleDateFormat("yyyy-MM-dd");//当地时间格式
        }


        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }
}
