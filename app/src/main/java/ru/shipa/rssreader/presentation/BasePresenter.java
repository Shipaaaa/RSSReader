package ru.shipa.rssreader.presentation;

import android.os.Bundle;

import ru.shipa.rssreader.ui.BaseActivity;

/**
 * Created by Vlad on 14.03.2016.
 */
public interface BasePresenter {
    void onCreate(BaseActivity baseActivity, Bundle savedInstanceState);
    void onResume();
    void onSaveInstanceState(Bundle outState);
    void onPause();
}
