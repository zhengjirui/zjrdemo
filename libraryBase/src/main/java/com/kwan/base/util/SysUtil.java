package com.kwan.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

import com.kwan.base.BaseApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SysUtil {

    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_MOBILE = 1;
    public static final int NETWORK_WIFI = 2;

    public static String getAppPackageName() {
        return BaseApplication.getInstance().getPackageName();
    }

    public static int getNetworkState() {

        ConnectivityManager connManager = (ConnectivityManager) BaseApplication
                .getInstance().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        // Wifi
        State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (state == State.CONNECTED || state == State.CONNECTING) {
            return NETWORK_WIFI;
        }

        // 3G
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        if (state == State.CONNECTED || state == State.CONNECTING) {
            return NETWORK_MOBILE;
        }
        return NETWORK_NONE;
    }

    public static String milliSecond2Date(String template, long time) {
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.getDefault());
        return sdf.format(d);
    }

}
