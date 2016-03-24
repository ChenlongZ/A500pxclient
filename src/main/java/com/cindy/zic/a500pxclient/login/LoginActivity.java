package com.cindy.zic.a500pxclient.login;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cindy.zic.a500pxclient.R;
import com.cindy.zic.a500pxclient.commons.net.NetUtil;
import com.cindy.zic.a500pxclient.commons.utils.PrefUtil;
import com.cindy.zic.a500pxclient.main.MainActivity;

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
    @Bind(R.id.glogin)          Button gloginBtn;
    @Bind(R.id.loginProgressBar)ProgressBar loginProgressBar;
    @Bind(R.id.signupWeb)       WebView signupWeb;

    private String tmpEmail;
    private String tmpPassword;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_login);

        // set listeners
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
        fbloginBtn.setOnClickListener(this);
        gloginBtn.setOnClickListener(this);

        // view injection
        ButterKnife.bind(this);

        // Init
        tmpEmail = PrefUtil.getFromPrefs(this, PrefUtil.PREFS_LOGIN_USERNAME_KEY, "");
        tmpPassword = PrefUtil.getFromPrefs(this, PrefUtil.PREFS_LOGIN_PASSWORD_KEY, "");
        loginEmail.setText(tmpEmail);
        loginPassword.setText(tmpPassword);
        this.SetProgressBarVisibility(0);
        loginPresenter = new LoginPresenterImpl();

        // If did login before, retrieve data and do login
        if (!tmpEmail.isEmpty() && !tmpPassword.isEmpty()) {
            loginPresenter.login(tmpEmail, tmpPassword);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        perform net check
         */
        isConnected = NetUtil.CheckConnectivity(this);
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
                loginPresenter.login(loginEmail.getText().toString(), loginPassword.getText().toString());
                break;
            case R.id.signupBtn:
                // TODO: open web view

                break;
            case R.id.fblogin:
                // TODO: implement
                break;
            case R.id.glogin:
                // TODO: implement
                break;
        }
    }


    @Override
    public void SetProgressBarVisibility(int visibility) {
        loginProgressBar.setVisibility(visibility);
    }

    @Override
    public void SetWebViewVisibility(int visibility){
        // TODO: implement
    }

    @Override
    public void OnLoginResult(boolean result){
        // disable progressbar
        this.SetProgressBarVisibility(0);
        tmpEmail = loginEmail.getText().toString();
        tmpPassword = loginPassword.getText().toString();
        if (result) {
            // make a toast
            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
            // save user info to local storage
            PrefUtil.saveToPrefs(this, PrefUtil.PREFS_LOGIN_USERNAME_KEY, tmpEmail);
            PrefUtil.saveToPrefs(this, PrefUtil.PREFS_LOGIN_PASSWORD_KEY, tmpPassword);
            // clear text fields
            ClearText();
            // jump
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // make a toast
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnSignUpReturned(){
        // TODO: fill email and password field with data
    }



    void ClearText() {
        loginEmail.setText("");
        loginPassword.setText("");
    }

}
