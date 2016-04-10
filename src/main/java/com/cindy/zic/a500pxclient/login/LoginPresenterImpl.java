package com.cindy.zic.a500pxclient.login;

import android.util.Log;
import android.widget.Toast;

import com.cindy.zic.a500pxclient.commons.Constants;
import com.cindy.zic.a500pxclient.login.OAuth.AccessToken;
import com.cindy.zic.a500pxclient.login.OAuth.OAuthAuthorization;
import com.cindy.zic.a500pxclient.login.OAuth.ThirdPartyProviders.FacebookProvider;
import com.cindy.zic.a500pxclient.login.OAuth.ThirdPartyProviders.WeiboProvider;
import com.cindy.zic.a500pxclient.login.OAuth.XAuthProvider;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zic on 3/10/16.
 */
public class LoginPresenterImpl implements LoginPresenter, LoginCallbacks {

    public static final String TAG = "LoginPresenter";

    @Override
    public AccessToken loginXAuth(String email, String password){
        final XAuthProvider x_provider = new XAuthProvider(email, password);
        final AccessToken accessToken = new AccessToken();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OAuthAuthorization auth = OAuthAuthorization.build(Constants.BASE_URL, Constants.PX_CONSUMER_KEY, Constants.PX_CONSUMER_SECRET);
                try {
                    if (auth.authorize(x_provider)) {
                        accessToken.setToken(auth.getAccessToken(x_provider).getToken());
                        Log.d(TAG, accessToken.getToken());
                        // TODO: this may not be correct, we assume access token won't expire and can be used next time we open the app
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Acquire access token failed...");
                }
            }
        }).start();
        return accessToken;
    }

    @Override
    public AccessToken loginOAuthFb() {
        OAuthAuthorization auth = OAuthAuthorization.build(Constants.FB_OAUTH_BASE_URL, Constants.FB_CONSUMER_KEY, Constants.FB_CONSUMER_SECRET);
        FacebookProvider fb_provider = new FacebookProvider(auth.getRequestToken());
        try {
            if (auth.authorize(fb_provider)) {
                AccessToken fb_token = auth.getAccessToken(fb_provider);
                return fb_token;
            }
        } catch (Exception e) {
            Log.e(TAG, "Acquire access token failed...");
        }
        return null;
    }

    @Override
    public AccessToken loginOAuthWb() {
        OAuthAuthorization auth = OAuthAuthorization.build(Constants.WB_OAUTH_BASE_URL, Constants.WB_CONSUMER_KEY, Constants.WB_CONSUMER_SECRET);
        WeiboProvider wb_provider = new WeiboProvider(auth.getRequestToken());
        try {
            if (auth.authorize(wb_provider)) {
                AccessToken wb_token = auth.getAccessToken(wb_provider);
                return wb_token;
            }
        } catch (Exception e) {
            Log.e(TAG, "Acquire access token failed...");
        }
        return null;
    }

    @Override
    public void onSuccess (int statusCode, JSONObject response){

    }

    @Override
    public void onSuccess (int statusCode, JSONArray response){

    }

    @Override
    public void onFailure (int statusCode, Throwable error){

    }
}
