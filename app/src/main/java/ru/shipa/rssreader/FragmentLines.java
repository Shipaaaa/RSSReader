package ru.shipa.rssreader;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ShipVlad on 30.12.2015.
 */

public class FragmentLines extends Fragment {
    Context ctx;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Bind(R.id.lvLines)
    ListView lvLines;
    @Bind(android.R.id.empty)
    TextView tvEmpty;
    SwipeRefreshLayout swipeLayout;

    public FragmentLines(Context ctx) {
        this.ctx = ctx;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lines, container, false);
        ButterKnife.bind(this, rootView);

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
        refreshLines();
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
        ArrayList<String> lineImg = new ArrayList<>();
        ArrayList<String> lineName = new ArrayList<>();
        ArrayList<String> lineDate = new ArrayList<>();

        String[] columns = new String[]{"lineName"};
        Cursor c = db.query("lines_list", columns, null, null, null, null, null);
        if (c.moveToFirst()) {

            do {
//                lineImg.add(c.getString(c.getColumnIndex("orderName")));
                lineName.add(c.getString(c.getColumnIndex("lineName")));
//                lineDate.add(c.getString(c.getColumnIndex("serviceSection")));
            } while (c.moveToNext());
        }
        c.close();

        ArrayList<Map<String, Object>> data = new ArrayList<>(lineName.size());
        Map<String, Object> m;

        for (int i = 0; i < lineName.size(); i++) {

            m = new HashMap<>();
//            m.put("ivLine", lineImg.get(i));
            m.put("tvLine", lineName.get(i));
//            m.put("tvDateLine", lineDate.get(i));
            data.add(m);
        }
//        String[] fromLine = {"ivLine", "tvLine", "tvDateLine"};
//        int[] toLine = {R.id.ivLine, R.id.tvLine, R.id.tvDateLine};
        String[] fromLine = {"tvLine"};
        int[] toLine = {R.id.tvLine};
        SimpleAdapter sLineAdapter = new SimpleAdapter(ctx, data, R.layout.lines_item, fromLine, toLine);
        lvLines.setEmptyView(tvEmpty);
        lvLines.setAdapter(sLineAdapter);
    }

    public void refreshLinesComplete() {
        refreshLines();
        swipeLayout.setRefreshing(false);
    }

}
