package ru.shipa.rssreader.presentation.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import ru.shipa.rssreader.App;
import ru.shipa.rssreader.R;
import ru.shipa.rssreader.model.db.DBHelper;
import ru.shipa.rssreader.presentation.BasePresenterImpl;
import ru.shipa.rssreader.presentation.activity.interfaces.MainActivityPresenter;
import ru.shipa.rssreader.ui.activity.interfaces.MainActivityView;

/**
 * Created by Vlad on 14.03.2016.
 */
public class MainActivityPresenterImpl extends BasePresenterImpl implements MainActivityPresenter {
    MainActivityView mView;

    public MainActivityPresenterImpl(@NonNull final MainActivityView mView) {
        this.mView = mView;
    }

    public void addLine(@NonNull String name, @NonNull final String url) {
        if (url.isEmpty()) {
            mView.printMessage(R.string.linesEmptyUrl);
            return;
        }
        if (name.isEmpty()) {
            name = url;
        }
        DBHelper dbHelper = DBHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("lines_list", new String[]{"lineUrl"}, "lineUrl = (?)"
                , new String[]{url}, null, null, null);
        if (cursor.getCount() > 0) {
            mView.printMessage(R.string.linesAddedError);
            cursor.close();
            return;
        }
        cursor.close();
        ContentValues cv = new ContentValues();
        cv.put("lineName", name);
        cv.put("lineUrl", url);
        cv.put("lineDate", App.getContext().getString(R.string.never));
        db.insert("lines_list", null, cv);
        mView.printMessage(R.string.linesAdded);
        dbHelper.close();
        //TODO Обновить список
    }
}
