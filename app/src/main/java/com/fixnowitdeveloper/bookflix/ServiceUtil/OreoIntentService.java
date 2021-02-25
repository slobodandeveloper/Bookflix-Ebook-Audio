package com.fixnowitdeveloper.bookflix.ServiceUtil;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.google.gson.Gson;
import com.fixnowitdeveloper.bookflix.ConnectionUtil.Connection;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseObject;
import com.fixnowitdeveloper.bookflix.JsonUtil.FavouriteUtil.FavouriteJson;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import java.util.ArrayList;

public class OreoIntentService extends JobIntentService {
    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;
    private String TAG = OreoIntentService.class.getName();

    public OreoIntentService() {
    }

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent intent) {

        enqueueWork(context, OreoIntentService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        Utility.Logger(TAG, "Setting : Working");

        if (intent != null) {


            //It load specific tags wallpaper at background
            //After loading it would add them into Wallpaper db

            RequestObject requestObject = intent.getParcelableExtra(Constant.IntentKey.REQUEST_OBJECT);
            String result = Connection.makeRequest(requestObject.getServerUrl(), requestObject.getJson(), requestObject.getRequestType());


            Utility.Logger(TAG, "JSON = " + requestObject.getJson());

            if (Utility.isEmptyString(result))
                return;

            if (result.equalsIgnoreCase(Constant.ImportantMessages.CONNECTION_ERROR))
                return;

            Gson gson = new Gson();
            Object object = null;
            DataObject dataObject = null;
            ArrayList<Object> objectList = new ArrayList<>();

            if (requestObject.getConnection() == Constant.CONNECTION.ALL_FAVOURITES) {

                object = gson.fromJson(result, FavouriteJson.class);
                dataObject = DataObject.getWallpaperObject(requestObject, object);

                if (dataObject.getCode().equalsIgnoreCase(Constant.ErrorCodes.success_code)) {

                    String userID = "null";

                    Management management = new Management(this);
                    PrefObject prefObject = management.getPreferences(new PrefObject()
                            .setRetrieveUserId(true).setRetrieveLogin(true));


                    if (prefObject.isLogin()) {
                        userID = prefObject.getUserId();
                    }

                    for (int i = 0; i < dataObject.getWallpaperList().size(); i++) {

                        DataObject dtObject = dataObject.getWallpaperList().get(i);
                        management.getDataFromDatabase(new DatabaseObject()
                                .setTypeOperation(Constant.TYPE.FAVOURITES)
                                .setDbOperation(Constant.DB.INSERT)
                                .setDataObject(new DataObject()
                                        .setId(dtObject.getId())
                                        .setUserId(userID)
                                        .setTitle(dtObject.getTitle())
                                        .setBookUrl(dtObject.getBookUrl())
                                        .setCoverUrl(dtObject.getOriginalUrl())
                                        .setArtistName(dtObject.getArtistName())
                                        .setOriginalUrl(dtObject.getOriginalUrl())));

                    }


                }

            }

            if (dataObject == null)
                return;


        }

    }


}
