package ru.shipa.rssreader.controller.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ru.shipa.rssreader.App;

/**
 * Created by Vlad on 14.03.2016.
 */
public class NWConst {
    public final static String BASE_URL = "";
    public final static String FULL_URL = "https://" + BASE_URL;

    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) App.getContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
