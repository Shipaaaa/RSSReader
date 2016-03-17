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
import ru.shipa.rssreader.presentation.fragment.NewsFragmentPresenterImpl;
import ru.shipa.rssreader.presentation.fragment.interfaces.NewsFragmentPresenter;
import ru.shipa.rssreader.ui.BaseFragment;
import ru.shipa.rssreader.ui.fragment.interfaces.NewsFragmentView;

/**
 * Created by ShipVlad on 30.12.2015.
 */
public class NewsFragmentViewImpl extends BaseFragment implements NewsFragmentView {
    NewsFragmentPresenter mPresenter;

    @Bind(R.id.rvNews)
    RecyclerView rvNews;
    @Bind(R.id.tvEmpty)
    TextView tvEmpty;
    @Bind(R.id.swipe_view)
    SwipeRefreshLayout swipeLayout;
    View mRootView;

    @NonNull
    public static NewsFragmentViewImpl newInstance() {
        return new NewsFragmentViewImpl();
    }

    @SuppressLint("ValidFragment")
    NewsFragmentViewImpl() {
        mPresenter = new NewsFragmentPresenterImpl(this);
        setPresenter((BasePresenter) mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNews.setLayoutManager(linearLayoutManager);
        rvNews.setAdapter(new LineListAdapter(new ArrayList<String[]>()));
        rvNews.setNestedScrollingEnabled(false);

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
            rvNews.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            rvNews.setAdapter(new LineListAdapter(linesList));
            rvNews.setItemAnimator(new DefaultItemAnimator());
            rvNews.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            registerForContextMenu(rvNews);
        }
        swipeLayout.setRefreshing(false);
    }
}
