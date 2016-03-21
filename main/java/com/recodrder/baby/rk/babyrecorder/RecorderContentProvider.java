package com.recodrder.baby.rk.babyrecorder;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Created by rk on 2016/3/7.
 */
public class RecorderContentProvider extends ContentProvider {

    private RecorderSqlite mRecorderSqlite;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(
                UriMatcher.NO_MATCH);
    }

    @Override
    public boolean onCreate() {
        mRecorderSqlite = new RecorderSqlite(getContext(), "babyrecorder.db", null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor c = mRecorderSqlite.getWritableDatabase().query("baby_recorder",strings,null,null,null,null,null);
        Log.i("TAG","c = " + c);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        String event_name = contentValues.getAsString("event").toString();
        String start_time = contentValues.getAsString("tstart").toString();

        mRecorderSqlite.getWritableDatabase().insert("baby_recorder", "_id", contentValues);

        return uri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        mRecorderSqlite.getWritableDatabase().delete("baby_recorder",s,strings);
        return 1;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        mRecorderSqlite.getWritableDatabase().update("baby_recorder",contentValues,s,null);
        
        return 1;
    }
}
