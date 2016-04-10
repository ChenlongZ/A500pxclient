package com.cindy.zic.a500pxclient.main.commons.util.image;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.utils.IoUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zic on 4/9/16.
 */
public class splashDiskCache implements DiskCache {

    private static final int BUFFER_SIZE = 1024 * 1024;

    private final File cacheDir;
    private final FileNameGenerator fileNameGenerator;

    public splashDiskCache(File cacheDir) {
        this.cacheDir = cacheDir;
        this.fileNameGenerator = this.new splashFileNameGenerator();
    }

    public splashDiskCache(File cacheDir, FileNameGenerator fileNameGenerator) {
        this.cacheDir = cacheDir;
        this.fileNameGenerator = fileNameGenerator;
    }

    @Override
    public File getDirectory() {
        return cacheDir;
    }

    @Override
    public File get(String imageUri) {
        return getFile(imageUri);
    }

    @Override
    public boolean save(String s, InputStream inputStream, IoUtils.CopyListener copyListener) throws IOException {
        return false;
    }

    @Override
    public boolean save(String imageUri, Bitmap bitmap) throws IOException {
        File imageFile = this.getFile(imageUri);
        imageFile = imageFile.getAbsoluteFile();
        OutputStream os = new BufferedOutputStream(new FileOutputStream(imageFile), BUFFER_SIZE);
        boolean successful = false;
        try {
            successful = bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        } finally {
            IoUtils.closeSilently(os);
            if (!successful) {
                imageFile.delete();
            }
        }
        bitmap.recycle();   // release bitmap memory
        return successful;
    }

    @Override
    public boolean remove(String imageUri) {
        return this.getFile(imageUri).delete();
    }

    @Override
    public void close() {
    }

    @Override
    public void clear() {
        File[] files;
        if (cacheDir.isDirectory()) {
            files = cacheDir.listFiles();
            for (File f : files) {
                f.delete();
            }
        }
    }

    private File getFile(String imageUri) {
        String fileName = fileNameGenerator.generate(imageUri);
        if (!this.cacheDir.exists() && !this.cacheDir.mkdirs());
        return new File(cacheDir, fileName);
    }

    protected class splashFileNameGenerator implements FileNameGenerator {

        @Override
        public String generate(String s) {
            return "splashscreen";
        }
    }

}
