package com.fixnowitdeveloper.bookflix.DatabaseUtil;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.SqlQueries;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

public class DownloadQueries implements SqlQueries {
    private final String TAG = DownloadQueries.class.getName();

    public DownloadQueries() {
        Utility.Logger(TAG, "Setting : Working");
    }

    @Override
    public String retrieving() {
        return "SELECT * FROM " + Constant.DatabaseColumn.DOWNLOAD_TABLE_NAME;
    }

    @Override
    public String update() {
        return "UPDATE " + Constant.DatabaseColumn.DOWNLOAD_TABLE_NAME + " SET "
                + Constant.DatabaseColumn.DOWNLOAD_COLUMN_TITLE + "=" + "%s"
                + " WHERE " + Constant.DatabaseColumn.DOWNLOAD_COLUMN_ID + "=" + "%s";

    }

    @Override
    public String insert() {
        return "INSERT INTO " + Constant.DatabaseColumn.DOWNLOAD_TABLE_NAME
                + "(" + Constant.DatabaseColumn.DOWNLOAD_COLUMN_TITLE
                + "," + Constant.DatabaseColumn.DOWNLOAD_COLUMN_ARTIST_NAME
                + "," + Constant.DatabaseColumn.DOWNLOAD_COLUMN_FILE_TYPE
                + "," + Constant.DatabaseColumn.DOWNLOAD_COLUMN_COVER_URL
                + "," + Constant.DatabaseColumn.DOWNLOAD_COLUMN_MEDIA_URL
                + ") VALUES (%s,%s,%s,%s,%s)";
    }

    @Override
    public String delete() {
        return "DELETE FROM " + Constant.DatabaseColumn.DOWNLOAD_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.DOWNLOAD_COLUMN_ID + "=" + "%s";
    }

    @Override
    public String retrieveSpecificType() {
        return "SELECT * FROM " + Constant.DatabaseColumn.DOWNLOAD_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.DOWNLOAD_COLUMN_FILE_TYPE + "=" + "%s";
    }


    @Override
    public String retrieveSpecificTags() {
        return "SELECT * FROM " + Constant.DatabaseColumn.DOWNLOAD_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.DOWNLOAD_COLUMN_ID + " =%s";

    }

    @Override
    public String deleteSpecificDownload() {
        return null;
    }
}
