package com.cindy.zic.a500pxclient.login;

/**
 * Created by zic on 3/10/16.
 */
public interface LoginView {

    public void SetProgressBarVisibility(int visibility);

    public void SetWebViewVisibility(int visibility);

    public void OnLoginResult(boolean result);

    public void OnSignUpReturned();

}
