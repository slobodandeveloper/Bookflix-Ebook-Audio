package com.fixnowitdeveloper.bookflix.DatabaseUtil;

import com.fixnowitdeveloper.bookflix.Utility.Utility;

public class DbQuery {
    private FavouriteQueries favouriteQueries;
    private FileQueries fileQueries;
    private DownloadQueries downloadQueries;
    private Mp3Queries mp3Queries;
    private MyAddedBooksQueries myAddedBooksQueries;

    protected DbQuery() {

        Utility.Logger(DbQuery.class.getName(), "Setting : Working");
        favouriteQueries = new FavouriteQueries();
        fileQueries = new FileQueries();
        downloadQueries = new DownloadQueries();
        mp3Queries = new Mp3Queries();
        myAddedBooksQueries = new MyAddedBooksQueries();

    }

    protected FavouriteQueries getFavouriteQueries() {
        return favouriteQueries;
    }

    protected FileQueries getFileQueries() {
        return fileQueries;
    }

    protected DownloadQueries getDownloadQueries() {
        return downloadQueries;
    }

    protected Mp3Queries getMp3Queries() {
        return mp3Queries;
    }

    public MyAddedBooksQueries getMyAddedBooksQueries() {
        return myAddedBooksQueries;
    }
}
