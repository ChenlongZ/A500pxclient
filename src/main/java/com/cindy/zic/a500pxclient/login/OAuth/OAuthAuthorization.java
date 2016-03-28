package com.cindy.zic.a500pxclient.login.OAuth;

import android.content.Entity;
import android.util.Log;

import com.cindy.zic.a500pxclient.commons.Constants;
import com.cindy.zic.a500pxclient.commons.utils.HttpParamUtil;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by zic on 3/12/16.
 */
public class OAuthAuthorization {

    private static final String TAG = OAuthAuthorization.class.getSimpleName();

    private String consumerKey;
    private String consumerSecrect;

    private String requestToken;
    private String requestSecrect;

    private String url;
    private String request_token_url;
    private String access_token_url;
    private String authorization_url;

    private OAuthAuthorization(String url) {
        this.url = url;
        this.request_token_url = url + Constants.OAUTH_REQUEST_TOKEN_SUFFIX;
        this.access_token_url = url + Constants.OAUTH_ACCESS_TOKEN_SUFFIX;
        this.authorization_url = url + Constants.OAUTH_AUTHORIZE_SUFFIX;
    }

    // third party provider info goes there;
    public static OAuthAuthorization build(String url, String k, String s){
        Builder builder = new Builder(url);
        OAuthAuthorization o = builder.consumerKey(k).consumerSecrect(s).build();
        return o;
    }

    // retrieve request token from provider
    private void init() throws Exception{
        CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecrect);
        CommonsHttpOAuthProvider provider = new CommonsHttpOAuthProvider(request_token_url, access_token_url, authorization_url);
        final String requestokenUrl = provider.retrieveRequestToken(consumer, "");
        this.requestToken = HttpParamUtil.getUrlParamValue(requestokenUrl, "oauth_token");
        this.requestSecrect = consumer.getTokenSecret();
    }

    public boolean authorize(OAuthProvider provider) throws Exception{
        final HttpPost post = new HttpPost(this.authorization_url);

        provider.setOAuthConsumer(this.consumerKey, this.consumerSecrect);
        provider.setOAuthRequestToken(this.requestToken, this.requestSecrect);

        provider.signForAccessToken(post);

        HttpClient client = new DefaultHttpClient();
        HttpResponse res = client.execute(post);

        if (res.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            HttpEntity entity = res.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Log.e(TAG, "OAuth authorization failed: " + responseString);
            return false;
        }


        return true;
    }

    public AccessToken getAccessToken(OAuthProvider provider) throws Exception{
        final HttpPost post = new HttpPost(this.access_token_url);

        provider.setOAuthConsumer(this.consumerKey, this.consumerSecrect);
        provider.setOAuthRequestToken(this.requestToken, this.requestSecrect);
        provider.signForAccessToken(post);

        HttpClient client = new DefaultHttpClient();
        HttpResponse res = client.execute(post);

        if (res.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            Log.e(TAG, "OAuth - acquire access token failed: " + EntityUtils.toString(res.getEntity(), "UTF-8"));
            throw new Exception("Http response status code != 200");
        }

        return new AccessToken(res);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        final OAuthAuthorization oauth = new OAuthAuthorization(this.url);
        oauth.consumerKey = this.consumerKey;
        oauth.consumerKey = this.consumerSecrect;
        return oauth;
    }

    public static class Builder {

        private OAuthAuthorization instance;

        public Builder(String url) {
            this.instance = new OAuthAuthorization(url);
        }

        public Builder consumerKey(String consumerKey) {
            this.instance.consumerKey = consumerKey;
            return this;
        }

        public Builder consumerSecrect(String consumerSecrect) {
            this.instance.consumerSecrect = consumerSecrect;
            return this;
        }

        public OAuthAuthorization build() {
            try {
                OAuthAuthorization o = (OAuthAuthorization) this.instance.clone();
                o.init();
                return o;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public String getRequestToken() {return this.requestToken;}

}
