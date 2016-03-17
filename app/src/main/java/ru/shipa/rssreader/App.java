package ru.shipa.rssreader;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by Vlad on 14.03.2016.
 */
public final class App extends Application {
    private static App sInstance;

    public static App getContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static boolean isTablet() {
        return (sInstance.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}