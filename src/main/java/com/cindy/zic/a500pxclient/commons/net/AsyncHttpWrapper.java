package com.cindy.zic.a500pxclient.commons.net;

import android.content.Context;
import android.util.Log;

import com.cindy.zic.a500pxclient.commons.callbacks.BaseCallback;
import com.cindy.zic.a500pxclient.commons.utils.LogUtil;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * call AsyncHttpClient post and get method
 * define responseHandler
 * call callbacks in responseHandlers
 * convert returned parameter into objects
 */
public class AsyncHttpWrapper {

    public static final String TAG = AsyncHttpWrapper.class.getSimpleName();

    public static void post(Context context, String url, HashMap<String, String> params, BaseCallback callback){
        // init async http client
        AsyncHttpClient client = new AsyncHttpClient();

        // parsing params
        RequestParams requestParams = new RequestParams(params);

        // executing request
        client.post(context, url, requestParams, new CustomizedHandler(callback));
    }

    public static void get(Context context, String url, HashMap<String, String> params, BaseCallback callback){
        // init async http client
        AsyncHttpClient client = new AsyncHttpClient();

        // parsing params
        RequestParams requestParams = new RequestParams(params);

        // executing request
        client.get(context, url, requestParams, new CustomizedHandler(callback));
    }

    static class CustomizedHandler extends JsonHttpResponseHandler {

        private BaseCallback callback;

        public CustomizedHandler(BaseCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            if (callback != null) {
                this.callback.onSuccess(statusCode, response);
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            if (callback != null) {
                this.callback.onSuccess(statusCode, response);
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            Log.e(TAG, "AsyncJson bad response:" + statusCode);
            if (callback != null) {
                this.callback.onFailure(statusCode, throwable);
                if (errorResponse != null) {
                    String error = errorResponse.toString();
                    LogUtil.e(error);
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.e(TAG, "AsyncJson bad response:" + statusCode);
            if (callback != null) {
                this.callback.onFailure(statusCode, throwable);
                if (errorResponse != null) {
                    String error = errorResponse.toString();
                    LogUtil.e(error);
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
            Log.e(TAG, "AsyncJson bad response:" + statusCode);
            if (callback != null) {
                this.callback.onFailure(statusCode, throwable);
                if (errorResponse != null) {
                    LogUtil.e(errorResponse);
                }
            }
        }
    }

}
