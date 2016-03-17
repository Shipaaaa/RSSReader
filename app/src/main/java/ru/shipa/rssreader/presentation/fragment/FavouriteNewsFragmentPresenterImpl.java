package ru.shipa.rssreader.presentation.fragment;

import android.support.annotation.NonNull;

import ru.shipa.rssreader.presentation.BasePresenterImpl;
import ru.shipa.rssreader.presentation.fragment.interfaces.FavouriteNewsFragmentPresenter;
import ru.shipa.rssreader.ui.fragment.interfaces.FavouriteNewsFragmentView;

/**
 * Created by Vlad on 14.03.2016.
 */
public class FavouriteNewsFragmentPresenterImpl extends BasePresenterImpl implements FavouriteNewsFragmentPresenter {
    FavouriteNewsFragmentView mView;

    public FavouriteNewsFragmentPresenterImpl(@NonNull final FavouriteNewsFragmentView mView) {
        this.mView = mView;
    }
}
