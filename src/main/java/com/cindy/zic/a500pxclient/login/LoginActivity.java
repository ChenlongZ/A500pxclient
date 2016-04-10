package com.cindy.zic.a500pxclient.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cindy.zic.a500pxclient.R;
import com.cindy.zic.a500pxclient.commons.net.NetUtil;
import com.cindy.zic.a500pxclient.commons.utils.PrefUtil;
import com.cindy.zic.a500pxclient.login.OAuth.AccessToken;
import com.cindy.zic.a500pxclient.main.android.MainActivity;

import butterknife.ButterKnife;
import butterknife.Bind;

public class LoginActivity extends Activity implements View.OnClickListener, LoginView{

    private static final String TAG = LoginActivity.class.getSimpleName();
    private boolean isConnected;

    @Bind(R.id.loginBtn)        Button loginBtn;
    @Bind(R.id.signupBtn)       Button signupBtn;
    @Bind(R.id.loginEmail)      EditText loginEmail;
    @Bind(R.id.loginPassword)   EditText loginPassword;
    @Bind(R.id.fblogin)         Button fbloginBtn;
    @Bind(R.id.wblogin)          Button wbloginBtn;
    @Bind(R.id.loginProgressBar)ProgressBar loginProgressBar;

    private String tmpEmail;
    private String tmpPassword;
    private AccessToken px_token, fb_token, wb_token;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_login);

        // view injection
        ButterKnife.bind(this);

        // set listeners
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
        fbloginBtn.setOnClickListener(this);
        wbloginBtn.setOnClickListener(this);

        // Init
        tmpEmail = PrefUtil.getFromPrefs(this, PrefUtil.PREFS_LOGIN_USERNAME_KEY, "");
        tmpPassword = PrefUtil.getFromPrefs(this, PrefUtil.PREFS_LOGIN_PASSWORD_KEY, "");
        this.SetProgressBarVisibility(0);
        loginPresenter = new LoginPresenterImpl();

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        perform net check
         */
        isConnected = NetUtil.CheckConnectivity(this);

        // if connected
        if (isConnected) {
            // If did login before, retrieve data and do login
            // TODO: if you do this, when PxUser log out, tmpEmail and tmpPassword should be clear to preven auto login
            if (!tmpEmail.isEmpty() && !tmpPassword.isEmpty()) {
                loginEmail.setText(tmpEmail);
                loginPassword.setText(tmpPassword);
                loginPresenter.loginXAuth(tmpEmail, tmpPassword);
            }
            // TODO: add OAuth support
            // TODO: we assume access token won't expire and can be used for next time login, this needs to be validated
        } else {
          Toast.makeText(this, "Network ungeilivable", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        if (!isConnected) {
            Toast.makeText(this, "Network ungeilivable! Check connection", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.loginBtn:
                // login
                px_token = loginPresenter.loginXAuth(loginEmail.getText().toString(), loginPassword.getText().toString());
                SaveAccessToken(px_token, "px_token");
                break;
            case R.id.signupBtn:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://500px.com/signup")));
                break;
            case R.id.fblogin:
                fb_token = loginPresenter.loginOAuthFb();
                SaveAccessToken(fb_token, "fb_token");
                break;
            case R.id.wblogin:
                wb_token = loginPresenter.loginOAuthWb();
                SaveAccessToken(wb_token, "wb_token");
                break;
        }
    }


    @Override
    public void SetProgressBarVisibility(int visibility) {
        loginProgressBar.setVisibility(visibility);
    }

    @Override
    public void OnLoginResult(boolean result){
        // disable progressbar
        this.SetProgressBarVisibility(0);
        if (result) {
            // make a toast
            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
            // save PxUser info to local storage
            CacheUserCredential(loginEmail.getText().toString(), loginPassword.getText().toString());
            // clear text fields
            ClearText();
            // jump
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // make a toast
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            // clear cache
            CacheUserCredential("", "");
            // clear text
            ClearText();
        }
    }

    @Override
    public void OnSignUpReturned(){
        // TODO: fill email and password field with data
    }

    void CacheUserCredential(String username, String password) {
        tmpEmail = username;
        tmpPassword = password;
        PrefUtil.saveToPrefs(this, PrefUtil.PREFS_LOGIN_USERNAME_KEY, tmpEmail);
        PrefUtil.saveToPrefs(this, PrefUtil.PREFS_LOGIN_PASSWORD_KEY, tmpPassword);
    }

    void ClearText() {
        loginEmail.setText("");
        loginPassword.setText("");
    }

    private void SaveAccessToken(AccessToken accessToken, String tokenType) {
        PrefUtil.saveToPrefs(this, tokenType, accessToken.getToken());
        PrefUtil.saveToPrefs(this, tokenType + "_secret", accessToken.getTokenSecret());
    }

}
