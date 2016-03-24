package com.cindy.zic.a500pxclient.commons.net;

import com.cindy.zic.a500pxclient.commons.Constants;
import com.cindy.zic.a500pxclient.login.OAuth.AccessToken;

import java.lang.reflect.AccessibleObject;

/**
 * Created by zic on 3/12/16.
 */
public class PxApi {

    private static final String TAG = PxApi.class.getSimpleName();

    private static final String BASE_URL = Constants.BASE_URL;

    private AccessToken accessToken;
    private String consumerKey;
    private String consumerSecrect;

    public PxApi(AccessToken accessToken, String consumerKey, String consumerSecrect) {
        this.accessToken = accessToken;
        this.consumerKey = consumerKey;
        this.consumerSecrect = consumerSecrect;
    }

    public PxApi(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getBaseUrl () {
        return BASE_URL;
    }
}
