package com.cindy.zic.a500pxclient.login.OAuth;

/**
 * Created by zic on 3/12/16.
 */
import org.apache.http.client.methods.HttpPost;


public interface OAuthProvider {
    void signForAccessToken(HttpPost req) throws Exception;

    void setOAuthConsumer(String consumerKey, String consumerSecret);
    void setOAuthRequestToken(String requestTokenKey, String requestTokenSecret);
}