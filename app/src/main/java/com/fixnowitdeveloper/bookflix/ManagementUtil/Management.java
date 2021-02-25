package com.fixnowitdeveloper.bookflix.ManagementUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;

import com.fixnowitdeveloper.bookflix.ConnectionUtil.ConnectionBuilder;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseHandler;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseObject;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.QueryFactory;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.QueryRunner;
import com.fixnowitdeveloper.bookflix.ObjectUtil.CursorObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import java.util.ArrayList;

public class Management {
    private String TAG = Management.class.getName();
    private Context context;
    private QueryRunner queryRunner;
    private QueryFactory queryFactory;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public Management(Context context) {

        Utility.Logger(TAG, "Setting : Working");

        this.context = context;
        queryFactory = new QueryFactory();
        queryRunner = new QueryRunner(context);
        sharedPreferences = context.getSharedPreferences(Utility.getStringFromRes(context, R.string.app_name), Context.MODE_PRIVATE);

    }


    /**
     * <p>It is used to send Request to Server</p>
     *
     * @param requestObject
     */
    public void sendRequestToServer(RequestObject requestObject) {

        String serverUrl = null;
        String requestType = Constant.REQUEST.POST.toString();

        Utility.Logger(TAG, "Setting : " + requestObject.toString());


        //region All functionality of giving Server Url

        if (requestObject.getConnection() == Constant.CONNECTION.HOME
                || requestObject.getConnection() == Constant.CONNECTION.ADMOB
                || requestObject.getConnection() == Constant.CONNECTION.ALL_ARTIST
                || requestObject.getConnection() == Constant.CONNECTION.POPULAR
                || requestObject.getConnection() == Constant.CONNECTION.NEWS_FEED
                || requestObject.getConnection() == Constant.CONNECTION.ARTIST_DETAIL
                || requestObject.getConnection() == Constant.CONNECTION.BOOK_DETAIL
                || requestObject.getConnection() == Constant.CONNECTION.REPORT
                || requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_AUTHOR_DETAIL
                || requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_BOOK
                ) {

            serverUrl = Constant.ServerInformation.TRENDING_PHOTOS_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.TRENDING_VIDEO_URL) {

            serverUrl = Constant.ServerInformation.TRENDING_VIDEOS_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.ALL_CATEGORIES
                ) {

            serverUrl = Constant.ServerInformation.ALL_CATEGORIES_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.CATEGORIZED_PHOTOS) {

            serverUrl = Constant.ServerInformation.CATEGORIZED_PHOTOS_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.CATEGORIZED_VIDEOS) {

            serverUrl = Constant.ServerInformation.CATEGORIZED_VIDEOS_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.LOGIN) {

            serverUrl = Constant.ServerInformation.LOGIN_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.SIGN_UP) {

            serverUrl = Constant.ServerInformation.REGISTER_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.FORGOT) {

            serverUrl = Constant.ServerInformation.FORGOT_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.UPDATE) {

            serverUrl = Constant.ServerInformation.UPDATE_PROFILE_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.ADD_FAVOURITES) {

            serverUrl = Constant.ServerInformation.ADD_FAVOURITES_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.DELETE_FAVOURITES) {

            serverUrl = Constant.ServerInformation.DELETE_FAVOURITES_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.ALL_FAVOURITES) {

            serverUrl = Constant.ServerInformation.ALL_FAVOURITES_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.SEARCH) {

            serverUrl = Constant.ServerInformation.SEARCH_PHOTO_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.ADD_COMMENT
                || requestObject.getConnection() == Constant.CONNECTION.ALL_COMMENT) {

            serverUrl = Constant.ServerInformation.COMMENT_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.LIKE_DISLIKES) {

            serverUrl = Constant.ServerInformation.LIKES_DISLIKES_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.PRIVACY_POLICY) {

            serverUrl = Constant.ServerInformation.PRIVACY_POLICY_URL;

        } else if (requestObject.getConnection() == Constant.CONNECTION.DOWNLOAD
                && requestObject.getConnectionType() == Constant.CONNECTION_TYPE.DOWNLOAD) {

            serverUrl = requestObject.getServerUrl();

        } else if (requestObject.getConnection() == Constant.CONNECTION.DOWNLOAD
                && requestObject.getConnectionType() == Constant.CONNECTION_TYPE.BACKGROUND) {

            serverUrl = Constant.ServerInformation.TRENDING_PHOTOS_URL;

        }

        //endregion

        //It initialize the Connection to Server

        new ConnectionBuilder.CreateConnection()
                .setRequestObject(requestObject
                        .setContext(context)
                        .setServerUrl(serverUrl)
                        .setRequestType(requestType))
                .buildConnection();


    }


