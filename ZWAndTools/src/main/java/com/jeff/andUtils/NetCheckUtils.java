package com.jeff.andUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 说明： 网络状态检测类
 * 作者： 张武
 * 日期： 2017/4/11.
 * email:wuzhang4@creditease.cn
 */

public class NetCheckUtils {

    public static final int ONLY_WIFI = 0x001;
    public static final int ONLY_2G_3G_4G = 0x002;
    public static final int ALL_TYPE = 0x003;

    private static String TAG = NetCheckUtils.class.getName();
    private static int connectType = ALL_TYPE;


    /**
     * 根据当前网络连接方式设置 判断是否有网络连接
     */
    public static boolean isNetworkAvailable(Context context) {

        switch (connectType) {
            case ONLY_2G_3G_4G:
                return isMobileDataEnable(context);
            case ONLY_WIFI:
                return isWifiDataEnable(context);
            case ALL_TYPE:
                return isMobileDataEnable(context)||isWifiDataEnable(context);
            default:
        }
        return false;
    }

    /**
     * 判断wifi 是否可用
     *
     * @param context 上下文
     * @return isWifiDataEnable
     */
    public static boolean isWifiDataEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiDataEnable = false;
        isWifiDataEnable = connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return isWifiDataEnable;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context 上下文
     * @return isMobileDataEnable
     */
    public static boolean isMobileDataEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable = false;

        isMobileDataEnable = connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

        return isMobileDataEnable;
    }


    /**
     * 判断网络是否为漫游
     */
    public static boolean isNetworkRoaming(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.v(TAG, "couldn't get connectivity manager");
        } else {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null
                    && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager tm = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null && tm.isNetworkRoaming()) {
                    Log.v(TAG, "network is roaming");
                    return true;
                } else {
                    Log.v(TAG, "network is not roaming");
                }
            } else {
                Log.v(TAG, "not using mobile network");
            }
        }
        return false;
    }

    /**
     * 得到客户端IP
     */
    public static String getIP() {
        String ipaddress = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                if (intf.getName().toLowerCase().equals("eth0")
                        || intf.getName().toLowerCase().equals("wlan0")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf
                            .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            ipaddress = inetAddress.getHostAddress();
                            if (!ipaddress.contains("::")) {// ipV6的地址
                                return ipaddress;
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipaddress;

    }

}
