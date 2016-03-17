package ru.shipa.rssreader.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.shipa.rssreader.R;
import ru.shipa.rssreader.controller.adapter.LineListAdapter;
import ru.shipa.rssreader.presentation.BasePresenter;
import ru.shipa.rssreader.presentation.fragment.FavouriteNewsFragmentPresenterImpl;
import ru.shipa.rssreader.presentation.fragment.interfaces.FavouriteNewsFragmentPresenter;
import ru.shipa.rssreader.ui.BaseFragment;
import ru.shipa.rssreader.ui.fragment.interfaces.FavouriteNewsFragmentView;

/**
 * Created by ShipVlad on 30.12.2015.
 */
public class FavouriteNewsFragmentViewImpl extends BaseFragment implements FavouriteNewsFragmentView {
    FavouriteNewsFragmentPresenter mPresenter;

    @Bind(R.id.rvFavouriteNews)
    RecyclerView rvFavouriteNews;
    @Bind(R.id.tvEmpty)
    TextView tvEmpty;
    @Bind(R.id.swipe_view)
    SwipeRefreshLayout swipeLayout;
    View mRootView;

    @NonNull
    public static FavouriteNewsFragmentViewImpl newInstance() {
        return new FavouriteNewsFragmentViewImpl();
    }

    @SuppressLint("ValidFragment")
    FavouriteNewsFragmentViewImpl() {
        mPresenter = new FavouriteNewsFragmentPresenterImpl(this);
        setPresenter((BasePresenter) mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_favourite_news, container, false);
        ButterKnife.bind(this, rootView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFavouriteNews.setLayoutManager(linearLayoutManager);
        rvFavouriteNews.setAdapter(new LineListAdapter(new ArrayList<String[]>()));
        rvFavouriteNews.setNestedScrollingEnabled(false);

//        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mPresenter.getLines();
//            }
//        });
        swipeLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green, R.color.red);

//        swipeLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeLayout.setRefreshing(true);
//            }
//        });
//        mPresenter.getLines();
        return rootView;
    }

    public void printMessage(@NonNull String message) {
        showSnackBar(message, mRootView);
        swipeLayout.setRefreshing(false);
    }

    public void refreshData(@NotNull List<String[]> linesList) {
        if (linesList.isEmpty()) {
            rvFavouriteNews.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            rvFavouriteNews.setAdapter(new LineListAdapter(linesList));
            rvFavouriteNews.setItemAnimator(new DefaultItemAnimator());
            rvFavouriteNews.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            registerForContextMenu(rvFavouriteNews);
        }
        swipeLayout.setRefreshing(false);
    }
}
