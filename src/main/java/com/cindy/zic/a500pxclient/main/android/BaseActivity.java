package com.cindy.zic.a500pxclient.main.android;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import com.cindy.zic.a500pxclient.R;

import java.io.File;

public class BaseActivity extends Activity {

    protected File cacheDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PackageManager m = getPackageManager();
        String name = getPackageName();
        try {
            PackageInfo p = m.getPackageInfo(name, 0);
            cacheDir = new File(p.applicationInfo.dataDir);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("BASE", e.getMessage());
        }
    }

}
