package com.jeff.XNetUtils;

/**
 * 说明：
 * 作者： 张武
 * 日期： 2017/5/18.
 * email:wuzhang4@creditease.cn
 */

public interface RequestListener {

    void onSuccess(BaseResponse resp);

    void onFailed(String errorMsg);

    void onError(String errorMsg);

    void finish();


}
