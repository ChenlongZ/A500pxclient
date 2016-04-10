package com.cindy.zic.a500pxclient.main.android;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

import com.cindy.zic.a500pxclient.R;
import com.cindy.zic.a500pxclient.commons.net.NetUtil;
import com.cindy.zic.a500pxclient.main.commons.util.image.splashDiskCache;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.IoUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends BaseActivity {

    File cacheDir;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    ImageLoaderConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide action bar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();

        // init UIL
        cacheDir = super.cacheDir;

        displayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .build();

        config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new splashDiskCache(cacheDir))
                .defaultDisplayImageOptions(displayImageOptions)
                .build();

        imageLoader.getInstance().init(config);

        // check network connectivity

        // if connected
        if(NetUtil.CheckConnectivity(this)){
        /*
           fetch popular 1st meta, using PxApi
            do not cache it
           fetch popular 1st image using UImageLoader
            crop to fit resolution
            cache to replace old 'splashcreen.png'
            prepare it for imageview
            if failed, use default picture
         */
        // else
        } else {
        /*
           fetch cached 'splashcreen.png' from local directory
           prepare it for imageview
           if not found, use default picture

         */

        }

        // display
        setContentView(R.layout.activity_splash);
    }

}
