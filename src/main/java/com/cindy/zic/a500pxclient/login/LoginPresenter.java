package com.cindy.zic.a500pxclient.login;

import com.cindy.zic.a500pxclient.login.OAuth.AccessToken;

/**
 * Created by zic on 3/10/16.
 */
public interface LoginPresenter {

    public AccessToken loginXAuth(String email, String password);
    public AccessToken loginOAuthFb();
    public AccessToken loginOAuthWb();
}
