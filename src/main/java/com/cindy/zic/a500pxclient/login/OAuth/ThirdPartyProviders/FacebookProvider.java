package com.cindy.zic.a500pxclient.login.OAuth.ThirdPartyProviders;

import com.cindy.zic.a500pxclient.login.OAuth.OAuthConstants;
import com.cindy.zic.a500pxclient.login.OAuth.OAuthParameters;
import com.cindy.zic.a500pxclient.login.OAuth.XAuthProvider;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by zic on 3/12/16.
 */
public class FacebookProvider extends XAuthProvider {

    private String facebookToken;

    public FacebookProvider(String facebookToken) {
        super();
        this.facebookToken = facebookToken;
    }

    protected ArrayList<NameValuePair> buildParameters(OAuthParameters params) {
        ArrayList<NameValuePair> tuples = new ArrayList<NameValuePair>();
        tuples.add(new BasicNameValuePair(OAuthConstants.MODE, "facebook_auth"));
        params.put(OAuthConstants.MODE, "facebook_auth");
        tuples.add(new BasicNameValuePair(OAuthConstants.X_TOKEN, facebookToken));
        params.put(OAuthConstants.X_TOKEN, facebookToken);

        return tuples;
    }
}
