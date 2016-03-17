package ru.shipa.rssreader.presentation.fragment;

import android.support.annotation.NonNull;

import ru.shipa.rssreader.presentation.BasePresenterImpl;
import ru.shipa.rssreader.presentation.fragment.interfaces.NewsFragmentPresenter;
import ru.shipa.rssreader.ui.fragment.interfaces.NewsFragmentView;

/**
 * Created by Vlad on 14.03.2016.
 */
public class NewsFragmentPresenterImpl extends BasePresenterImpl implements NewsFragmentPresenter {
    NewsFragmentView mView;

    public NewsFragmentPresenterImpl(@NonNull final NewsFragmentView mView) {
        this.mView = mView;
    }

}