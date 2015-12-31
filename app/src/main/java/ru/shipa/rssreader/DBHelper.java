package ru.shipa.rssreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ShipVlad on 30.12.2015.
 */
public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context) {
        super(context, "RSSReader", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table lines_list ("
                + "id integer primary key autoincrement,"
                + "lineName text,"
                + "lineUrl text" + ");");

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
