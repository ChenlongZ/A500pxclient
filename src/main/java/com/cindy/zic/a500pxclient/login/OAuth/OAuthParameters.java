package com.cindy.zic.a500pxclient.login.OAuth;

import android.net.Uri;

import com.cindy.zic.a500pxclient.commons.utils.HttpParamUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by zic on 3/12/16.
 */
public class OAuthParameters {

    private HashMap<String, String> params;

    public OAuthParameters(String consumerKey) {

        if (consumerKey == null || consumerKey.trim().length() == 0) {
            throw new IllegalArgumentException("Consumer must not be empty");
        }
        params = new HashMap<String, String>();
        params.put(OAuthConstants.TIMESTAMP, GetTimeStampInSec());
        params.put(OAuthConstants.NONCE, GetTimeStampInSec());
        params.put(OAuthConstants.CONSUMER_KEY, consumerKey);
        params.put(OAuthConstants.SIGN_METHOD, "HMAC-SHA1");
        params.put(OAuthConstants.VERSION, "1,0");
    }

    public String GetParamAsString() {
        StringBuffer sb = new StringBuffer();
        String[] keys = SortedKeys();

        for (String key : keys) {
            sb.append(HttpParamUtil.encode(key));
            sb.append("=");
            sb.append(HttpParamUtil.encode(params.get(key)));

            if (!key.equals(keys[keys.length - 1])){
                sb.append("&");
            }
        }

        return sb.toString();
    }

    public String GetAuthorizedHeaderValue() {
        StringBuffer sb = new StringBuffer();
        String[] sKeys = SortedKeys();

        sb.append("OAuth ");
        for (String key : sKeys) {
            if (key.startsWith(OAuthConstants.PARAM_PREFIX)) {
                sb.append(key);
                sb.append('=');
                sb.append('"');
                sb.append(Uri.encode(params.get(key)));
                sb.append("\", ");
            }
        }

        return sb.toString().substring(0, sb.length() - 2);

    }

    private String[] SortedKeys() {
        final Set<String> keys = params.keySet();
        Object[] oKeys = keys.toArray();
        Arrays.sort(oKeys);

        String[] sKeys = new String[oKeys.length];
        int ix = 0;
        for (Object o : oKeys) {
            sKeys[ix++] = o.toString();
        }

        return sKeys;
    }

    public void put(String k, String v) {
        params.put(k, v);
    }

    private String GetTimeStampInSec() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
