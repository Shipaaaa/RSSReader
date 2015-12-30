package ru.shipa.rssreader;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ShipVlad on 30.12.2015.
 */

public class FragmentLines extends Fragment {
    Context ctx;
    DBHelper dbHelper;
    SQLiteDatabase db;

    SwipeRefreshLayout swipeLayout;

    public FragmentLines (Context ctx) {
        this.ctx = ctx;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lines, container, false);

        dbHelper = new DBHelper(ctx);
        db = dbHelper.getWritableDatabase();
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_view);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLines();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green, R.color.red);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    public void loadLines() {
        new GetNewsTask(ctx, this).execute();
    }
    public void refreshLines() {

    }
    public void refreshLinesComplete() {
        refreshLines();
        swipeLayout.setRefreshing(false);
    }

}
