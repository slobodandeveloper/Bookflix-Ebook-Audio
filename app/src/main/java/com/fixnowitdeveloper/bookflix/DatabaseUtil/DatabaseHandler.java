package com.fixnowitdeveloper.bookflix.DatabaseUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.MyApplication;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;


/**
 * Created by hp on 2/27/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = Utility.getStringFromRes(MyApplication.getInstance(), R.string.app_name);

    String CREATE_FAVOURITE_APP_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.TABLE_NAME + "(" +
            Constant.DatabaseColumn.COLUMN_ID + " INTEGER PRIMARY KEY," +
            Constant.DatabaseColumn.COLUMN_BOOK_TITLE + " TEXT," +
            Constant.DatabaseColumn.COLUMN_COVER_URL + " TEXT ," +
            Constant.DatabaseColumn.COLUMN_ARTIST_NAME + " TEXT," +
            Constant.DatabaseColumn.COLUMN_BOOK_URL + " TEXT," +
            Constant.DatabaseColumn.COLUMN_USER_ID + " TEXT," +
            Constant.DatabaseColumn.COLUMN_BOOK_ID + " TEXT ," +
            Constant.DatabaseColumn.COLUMN_TWITTER_URL + " TEXT " + ")";

    String CREATE_FILES_APP_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.FILE_TABLE_NAME + "(" +
            Constant.DatabaseColumn.FILE_ID_COLUMN + " INTEGER PRIMARY KEY," +
            Constant.DatabaseColumn.FILE_TITLE_COLUMN + " TEXT," +
            Constant.DatabaseColumn.FILE_TYPE_COLUMN + " TEXT, " +
            Constant.DatabaseColumn.FILE_URL_COLUMN + " TEXT, " +
            Constant.DatabaseColumn.FILE_COVER_COLUMN + " TEXT, " +
            Constant.DatabaseColumn.FILE_PAGES_COLUMN + " TEXT, " +
            Constant.DatabaseColumn.FILE_READ_PAGES_COLUMN + " TEXT " +")";

    String CREATE_MY_ADDED_BOOKS_APP_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.MY_ADDED_BOOK_TABLE_NAME + "(" +
            Constant.DatabaseColumn.MY_ADDED_BOOK_ID_COLUMN + " INTEGER PRIMARY KEY," +
            Constant.DatabaseColumn.MY_ADDED_BOOK_TITLE_COLUMN + " TEXT," +
            Constant.DatabaseColumn.MY_ADDED_BOOK_TYPE_COLUMN + " TEXT, " +
            Constant.DatabaseColumn.MY_ADDED_BOOK_URL_COLUMN + " TEXT, " +
            Constant.DatabaseColumn.MY_ADDED_BOOK_COVER_COLUMN + " TEXT, " +
            Constant.DatabaseColumn.MY_ADDED_BOOK_PAGES_COLUMN + " TEXT, " +
            Constant.DatabaseColumn.MY_ADDED_BOOK_READ_PAGES_COLUMN + " TEXT " +")";


    String CREATE_DOWNLOAD_APP_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.DOWNLOAD_TABLE_NAME + "(" +
            Constant.DatabaseColumn.DOWNLOAD_COLUMN_ID + " INTEGER PRIMARY KEY," +
            Constant.DatabaseColumn.DOWNLOAD_COLUMN_TITLE + " TEXT ," +
            Constant.DatabaseColumn.DOWNLOAD_COLUMN_ARTIST_NAME + " TEXT," +
            Constant.DatabaseColumn.DOWNLOAD_COLUMN_FILE_TYPE + " TEXT," +
            Constant.DatabaseColumn.DOWNLOAD_COLUMN_COVER_URL + " TEXT," +
            Constant.DatabaseColumn.DOWNLOAD_COLUMN_MEDIA_URL + " TEXT " + ")";

    String CREATE_PLAYLIST_MP3_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.PLAYLIST_MP3_TABLE_NAME + "(" +
            Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_ID + " INTEGER PRIMARY KEY," + Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_PLAYLIST_ID + " TEXT," +
            Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_SERVER_ID + " TEXT," + Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_TITLE + " TEXT ," +
            Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_ARTIST_NAME + " TEXT," + Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_COVER_URL + " TEXT," +
            Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_MEDIA_URL + " TEXT " + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_FAVOURITE_APP_TABLE);
        db.execSQL(CREATE_FILES_APP_TABLE);
        db.execSQL(CREATE_MY_ADDED_BOOKS_APP_TABLE);
        db.execSQL(CREATE_DOWNLOAD_APP_TABLE);
        db.execSQL(CREATE_PLAYLIST_MP3_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        ///db.execSQL("ALTER TABLE " + Constant.REMINDER_DATA.TABLE_NAME + " CHANGE COLUMN " + Constant.REMINDER_DATA.FAVOURITE + " TEXT '" + Constant.REMINDER_DATA.NOT_FAVOURITE + "'");

        db.execSQL("DROP TABLE IF EXISTS " + Constant.DatabaseColumn.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constant.DatabaseColumn.FILE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constant.DatabaseColumn.MY_ADDED_BOOK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constant.DatabaseColumn.DOWNLOAD_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

}
