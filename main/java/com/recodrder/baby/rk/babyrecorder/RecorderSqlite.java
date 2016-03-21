package com.recodrder.baby.rk.babyrecorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rk on 2016/3/7.
 */
public class RecorderSqlite extends SQLiteOpenHelper {

    static final String sql = "create table baby_recorder(_id integer primary key autoincrement,event varchar(30),tstart varchar(30),tend varchar(30))";
    public RecorderSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
