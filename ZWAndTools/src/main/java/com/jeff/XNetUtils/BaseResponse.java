package com.jeff.XNetUtils;

/**
 * 说明： 请求返回基类对象
 * 作者： 张武
 * 日期： 2017/5/17.
 * email:wuzhang4@creditease.cn
 */

public class BaseResponse{

    private int errorCode; // 0 - 请求成功  1 - 请求失败

    private String errorMessage;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
