package ru.shipa.rssreader;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    final int MENU_DELETE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lines, container, false);
        ButterKnife.bind(this, rootView);
        ctx = getActivity();
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
        ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            loadLines();
        }
        registerForContextMenu(lvLines);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.lvLines:
                menu.add(0, MENU_DELETE, 0, R.string.deleteLines);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DELETE:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                View view = info.targetView;
                TextView column = (TextView) ((RelativeLayout) view).getChildAt(1);
                db.delete("lines_list", "lineName = (?)", new String[]{column.getText().toString()});
                refreshLines();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void loadLines() {
        new GetNewsTask(ctx, this).execute();
    }

    public void refreshLines() {
        ArrayList<String> lineImg = new ArrayList<>();
        ArrayList<String> lineName = new ArrayList<>();
        ArrayList<String> lineDate = new ArrayList<>();

        String[] columns = new String[]{"lineName", "lineDate"};
        Cursor c = db.query("lines_list", columns, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
//                lineImg.add(c.getString(c.getColumnIndex("")));
                lineName.add(c.getString(c.getColumnIndex("lineName")));
                lineDate.add(c.getString(c.getColumnIndex("lineDate")));
            } while (c.moveToNext());
        }
        c.close();

        ArrayList<Map<String, Object>> data = new ArrayList<>(lineName.size());
        Map<String, Object> m;

        for (int i = 0; i < lineName.size(); i++) {

            m = new HashMap<>();
//            m.put("ivLine", lineImg.get(i));
            m.put("tvLine", lineName.get(i));
            m.put("tvDateLine", lineDate.get(i));
            data.add(m);
        }
//        String[] fromLine = {"ivLine", "tvLine", "tvDateLine"};
//        int[] toLine = {R.id.ivLine, R.id.tvLine, R.id.tvDateLine};
        String[] fromLine = {"tvLine", "tvDateLine"};
        int[] toLine = {R.id.tvLine, R.id.tvDateLine};
        SimpleAdapter sLineAdapter = new SimpleAdapter(ctx, data, R.layout.lines_item, fromLine, toLine) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tvLine = (TextView) view.findViewById(R.id.tvLine);
                TextView tvRefresh = (TextView) view.findViewById(R.id.tvRefresh);
                TextView tvDateLine = (TextView) view.findViewById(R.id.tvDateLine);

                if (tvDateLine.getText() != getString(R.string.never)) {
                    tvLine.setTextColor(getResources().getColor(R.color.secondary_text));
                    tvRefresh.setTextColor(getResources().getColor(R.color.secondary_text));
                    tvDateLine.setTextColor(getResources().getColor(R.color.secondary_text));
                }
                return view;
            }
        };
        lvLines.setEmptyView(tvEmpty);
        lvLines.setAdapter(sLineAdapter);
    }

    public void refreshLinesComplete() {
        refreshLines();
        swipeLayout.setRefreshing(false);
    }
}
