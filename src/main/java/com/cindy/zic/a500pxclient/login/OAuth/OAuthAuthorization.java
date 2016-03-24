package com.cindy.zic.a500pxclient.login.OAuth;

import com.cindy.zic.a500pxclient.commons.Constants;

/**
 * Created by zic on 3/12/16.
 */
public class OAuthAuthorization {

    private String consumerKey;
    private String consumerSecrect;

    private String requestToken;
    private String requestSecrect;

    private String request_token_url = Constants.OAUTH_REQUEST_TOKEN_URL;
    private String access_token_url = Constants.OAUTH_ACCESS_TOKEN_URL;
    private String authorization = Constants.OAUTH_AUTHORIZE_URL;

    OAuthAuthorization() {};

    public static OAuthAuthorization build(String k, String s){
        Builder builder = new Builder();
        OAuthAuthorization o = builder.consumerKey(k).consumerSecrect(s).build();
        return o;
    }

    private void init() throws Exception{

    }

    public boolean authorize(OAuthProvider provider) {
        return false;
    }

    public AccessToken getAccessToken(OAuthProvider provider){
        return null;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        final OAuthAuthorization oauth = new OAuthAuthorization();
        oauth.consumerKey = this.consumerKey;
        oauth.consumerKey = this.consumerSecrect;
        return oauth;
    }

    public static class Builder {

        private OAuthAuthorization instance;

        public Builder() {
            this.instance = new OAuthAuthorization();
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
                OAuthAuthorization o = (OAuthAuthorization)this.instance.clone();
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

}
