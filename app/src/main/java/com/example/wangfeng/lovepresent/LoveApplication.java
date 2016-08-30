package com.example.wangfeng.lovepresent;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by wangfeng on 15/8/14.
 */
public class LoveApplication extends Application {
    private final int IMAGELOADER_MEMORY_CACHE_SIZE = 2 * 1024 * 1024;
    private final int IMAGELOADER_MEMORY_CACHE_SIZE_PRECENTAGE = 5;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        initImageLoader();
    }
    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(IMAGELOADER_MEMORY_CACHE_SIZE)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .memoryCacheSizePercentage(IMAGELOADER_MEMORY_CACHE_SIZE_PRECENTAGE);
//        if (DEBUG) {
//            builder.writeDebugLogs();
//        }
        ImageLoaderConfiguration config = builder.build();
        ImageLoader.getInstance().init(config);
    }
}
