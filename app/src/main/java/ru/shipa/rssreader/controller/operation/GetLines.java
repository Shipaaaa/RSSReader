package ru.shipa.rssreader.controller.operation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.redmadrobot.chronos.ChronosOperation;
import com.redmadrobot.chronos.ChronosOperationResult;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.shipa.rssreader.controller.network.NWConst;
import ru.shipa.rssreader.model.db.DBHelper;

/**
 * Created by Vlad on 14.03.2016.
 */
public class GetLines extends ChronosOperation<List<String[]>> {

    @Nullable
    @Override
    public List<String[]> run() {
        if (NWConst.isNetworkConnected()) {
            refreshLines();
        }
        return getLinesListFromDB();
    }

    @NonNull
    @Override
    public Class<? extends ChronosOperationResult<List<String[]>>> getResultClass() {
        return Result.class;
    }

    public final static class Result extends ChronosOperationResult<List<String[]>> {
    }


    public void refreshLines() {
//        new GetNewsTask().execute();


       /* API api = ServiceGenerator.createService(API.class,
                NWConst.ACCESS_TOKEN_TITLE + PreferencesStorage.getInstance().getToken());
        Call<ServerResponse> call = api.getNotifications();
        try {
            Response<ServerResponse> response = call.execute();
            if (response.isSuccess()) {
                Object data = response.body().getData();
                GsonSerialization serialization = new GsonSerialization();
                NotificationListResponse result =
                        serialization.deserializeFromNW(data, NotificationListResponse.class);
                DBHelper db = DBHelper.getInstance();
                db.deleteTable(DBConst.TABLE_NAME_NOTIFICATIONS);
                List<NWNotificationList> notificationList = result.getNotificationList();
                for (NWNotificationList notification : notificationList) {
                    db.writeData(new NoSqlEntity(notification.getId(), notification),
                            DBConst.TABLE_NAME_NOTIFICATIONS);
                }
            } else {
                throw new RuntimeException(App.getContext().getString(R.string.toast_auth_error));
            }
        } catch (IOException e) {
            throw new RuntimeException(App.getContext().getString(R.string.toast_net_server_error));
        }*/
    }

    public List<String[]> getLinesListFromDB() {
        final DBHelper dbHelper = DBHelper.getInstance();
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        final List<String[]> data = new ArrayList<>();
        final String[] columns = new String[]{"lineName", "lineDate"};
        final Cursor c = db.query("lines_list", columns, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                data.add(new String[]{c.getString(c.getColumnIndex("lineName")),
                        c.getString(c.getColumnIndex("lineDate"))});
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();
        return data;
    }
}