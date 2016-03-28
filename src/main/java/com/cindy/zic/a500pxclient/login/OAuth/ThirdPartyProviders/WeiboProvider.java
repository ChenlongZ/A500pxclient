package com.cindy.zic.a500pxclient.login.OAuth.ThirdPartyProviders;

import com.cindy.zic.a500pxclient.login.OAuth.OAuthConstants;
import com.cindy.zic.a500pxclient.login.OAuth.OAuthParameters;
import com.cindy.zic.a500pxclient.login.OAuth.OAuthProvider;
import com.cindy.zic.a500pxclient.login.OAuth.XAuthProvider;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by zic on 3/12/16.
 */
public class WeiboProvider extends XAuthProvider {

    private String weiboToken;

    public WeiboProvider(String weiboToken) {
        super();
        this.weiboToken = weiboToken;
    }

    protected ArrayList<NameValuePair> buildParameters(OAuthParameters params) {
        ArrayList<NameValuePair> tuples = new ArrayList<NameValuePair>();
        tuples.add(new BasicNameValuePair(OAuthConstants.MODE, "weibo_auth"));
        params.put(OAuthConstants.MODE, "weibo_auth");
        tuples.add(new BasicNameValuePair(OAuthConstants.X_TOKEN, weiboToken));
        params.put(OAuthConstants.X_TOKEN, weiboToken);

        return tuples;
    }
}
