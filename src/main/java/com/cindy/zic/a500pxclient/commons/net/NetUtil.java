package com.cindy.zic.a500pxclient.commons.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zic on 3/11/16.
 */
public class NetUtil {

    /* check network connectivity
    * para: context
    * return: true -> connected, false -> not connected
     */
    public static boolean CheckConnectivity(Context context)

    {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
