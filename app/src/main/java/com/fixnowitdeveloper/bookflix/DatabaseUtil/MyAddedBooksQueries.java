package com.fixnowitdeveloper.bookflix.DatabaseUtil;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.SqlQueries;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

public class MyAddedBooksQueries implements SqlQueries {


    public MyAddedBooksQueries() {
        Utility.Logger(MyAddedBooksQueries.class.getName(), "Setting : Working");
    }

    @Override
    public String retrieving() {
        return "SELECT * FROM " + Constant.DatabaseColumn.MY_ADDED_BOOK_TABLE_NAME + " order by id desc";
    }

    @Override
    public String update() {
        return "UPDATE " + Constant.DatabaseColumn.MY_ADDED_BOOK_TABLE_NAME + " SET "
                + Constant.DatabaseColumn.MY_ADDED_BOOK_READ_PAGES_COLUMN + "=" + "%s"
                + " WHERE " + Constant.DatabaseColumn.MY_ADDED_BOOK_ID_COLUMN + "=" + "%s";

    }

    @Override
    public String insert() {
        return "INSERT INTO " + Constant.DatabaseColumn.MY_ADDED_BOOK_TABLE_NAME
                + "(" + Constant.DatabaseColumn.MY_ADDED_BOOK_TITLE_COLUMN
                + "," + Constant.DatabaseColumn.MY_ADDED_BOOK_TYPE_COLUMN
                + "," + Constant.DatabaseColumn.MY_ADDED_BOOK_URL_COLUMN
                + "," + Constant.DatabaseColumn.MY_ADDED_BOOK_COVER_COLUMN
                + "," + Constant.DatabaseColumn.MY_ADDED_BOOK_PAGES_COLUMN
                + "," + Constant.DatabaseColumn.MY_ADDED_BOOK_READ_PAGES_COLUMN
                + ") VALUES (%s,%s,%s,%s,%s,%s)";
    }

    @Override
    public String delete() {
        return "DELETE FROM " + Constant.DatabaseColumn.MY_ADDED_BOOK_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.MY_ADDED_BOOK_ID_COLUMN + "=" + "%s";
    }

    @Override
    public String retrieveSpecificType() {
        return "SELECT * FROM " + Constant.DatabaseColumn.MY_ADDED_BOOK_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.MY_ADDED_BOOK_TYPE_COLUMN + "=" + "%s";
    }


    @Override
    public String retrieveSpecificTags() {
        return "SELECT * FROM " + Constant.DatabaseColumn.MY_ADDED_BOOK_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.MY_ADDED_BOOK_URL_COLUMN + " =%s AND "
                + Constant.DatabaseColumn.MY_ADDED_BOOK_TYPE_COLUMN + " =%s";

    }

    @Override
    public String deleteSpecificDownload() {
        return "SELECT * FROM " + Constant.DatabaseColumn.MY_ADDED_BOOK_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.MY_ADDED_BOOK_TITLE_COLUMN + " =%s AND "
                + Constant.DatabaseColumn.MY_ADDED_BOOK_TYPE_COLUMN + " =%s";
    }
}
