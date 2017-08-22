package com.jeff.XNetUtils;

import org.xutils.common.util.KeyValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明： 请求数据封装类
 *
 * 作者： 张武
 * 日期： 2017/5/17.
 * email:wuzhang4@creditease.cn
 */
public class Request<T> implements java.io.Serializable {

    public static String PARAMS="params";

    public Request() {
        parameters = new HashMap<String, List<KeyValue>>();
    }


    //请求参数
    private Map<String, List<KeyValue>> parameters;
    //访问地址
    private String url;
    //返回类型
    public Class<T> r_calzz;
    //某些接口单独设置超时时间
    private int outTimeSecond = 0;

    //是否信任緩存，若是 则不请求网络
    private boolean isTrustCache=false;

    //缓存有效时间
    private long cacheTime=0;

    public Request<T> addParameter(String name, List<KeyValue> value) {
        parameters.put(name, value);
        return this;
    }

    public List<KeyValue> getParameter(String name) {
        return parameters.get(name);
    }


    public String getUrl() {
        return url;
    }

    public Request<T> setUrl(String url) {
        this.url = url;
        return this;
    }


    public Class<T> getR_calzz() {
        return r_calzz;
    }

    public Request<T> setR_calzz(Class<T> r_calzz) {
        this.r_calzz = r_calzz;
        return this;
    }


    public int getOutTimeSecond() {
        return outTimeSecond;
    }

    public Request<T> setOutTimeSecond(int outTimeSecond) {
        this.outTimeSecond = outTimeSecond;
        return this;
    }
}