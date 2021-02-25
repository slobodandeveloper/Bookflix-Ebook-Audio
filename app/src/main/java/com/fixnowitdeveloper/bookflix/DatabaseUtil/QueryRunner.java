package com.fixnowitdeveloper.bookflix.DatabaseUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.ObjectUtil.CursorObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import java.util.ArrayList;

public class QueryRunner {
    private Context context;

    /**
     * <p>It contain methods for executing the SQL Database Queries & get Required Result</p>
     */
    public QueryRunner(Context context) {
        this.context = context;
        Utility.Logger(QueryRunner.class.getName(), "Setting : Working");
    }


    /**
     * <p>It execute Query and return data in the form of list by adding into arraylist</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return arraylist which contain all of required data from Database
     */
    public ArrayList<Object> getAll(String query, SQLiteOpenHelper sqLiteOpenHelper, DatabaseObject databaseObject) {

        CursorObject cursorObject = getCursor(query, sqLiteOpenHelper);
        Cursor cursor = cursorObject.getCursor();
        ArrayList<Object> objectArrayList = new ArrayList<>();
        Utility.Logger(QueryRunner.class.getName(), "Size of Cursor : " + cursor.getCount());

        if (cursor.moveToFirst()) {

            do {

                if (databaseObject.getTypeOperation() == Constant.TYPE.FAVOURITES) {


                    objectArrayList.add(new DataObject()
                            .setId(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.COLUMN_ID)))
                            .setBookUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.COLUMN_BOOK_URL)))
                            .setStreamName(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.COLUMN_BOOK_TITLE)))
                            .setTitle(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.COLUMN_BOOK_TITLE)))
                            .setOriginalUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.COLUMN_COVER_URL)))
                            .setCoverUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.COLUMN_COVER_URL)))
                            .setArtistName(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.COLUMN_ARTIST_NAME)))
                            .setFbUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.COLUMN_BOOK_ID)))
                            .setWebUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.COLUMN_USER_ID)))
                            .setTwitterUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.COLUMN_TWITTER_URL))));


                } else if (databaseObject.getTypeOperation() == Constant.TYPE.FILE_READING_STATUS) {


                    objectArrayList.add(new DataObject()
                            .setId(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_ID_COLUMN)))
                            .setTitle(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_TITLE_COLUMN)))
                            .setFileType(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_TYPE_COLUMN)))
                            .setBookUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_URL_COLUMN)))
                            .setCoverUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_COVER_COLUMN)))
                            .setBookPage(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_PAGES_COLUMN)))
                            .setCurrentPage(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_READ_PAGES_COLUMN)))
                            .setDataType(Constant.DATA_TYPE.HISTORY));


                } else if (databaseObject.getTypeOperation() == Constant.TYPE.DOWNLOAD) {


                    objectArrayList.add(new DataObject()
                            .setId(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.DOWNLOAD_COLUMN_ID)))
                            .setTitle(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.DOWNLOAD_COLUMN_TITLE)))
                            .setArtistName(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.DOWNLOAD_COLUMN_ARTIST_NAME)))
                            .setFileType(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.DOWNLOAD_COLUMN_FILE_TYPE)))
                            .setOriginalUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.DOWNLOAD_COLUMN_COVER_URL)))
                            .setCoverUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.DOWNLOAD_COLUMN_COVER_URL)))
                            .setBookUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.DOWNLOAD_COLUMN_MEDIA_URL))));


                } else if (databaseObject.getTypeOperation() == Constant.TYPE.HISTORY) {


                    objectArrayList.add(new DataObject()
                            .setId(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_ID)))
                            .setPlaylistId(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_PLAYLIST_ID)))
                            .setServerId(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_SERVER_ID)))
                            .setTitle(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_TITLE)))
                            .setStreamName(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_TITLE)))
                            .setArtistName(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_ARTIST_NAME)))
                            .setOriginalUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_COVER_URL)))
                            .setBookUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.PLAYLIST_MP3_COLUMN_MEDIA_URL))));


                } else if (databaseObject.getTypeOperation() == Constant.TYPE.MY_ADDED_BOOKS_STATUS) {


                    objectArrayList.add(new DataObject()
                            .setId(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_ID_COLUMN)))
                            .setTitle(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_TITLE_COLUMN)))
                            .setFileType(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_TYPE_COLUMN)))
                            .setBookUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_URL_COLUMN)))
                            .setCoverUrl(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_COVER_COLUMN)))
                            .setBookPage(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_PAGES_COLUMN)))
                            .setCurrentPage(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FILE_READ_PAGES_COLUMN)))
                            .setDataType(Constant.DATA_TYPE.MY_ADDED_BOOKS));


                }

            } while (cursor.moveToNext());

        }

        cursor.close();
        cursorObject.getDatabase().close();

        return objectArrayList;
    }


    /**
     * <p>Execute query and give true/false on Success or Failing</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return true if perform successfully
     */
    public CursorObject getStatus(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        Utility.Logger(QueryRunner.class.getName(), "Setting : " + query);
        CursorObject cursorObject = getCursor(query, sqLiteOpenHelper);
        Cursor cursor = cursorObject.getCursor();

        try {

            cursor.moveToFirst();
            cursor.close();

        } catch (SQLiteConstraintException e) {

            cursorObject.setCursor(null).setDatabase(null);
        } catch (UnsupportedOperationException uoe) {

            cursorObject.setCursor(null).setDatabase(null);
        }

        return cursorObject;
    }


    /**
     * <p>Execute query and give last inserted Row Id</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return long      last inserted id
     */
    public long getLastInsertID(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        Utility.Logger("Last Inserted", query);
        long lastInsertedId;
        CursorObject cursorObject = getCursor(query, sqLiteOpenHelper);
        Cursor cursor = cursorObject.getCursor();
        cursor.moveToFirst();
        lastInsertedId = cursor.getLong(0);
        Utility.Logger("Before", String.valueOf(lastInsertedId));
        cursor.close();
        cursorObject.getDatabase().close();
        Utility.Logger("After", String.valueOf(lastInsertedId));
        return lastInsertedId;
    }


    /**
     * <p>It run the SQL Query and return data in Cursor</p>
     *
     * @param query            SQL Query which we want to run
     * @param sqLiteOpenHelper Instance of SQLiteOpenHelper
     * @return cursor which contain data fetch from Database
     */
    private CursorObject getCursor(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        CursorObject cursorObject = new CursorObject();

        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursorObject.setCursor(cursor).setDatabase(db);


        return cursorObject;
    }


}
