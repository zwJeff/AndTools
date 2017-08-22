package com.jeff.XNetUtils;

/**
 * 说明：
 * 作者： 张武
 * 日期： 2017/5/17.
 * email:wuzhang4@creditease.cn
 */

public interface DownloadListener<T> {

    void onstart();

    void onLoading(long total, long current, boolean isDownloading);

    void onSuccess(T response);

    void onFail(String result);

    void onCancle();

    void onFinished();



}