    /**
     * <p>It is used to retrieve data from Database</p>
     *
     * @param databaseObject
     */
    public ArrayList<Object> getDataFromDatabase(DatabaseObject databaseObject) {
        ArrayList<Object> objectArrayList = new ArrayList<>();
        CursorObject cursorObject = null;

        //Get required Query for Specific Db Operation

        String formattedQuery = queryFactory.getRequiredFormattedQuery(databaseObject);
        Utility.Logger(TAG, "Setting : Working : Query = " + formattedQuery);

        //If Query Empty then return Empty Arraylist

        if (Utility.isEmptyString(formattedQuery))
            return objectArrayList;

        //Initialize Database Handler

        if (databaseObject.getTypeOperation() == Constant.TYPE.FAVOURITES
                || databaseObject.getTypeOperation() == Constant.TYPE.HISTORY
                || databaseObject.getTypeOperation() == Constant.TYPE.MY_ADDED_BOOKS_STATUS
                || databaseObject.getTypeOperation() == Constant.TYPE.DOWNLOAD
                || databaseObject.getTypeOperation() == Constant.TYPE.FILE_READING_STATUS) {

            sqLiteOpenHelper = new DatabaseHandler(context);

        }

        //Perform Db Operation on formatted Sql Queries

        if (databaseObject.getDbOperation() == Constant.DB.RETRIEVE) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        } else if (databaseObject.getDbOperation() == Constant.DB.INSERT) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null)
                    cursorObject.getDatabase().close();
            }

        } else if (databaseObject.getDbOperation() == Constant.DB.DELETE) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null)
                    cursorObject.getDatabase().close();
            }

        } else if (databaseObject.getDbOperation() == Constant.DB.DELETE_FAVOURITES) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null)
                    cursorObject.getDatabase().close();
            }

        } else if (databaseObject.getDbOperation() == Constant.DB.UPDATE) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null) {
                if (cursorObject.getDatabase() != null)
                    cursorObject.getDatabase().close();
            }

        } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_BOOK) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_TYPE) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        } else if (databaseObject.getDbOperation() == Constant.DB.SPECIFIC_BOOK_BY_NAME) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        }

        return objectArrayList;
    }


    /**
     * <p>It is used to get Preference Data</p>
     *
     * @param prefObject
     * @return
     */
    public PrefObject getPreferences(PrefObject prefObject) {
        PrefObject pref = new PrefObject();

        if (prefObject.isRetrieveTags()) {
            pref.setTags(sharedPreferences.getString(Constant.SharedPref.PREF_TAGS, null));
        }

        if (prefObject.isRetrieveNext()) {
            pref.setNextPage(sharedPreferences.getString(Constant.SharedPref.NEXT_URL, null));
        }

        if (prefObject.isRetrievePosition()) {
            pref.setCurrentPosition(sharedPreferences.getInt(Constant.SharedPref.POSITION, -1));
        }

        if (prefObject.isRetrieveFirstLaunch()) {
            pref.setFirstLaunch(sharedPreferences.getBoolean(Constant.SharedPref.FIRST_LAUNCH, true));
        }

        if (prefObject.isRetrieveLogin()) {
            pref.setLogin(sharedPreferences.getBoolean(Constant.SharedPref.LOGIN, false));
        }

        if (prefObject.isRetrieveUserId()) {
            pref.setUserId(sharedPreferences.getString(Constant.SharedPref.USER_ID, "0"));
        }

        if (prefObject.isRetrieveUserRemember()) {
            pref.setUserRemember(sharedPreferences.getBoolean(Constant.SharedPref.USER_REMEMBER, false));
        }

        if (prefObject.isRetrieveNewsfeed()) {
            pref.setNewsfeedId(sharedPreferences.getString(Constant.SharedPref.NEWS_FEED, "0"));
        }

        if (prefObject.isRetrieveUserCredential()) {

            pref.setUserId(sharedPreferences.getString(Constant.SharedPref.USER_ID, null));
            pref.setUserEmail(sharedPreferences.getString(Constant.SharedPref.USER_EMAIL, null));
            pref.setUserPassword(sharedPreferences.getString(Constant.SharedPref.USER_PASSWORD, null));
            pref.setFirstName(sharedPreferences.getString(Constant.SharedPref.USER_FIRST_NAME, null));
            pref.setLastName(sharedPreferences.getString(Constant.SharedPref.USER_LAST_NAME, null));
            pref.setPictureUrl(sharedPreferences.getString(Constant.SharedPref.USER_PICTURE, null));
            pref.setLoginType(sharedPreferences.getString(Constant.SharedPref.LOGIN_TYPE, Constant.LoginType.NATIVE_LOGIN));
            pref.setUid(sharedPreferences.getString(Constant.SharedPref.UID, null));

        }

        if (prefObject.isRetrieveNightMode()) {
            pref.setNightMode(sharedPreferences.getBoolean(Constant.SharedPref.NIGHT_MODE, false));
        }

        if (prefObject.isRetrievePush()) {
            pref.setPush(sharedPreferences.getBoolean(Constant.SharedPref.PUSH_NOTIFICATION, false));
        }

        if (prefObject.isRetrieveDownloadWifi()) {
            pref.setDownloadWifi(sharedPreferences.getBoolean(Constant.SharedPref.DOWNLOAD_WIFI, false));
        }

        Utility.Logger(TAG, "getPreference = " + pref.toString());

        return pref;

    }


    /**
     * <p>It is used to save Preferences data</p>
     *
     * @param prefObject
     */
    public void savePreferences(PrefObject prefObject) {
        editor = sharedPreferences.edit();

        Utility.Logger(TAG, "Preference = " + prefObject.toString());

        if (prefObject.isSaveTags()) {
            editor.putString(Constant.SharedPref.PREF_TAGS, prefObject.getTags());
        } else if (prefObject.isSaveNext()) {
            editor.putString(Constant.SharedPref.NEXT_URL, prefObject.getNextPage());
        } else if (prefObject.isSavePosition()) {
            editor.putInt(Constant.SharedPref.POSITION, prefObject.getCurrentPosition());
        } else if (prefObject.isSaveFirstLaunch()) {
            editor.putBoolean(Constant.SharedPref.FIRST_LAUNCH, prefObject.isFirstLaunch());
        } else if (prefObject.isSaveLogin()) {
            editor.putBoolean(Constant.SharedPref.LOGIN, prefObject.isLogin());
        } else if (prefObject.isSaveUserId()) {
            editor.putString(Constant.SharedPref.USER_ID, prefObject.getUserId());
        } else if (prefObject.isSaveUserRemember()) {
            editor.putBoolean(Constant.SharedPref.USER_REMEMBER, prefObject.isUserRemember());
        } else if (prefObject.isSaveNewsfeed()) {
            editor.putString(Constant.SharedPref.NEWS_FEED, prefObject.getNewsfeedId());
        } else if (prefObject.isSaveUserCredential()) {

            editor.putString(Constant.SharedPref.USER_ID, prefObject.getUserId());
            editor.putString(Constant.SharedPref.USER_EMAIL, prefObject.getUserEmail());
            editor.putString(Constant.SharedPref.USER_PASSWORD, prefObject.getUserPassword());
            editor.putString(Constant.SharedPref.USER_FIRST_NAME, prefObject.getFirstName());
            editor.putString(Constant.SharedPref.USER_LAST_NAME, prefObject.getLastName());
            editor.putString(Constant.SharedPref.USER_PICTURE, prefObject.getPictureUrl());
            editor.putString(Constant.SharedPref.LOGIN_TYPE, prefObject.getLoginType());
            editor.putString(Constant.SharedPref.UID, prefObject.getUid());

        } else if (prefObject.isSaveNightMode()) {
            editor.putBoolean(Constant.SharedPref.NIGHT_MODE, prefObject.isNightMode());
        } else if (prefObject.isSaveDownloadWifi()) {
            editor.putBoolean(Constant.SharedPref.DOWNLOAD_WIFI, prefObject.isDownloadWifi());
        } else if (prefObject.isSavePush()) {
            editor.putBoolean(Constant.SharedPref.PUSH_NOTIFICATION, prefObject.isPush());
        }

        editor.commit();

    }


}
