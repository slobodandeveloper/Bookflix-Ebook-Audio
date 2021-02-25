package com.fixnowitdeveloper.bookflix.ObjectUtil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CursorObject {
    private Cursor cursor;
    private SQLiteDatabase database;


    public Cursor getCursor() {
        return cursor;
    }

    public CursorObject setCursor(Cursor cursor) {
        this.cursor = cursor;
        return this;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public CursorObject setDatabase(SQLiteDatabase database) {
        this.database = database;
        return this;
    }



}
