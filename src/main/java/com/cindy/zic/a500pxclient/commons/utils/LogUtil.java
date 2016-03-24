package com.cindy.zic.a500pxclient.commons.utils;

import android.util.Log;

/**
 * Created by zic on 3/12/16.
 */
public class LogUtil {

    private static final String TAG = LogUtil.class.getSimpleName();

    public static void e(String error){
        // TODO: write to log file

        // log to system log
        Log.e(TAG, error);
    }
}