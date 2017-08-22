package com.jeff.XNetUtils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jeff.AndTool;
import com.jeff.andUtils.FormatUtil;
import com.jeff.andUtils.NetCheckUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.io.File;
import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 说明：
 * 作者： 张武
 * 日期： 2017/5/17.
 * email:wuzhang4@creditease.cn
 */

public class XHttpUtils {

    public static final int RETRY_TIME = 0;//重试次数
    public static final int TIMEOUT_SOCKET = 30 * 1000; //超时时间，默认30秒
    public static final int METHOD_GET = 0x01;
    public static final int METHOD_POST = 0x02;

    private volatile static XHttpUtils instance;
    private Handler handler;

    private XHttpUtils() {
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static XHttpUtils getInstance() {
        if (instance == null) {
            synchronized (XHttpUtils.class) {
                if (instance == null) {
                    instance = new XHttpUtils();
                }
            }
        }
        return instance;
    }


    /**
     * 异步请求
     *
     * @param request
     * @param callback
     */
    public void requestSyn(int method, final Request request, final RequestListener callback) {
        if (!NetCheckUtils.isNetworkAvailable(AndTool.getInstanc().application.getApplicationContext())) {
            callback.onError("当前网络不可用");
            return;
        }
        RequestParams params = getRequestParams(request.getUrl());
        List<KeyValue> keyValueList = request.getParameter(Request.PARAMS);
        if (null != keyValueList && !keyValueList.isEmpty()) {
            for (KeyValue keyValue : keyValueList) {
                if (keyValue != null) {
                    params.addBodyParameter(keyValue.key, keyValue.getValueStr());
                }
            }

        }

        Log.v("jeff_http", "请求地址:" + params.getUri() + " 请求报文：" + FormatUtil.objToJson(params));
        Log.v("jeff_http", "请求地址:" + params.getUri() + " 请求参数：" + FormatUtil.objToJson(request.getParameter(Request.PARAMS)));
        if (method == METHOD_GET) {
            x.http().get(params, getCallBack(callback, request));
        } else {
            x.http().post(params, getCallBack(callback, request));
        }
    }

    /**
     * 带缓存数据的异步post请求
     *
     * @param url
     * @param keyValueList
     * @param ifCache      是否缓存
     * @param cacheTime    缓存存活时间
     * @param callback
     */
    public void postCache(String url, List<KeyValue> keyValueList, final boolean ifCache, long cacheTime, final RequestListener callback) {
        RequestParams params = new RequestParams(url);
        params.setCacheMaxAge(cacheTime);
        if (null != keyValueList && keyValueList.isEmpty()) {
            for (KeyValue keyValue : keyValueList) {
                if (keyValue != null) {
                    params.addBodyParameter(keyValue.key, keyValue.getValueStr());
                }
            }
        }
        x.http().post(params, new Callback.CacheCallback<String>() {
            private boolean hasError = false;
            private String result = null;

            @Override
            public boolean onCache(String result) {
                if (ifCache && null != result) {
                    this.result = result;
                }
                // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
                return ifCache;
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    this.result = result;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasError = true;
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (!hasError && result != null) {
//                    onSuccessResponse(result,callback);
                }
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     * @param callback
     */
    public void downFile(String url, String filePath, final DownloadListener callback) {
        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(filePath);
        params.setAutoRename(true);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(final File result) {
                //下载完成会走该方法
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onSuccess(result);
                        }
                    }
                });
            }

            @Override
            public void onError(final Throwable ex, boolean isOnCallback) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (null != callback) {
                            callback.onFail(ex.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onCancelled(CancelledException c) {
                callback.onCancle();
            }

            @Override
            public void onFinished() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFinished();
                        }
                    }
                });
            }

            //网络请求之前回调
            @Override
            public void onWaiting() {
            }

            //网络请求开始的时候回调
            @Override
            public void onStarted() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (null != callback) {
                            callback.onstart();
                        }
                    }
                });
            }

            //下载的时候不断回调的方法
            @Override
            public void onLoading(final long total, final long current, final boolean isDownloading) {
                //当前进度和文件总大小
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onLoading(total, current, isDownloading);
                        }
                    }
                });
            }
        });
    }

    /**
     * 文件上传
     *
     * @param request
     * @param callback
     */
    public void postFiles(final Request request, final UploadListener callback) {
        RequestParams params = getRequestParams(request.getUrl());
        List<KeyValue> keyValueList = request.getParameter(Request.PARAMS);
        if (null != keyValueList && !keyValueList.isEmpty()) {
            for (KeyValue keyValue : keyValueList) {
                if (keyValue != null) {
                    if (!TextUtils.isEmpty(keyValue.getValueStr()) && keyValue.getValueStr().startsWith("/storage/emulated/")) {
                        File file = new File(keyValue.getValueStr());
                        params.addBodyParameter(keyValue.key, file, "multipart/form-data;boundary=---------------pedataisfantasticforvcpe");
                    } else
                        params.addBodyParameter(keyValue.key, keyValue.getValueStr());
                }
            }
        }
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        params.setMultipart(true);
        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onWaiting() {
                Log.v("jeff_http", "等待中...");
            }

            @Override
            public void onStarted() {
                Log.v("jeff_http", "上传开始");
                callback.onstart();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                Log.v("jeff_http", "已上传 " + current + " / " + total);
                callback.onLoading(total, current, isDownloading);
            }

            @Override
            public void onSuccess(String result) {
                Log.v("jeff_http", "返回：" + result);
                if (!TextUtils.isEmpty(result)) {
                    BaseResponse resp = (BaseResponse) FormatUtil.jsonToObj(result, request.getR_calzz());
                    if (resp.getErrorCode() == 0)
                        callback.onSuccess(resp);
                    else
                        callback.onFail(resp.getErrorMessage());
                } else {
                    callback.onFail("返回数据异常");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.v("jeff_http", "出错 error：" + ex.getMessage());
                if (!isOnCallback)
                    callback.onFail(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.v("jeff_http", "取消上传");
                callback.onCancle();
            }

            @Override
            public void onFinished() {
                Log.v("jeff_http", "上传结束！");
                callback.onFinished();
            }
        });

    }

    /**
     * 上传Json串到服务器
     *
     * @param maps 将需要传的各个参数放在Map集合里面
     */
    public void upLoadJson(final Request request, Map<String, String> maps, final RequestListener callback) {
        JSONObject json_request = new JSONObject();//服务器需要传参的json对象
        try {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                json_request.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = getRequestParams(request.getUrl());
        params.setAsJsonContent(true);
        params.setBodyContent(json_request.toString());
        Log.v("jeff_http", "请求地址:" + params.getUri() + " 请求参数：" + FormatUtil.objToJson(params.getBodyParams()));
        x.http().post(params, getCallBack(callback, request));

    }


    /**
     * 普通数据接口的统一回调处理
     *
     * @param callback
     * @param request
     * @return
     */
    private Callback.CommonCallback getCallBack(final RequestListener callback, final Request request) {
        return new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.v("jeff_http", "返回：" + result.toString());
                if (!TextUtils.isEmpty(result)) {
                    DbCookieStore instance = DbCookieStore.INSTANCE;
                    List<HttpCookie> cookies = instance.getCookies();
                    for (HttpCookie cookie : cookies) {
                        String name = cookie.getName();
                        String value = cookie.getValue();
                        if ("JSESSIONID".equals(name)) {
//                            AndTool.getInstanc().application.setSession(value);
                            break;
                        }
                    }


                    BaseResponse response = (BaseResponse) FormatUtil.jsonToObj(result, request.getR_calzz());
                    if (response.getErrorCode() == 0)
                        callback.onSuccess(response);
                    else
                        callback.onError(response.getErrorMessage());
                } else {
                    callback.onError("接口返回数据为空");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                String errorMsg = "";
                if (!TextUtils.isEmpty(ex.getMessage()))
                    errorMsg += "ex.getMessage()=" + ex.getMessage();
                if (!TextUtils.isEmpty(ex.getLocalizedMessage()))
                    errorMsg += "ex.getLocalizedMessage()=" + ex.getLocalizedMessage();
                callback.onError("请求异常：" + errorMsg);
                LogUtil.v("请求异常：" + errorMsg);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callback.onFailed("请求取消");
            }

            @Override
            public void onFinished() {
                callback.finish();
            }

        };
    }


    /**
     * 添加统一请求Header
     */
    public void setHeader(RequestParams params, boolean isNeedCookie) {

        /**
         * JSESSIONID
         */
//        if (isNeedCookie)
//            params.setHeader("Cookie", "JSESSIONID=" + MyApplication.getMyApplication().getSession());
        /**
         * 客户端时间 yyyy-MM-dd HH:mm:ss.SSS  clientDatetime
         */
        params.setHeader("clientDatetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
                .format(Calendar.getInstance().getTime()));
        /**
         * 客户端版本 appVersion
         */
        params.setHeader("appVersion", AndTool.getInstanc().VERSION_NAME);
        /**
         * 手机操作系统
         */
        params.setHeader("systemType", "Android");
    }


    /**
     * 基本的请求参数
     */
    public static org.xutils.http.RequestParams getRequestParams(String url) {

        org.xutils.http.RequestParams requestParams = new org.xutils.http.RequestParams(url);
        requestParams.setConnectTimeout(TIMEOUT_SOCKET);
        requestParams.setReadTimeout(TIMEOUT_SOCKET);
        requestParams.setMaxRetryCount(RETRY_TIME);
        requestParams.setUseCookie(true);
//        //添加头信息
//        if (url.endsWith("api/user/login") || url.endsWith("api/user/register") || url.endsWith("api/user/registerSMSCode"))
//            //登录、注册相关接口不设置cookie
            XHttpUtils.getInstance().setHeader(requestParams, false);
//        else {
//            XHttpUtils.getInstance().setHeader(requestParams, true);
//        }
        //其他请求参数请在requestParams中添加
        return requestParams;
    }


}
