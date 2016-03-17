package ru.shipa.rssreader.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.shipa.rssreader.App;

/**
 * Created by ShipVlad on 30.12.2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper sInstance;

    public static DBHelper getInstance() {
        DBHelper localInstance = sInstance;
        if (localInstance == null) {
            synchronized (DBHelper.class) {
                localInstance = sInstance;
                if (localInstance == null) {
                    sInstance = localInstance = new DBHelper(App.getContext());
                }
            }
        }
        return localInstance;
    }


    public DBHelper(Context context) {
        super(context, DBConst.DB_Name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table lines_list ("
                + "id integer primary key autoincrement,"
                + "lineName text,"
                + "lineUrl text,"
                + "lineDate text" + ");");

        db.execSQL("create table news ("
                + "id integer primary key autoincrement,"
                + "newsTitle text,"
                + "newsLink text,"
                + "newsDescription text,"
                + "newsPubDate text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
