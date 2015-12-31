package ru.shipa.rssreader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ShipVlad on 30.12.2015.
 */

public class GetNewsTask extends AsyncTask<String, Void, String> {
    Context ctx;
    DBHelper dbHelper;
    SQLiteDatabase db;
    FragmentLines fragmentLines;

    GetNewsTask(Context ctx, FragmentLines fragmentLines) {
        this.ctx = ctx;
        this.fragmentLines = fragmentLines;
        dbHelper = new DBHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    protected String doInBackground(String... params) {
        String[] columns = new String[]{"lineName", "lineUrl"};
        Cursor c = db.query("lines_list", columns, null, null, null, null, null);
        if (c.moveToFirst()) {

            do {
                try {
                    parseNewsXML(getNewsRequest(c.getString(c.getColumnIndex("lineUrl"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (c.moveToNext());
        }
        c.close();
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        fragmentLines.refreshLinesComplete();
        dbHelper.close();
    }

    private String getNewsRequest(String url) throws IOException {
        InputStream inputStream = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("charset", "utf-8");
            conn.connect();

            inputStream = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }

    private void parseNewsXML(String result) {
        Log.d("result", result);
    }
}
