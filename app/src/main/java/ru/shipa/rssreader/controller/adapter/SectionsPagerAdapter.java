package ru.shipa.rssreader.controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.shipa.rssreader.App;
import ru.shipa.rssreader.R;
import ru.shipa.rssreader.ui.fragment.FavouriteNewsFragmentViewImpl;
import ru.shipa.rssreader.ui.fragment.LinesFragmentViewImpl;
import ru.shipa.rssreader.ui.fragment.NewsFragmentViewImpl;

/**
 * Created by Vlad on 15.03.2016.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LinesFragmentViewImpl.newInstance();
            case 1:
                return NewsFragmentViewImpl.newInstance();
            case 2:
                return FavouriteNewsFragmentViewImpl.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return App.getContext().getString(R.string.lines);
            case 1:
                return App.getContext().getString(R.string.news);
            case 2:
                return App.getContext().getString(R.string.favourite);
        }
        return null;
    }
}
