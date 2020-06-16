package com.etzSharding.bean;

import java.io.Serializable;

public class BaseEtzBean<T> implements Serializable {

    public int code;
    public String msg;
    public T data = null;

    @Override
    public String toString() {
        return "BaseEtzBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}