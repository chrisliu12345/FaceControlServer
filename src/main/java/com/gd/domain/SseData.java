package com.gd.domain;

public class SseData {
    private String clientflag;
    private Object data;
    private int code;

    public String getClientflag() {
        return clientflag;
    }

    public void setClientflag(String clientflag) {
        this.clientflag = clientflag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
