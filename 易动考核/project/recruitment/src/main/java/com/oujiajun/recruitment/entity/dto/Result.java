package com.oujiajun.recruitment.entity.dto;

import com.alibaba.fastjson.JSONObject;

/**
 * @author oujiajun
 */
public class Result<T> {
    private String resultCode;
    private String message;
    private T data;

    public Result() {
    }

    public Result(String resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public String toJsonString(){
        return JSONObject.toJSONString(this);
    }
}
