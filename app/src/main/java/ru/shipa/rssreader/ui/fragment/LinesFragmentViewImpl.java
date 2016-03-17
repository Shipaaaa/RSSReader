package ru.shipa.rssreader.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.shipa.rssreader.R;
import ru.shipa.rssreader.controller.adapter.LineListAdapter;
import ru.shipa.rssreader.presentation.BasePresenter;
import ru.shipa.rssreader.presentation.fragment.LinesFragmentPresenterImpl;
import ru.shipa.rssreader.presentation.fragment.interfaces.LinesFragmentPresenter;
import ru.shipa.rssreader.ui.BaseFragment;
import ru.shipa.rssreader.ui.fragment.interfaces.LinesFragmentView;
import ru.shipa.rssreader.utils.DividerItemDecoration;

/**
 * Created by ShipVlad on 30.12.2015.
 */
public class LinesFragmentViewImpl extends BaseFragment implements LinesFragmentView {
    LinesFragmentPresenter mPresenter;

    @Bind(R.id.rvLines)
    RecyclerView rvLines;
    @Bind(R.id.tvEmpty)
    TextView tvEmpty;
    @Bind(R.id.swipe_view)
    SwipeRefreshLayout swipeLayout;
    View mRootView;
    final int MENU_DELETE = 1;

    @NonNull
    public static LinesFragmentViewImpl newInstance() {
        return new LinesFragmentViewImpl();
    }

    @SuppressLint("ValidFragment")
    LinesFragmentViewImpl() {
        mPresenter = new LinesFragmentPresenterImpl(this);
        setPresenter((BasePresenter) mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_lines, container, false);
        ButterKnife.bind(this, mRootView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLines.setLayoutManager(linearLayoutManager);
        rvLines.setAdapter(new LineListAdapter(new ArrayList<String[]>()));
        rvLines.setNestedScrollingEnabled(false);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getLines();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green, R.color.red);

        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(true);
            }
        });
        mPresenter.getLines();
        registerForContextMenu(rvLines);
        return mRootView;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.rvLines:
                menu.add(0, MENU_DELETE, 0, R.string.deleteLines);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DELETE:
                deleteLine(item);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteLine(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final View view = info.targetView;
        final TextView column = (TextView) ((RelativeLayout) view).getChildAt(1);
        mPresenter.deleteLine(column.getText().toString());
        mPresenter.getLines();
    }


    public void printMessage(@NonNull String message) {
        showSnackBar(message, mRootView);
        swipeLayout.setRefreshing(false);
    }

    public void refreshData(@NotNull List<String[]> linesList) {
        if (linesList.isEmpty()) {
            rvLines.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                    new ItemTouchHelper.SimpleCallback(0,
                            ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView,
                                      RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    final TextView column = (TextView) ((RelativeLayout) viewHolder.itemView).getChildAt(1);
                    mPresenter.deleteLine(column.getText().toString());
                    mPresenter.getLines();
                }
            };

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            rvLines.setAdapter(new LineListAdapter(linesList));
            rvLines.setItemAnimator(new DefaultItemAnimator());
            rvLines.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
            rvLines.addItemDecoration(itemDecoration);
            itemTouchHelper.attachToRecyclerView(rvLines);
        }
        swipeLayout.setRefreshing(false);
    }
}
