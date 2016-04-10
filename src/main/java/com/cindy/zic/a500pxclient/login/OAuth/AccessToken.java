package com.cindy.zic.a500pxclient.login.OAuth;

import android.os.Parcel;
import android.os.Parcelable;

import com.cindy.zic.a500pxclient.commons.utils.HttpParamUtil;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * Created by zic on 3/12/16.
 */
public class AccessToken implements Parcelable {

    private String token;
    private String tokenSecret;

    public AccessToken() {};

    public AccessToken(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public AccessToken(Parcel in) {
        readFromParcel(in);
    }

    AccessToken(HttpResponse response){
        try {
            final String responseString = EntityUtils.toString(response
                    .getEntity());

            this.tokenSecret = HttpParamUtil.getUrlParamValue(
                    responseString, "oauth_token_secret");
            this.token = HttpParamUtil.getUrlParamValue(responseString,
                    "oauth_token");

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getToken() {
        return this.token;
    }

    public String getTokenSecret() {
        return this.tokenSecret;
    }

    public void setToken(String token) {this.token = token; }

    public void setSecret(String secret) {this.tokenSecret = secret; }

    public static final Parcelable.Creator<AccessToken> CREATOR = new Parcelable.Creator<AccessToken>(){
        public AccessToken createFromParcel(Parcel in) {
            return new AccessToken(in);
        }

        public AccessToken[] newArray(int size) {
            return new AccessToken[size];
        }
    };

    public void readFromParcel(Parcel in) {
        this.token = in.readString();
        this.tokenSecret = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(this.token);
        parcel.writeString(this.tokenSecret);
    }
}
