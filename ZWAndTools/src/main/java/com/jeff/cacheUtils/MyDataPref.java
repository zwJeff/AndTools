package com.jeff.cacheUtils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jeff.AndTool;
import com.jeff.andUtils.FormatUtil;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * 说明： 数据缓存类
 * 作者： 张武
 * 日期： 2016/11/9.
 * email:jeff_zw@qq.com
 */

public class MyDataPref {


    //数据缓存map
    private HashMap<String, Object> mLocalDatas;
    private SharedPreferences appSharePref;
    //sharePreference名字
    public static final String APP_SHARED_PREFERENCE_NAME = "lhcircle_sharePreference";

    private Object localDatalock = new Object();
    private Object preferDatalock = new Object();

    public MyDataPref() {
        synchronized (localDatalock) {
            if (mLocalDatas == null)
                mLocalDatas = new HashMap<>();
        }
    }

    /**
     * 增加数据到Map
     * 用对象的CanonicalName作为Key
     *
     * @param obj
     * @param <T>
     */
    public <T> void addToLocalData(T obj) {

        if (obj != null)
            mLocalDatas.put(obj.getClass().getCanonicalName(), obj);

    }

    /**
     * 从Map中读取数据
     * 用对象的CanonicalName作为Key
     *
     * @param obj
     * @param <T>
     */
    public <T> T getLocalData(Class<T> obj) {

        T result = null;

        if (obj != null) {
            result = (T) mLocalDatas.get(obj.getCanonicalName());
            if (result == null) {
                //todo 如果Map中没有，从preference中取
                result = pullFromPref(obj);
                if (result != null)
                    addToLocalData(result);
            }
        }
        return result;
    }

    /**
     * 将数据存入sharedPreference
     * 用CanonicalName作为key，数据以json串的形式存入
     *
     * @param obj
     * @param <T>
     */
    public <T> void pushToPref(T obj) {
        appSharePref = AndTool.getInstanc().application.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = appSharePref.edit();
        editor.putString(obj.getClass().getCanonicalName(), FormatUtil.objToJson(obj));
        editor.commit();
    }

    public <T> T pullFromPref(Class<T> obj) {
        appSharePref = AndTool.getInstanc().application.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        String json = appSharePref.getString(obj.getCanonicalName(), "");
        if (TextUtils.isEmpty(json))
            return null;
        return (T) FormatUtil.jsonToObj(json, obj);
    }


    /**
     * 增加指定key的String到缓存
     *
     * @param key
     * @param value
     */
    public void addToLocalData(String key, String value) {

        if (!TextUtils.isEmpty(key))
            mLocalDatas.put(key, value);

    }


    /**
     * 增加指定key的String到缓存
     *
     * @param key
     * @param value
     */
    public void pushToPref(String key, String value) {
        appSharePref = AndTool.getInstanc().application.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = appSharePref.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * 从缓存中取出指定key的String
     *
     * @param key
     */
    public String getLocalData(String key) {
        String result = "";
        if (!TextUtils.isEmpty(key)) {
            result= (String) mLocalDatas.get(key);
            if (TextUtils.isEmpty(result)) {
                //todo 如果Map中没有，从preference中取
                result = pullFromPref(key);
                if (result != null)
                    addToLocalData(key, result);
            }
        }
        return result;
    }


    /**
     * 从sharePre中取出指定key的String
     *
     * @param key
     */
    public String pullFromPref(String key) {
        appSharePref = AndTool.getInstanc().application.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        String result = appSharePref.getString(key, "");
        return result;
    }


}
