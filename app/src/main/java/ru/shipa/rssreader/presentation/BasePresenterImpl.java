package ru.shipa.rssreader.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.redmadrobot.chronos.ChronosConnector;

import ru.shipa.rssreader.ui.BaseActivity;

/**
 * Created by Vlad on 14.03.2016.
 */
public abstract class BasePresenterImpl implements BasePresenter {
    private ChronosConnector mConnector = new ChronosConnector();;

    @NonNull
    public ChronosConnector getConnector() {
        return mConnector;
    }

    @Override
    public void onCreate(@NonNull final BaseActivity baseActivity, @NonNull final Bundle savedInstanceState) {
        mConnector.onCreate(this, savedInstanceState);
    }

    @Override
    public void onResume() {
        mConnector.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        mConnector.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        mConnector.onPause();
    }
}