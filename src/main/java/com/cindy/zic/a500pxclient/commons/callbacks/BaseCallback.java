package com.cindy.zic.a500pxclient.commons.callbacks;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by zic on 3/12/16.
 */
public interface BaseCallback {

    public void onSuccess (int statusCode, JSONObject response);
    public void onSuccess (int statusCode, JSONArray response);
    public void onFailure (int statusCode, Throwable error);
}
