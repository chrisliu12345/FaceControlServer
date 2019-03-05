package com.gd.domain;

public class HandResult<T> {
    /**
     * 处理成功
     */
    public static int Success=200;
    /**
     * 程序异常
     */
    public static int APPError=500;


    private int code;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
