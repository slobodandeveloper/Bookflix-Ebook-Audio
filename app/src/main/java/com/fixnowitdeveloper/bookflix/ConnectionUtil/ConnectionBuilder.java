package com.fixnowitdeveloper.bookflix.ConnectionUtil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.gson.Gson;
import com.fixnowitdeveloper.bookflix.BuildConfig;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseObject;
import com.fixnowitdeveloper.bookflix.FontUtil.Font;
import com.fixnowitdeveloper.bookflix.JsonUtil.AdJsonUtil.AdJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.CategoriesUtil.CategoriesJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.CommentUtil.CommentJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.FavouriteUtil.FavouriteJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.PrivacyPolicyUtil.PrivacyPolicyJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.TrendingUtil.TrendingJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.UserUtil.UserJson;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.ServiceUtil.MyIntentService;
import com.fixnowitdeveloper.bookflix.ServiceUtil.OreoIntentService;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import java.io.File;
import java.io.FileOutputStream;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import needle.Needle;
import needle.UiRelatedProgressTask;


public class ConnectionBuilder {
    private String TAG = ConnectionBuilder.class.getName();
    ACProgressFlower acProgressFlower = null;

    public ConnectionBuilder(final RequestObject requestObject) {

        if (!Utility.checkConnection(requestObject.getContext())) {

            if (requestObject.getInternetCallback() != null) {
                requestObject.getInternetCallback().onConnectivityFailed();
                return;
            }

            Utility.Toaster(requestObject.getContext(), Utility.getStringFromRes(requestObject.getContext(), R.string.internet_not_available), Toast.LENGTH_SHORT);
            return;
        }


        Utility.extraData(TAG, "Json = " + requestObject.toString());


        if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.UI) {

            if (requestObject.getConnection() == Constant.CONNECTION.LOGIN
                    || requestObject.getConnection() == Constant.CONNECTION.SINGLE_STATION
                    || requestObject.getConnection() == Constant.CONNECTION.SIGN_UP
                    || requestObject.getConnection() == Constant.CONNECTION.FORGOT
                    || requestObject.getConnection() == Constant.CONNECTION.UPDATE
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_COMMENT
                    || requestObject.getConnection() == Constant.CONNECTION.PRIVACY_POLICY) {


                acProgressFlower = getACProgressFlower(requestObject.getContext(),
                        Utility.getStringFromRes(requestObject.getContext(), R.string.progress));

                if (acProgressFlower != null)
                    acProgressFlower.show();

            }

            Needle.onBackgroundThread().execute(new UiRelatedProgressTask<String, Integer>() {

                @Override
                protected void onProgressUpdate(Integer integer) {

                }

                @Override
                protected String doWork() {

                    if (Constant.REQUEST.valueOf(requestObject.getRequestType()) == Constant.REQUEST.GET) {
                        return Connection.makeRequest(requestObject.getServerUrl(), requestObject.getRequestType());
                    } else if (Constant.REQUEST.valueOf(requestObject.getRequestType()) == Constant.REQUEST.POST) {
                        return Connection.makeRequest(requestObject.getServerUrl(), requestObject.getJson(), requestObject.getRequestType());
                    } else
                        return Connection.makeRequest(requestObject.getServerUrl(), requestObject.getRequestType());

                }

                @Override
                protected void thenDoUiRelatedWork(String data) {

                    Utility.Logger(TAG, "Response = " + data);

                    if (Utility.isEmptyString(data)) {
                        return;
                    }

                    if (data.equals(Constant.ImportantMessages.CONNECTION_ERROR)) {
                        return;
                    }

                    Gson gson = new Gson();
                    Object object = null;
                    String responseCode = null;
                    String responseMessage = null;
                    DataObject dataObject = null;

                    if (requestObject.getConnection() == Constant.CONNECTION.TRENDING_PHOTOS_URL
                            || requestObject.getConnection() == Constant.CONNECTION.HOME
                            || requestObject.getConnection() == Constant.CONNECTION.SEARCH
                            || requestObject.getConnection() == Constant.CONNECTION.ALL_ARTIST
                            || requestObject.getConnection() == Constant.CONNECTION.POPULAR
                            || requestObject.getConnection() == Constant.CONNECTION.NEWS_FEED
                            || requestObject.getConnection() == Constant.CONNECTION.ARTIST_DETAIL
                            || requestObject.getConnection() == Constant.CONNECTION.BOOK_DETAIL
                            || requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_AUTHOR_DETAIL
                            || requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_BOOK
                            ) {

                        object = gson.fromJson(data, TrendingJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.TRENDING_VIDEO_URL) {

                        object = gson.fromJson(data, TrendingJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.ALL_CATEGORIES) {

                        object = gson.fromJson(data, CategoriesJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.CATEGORIZED_PHOTOS) {

                        object = gson.fromJson(data, TrendingJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.CATEGORIZED_VIDEOS) {

                        object = gson.fromJson(data, TrendingJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.ALL_FAVOURITES) {

                        object = gson.fromJson(data, FavouriteJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.ADD_FAVOURITES) {

                        object = gson.fromJson(data, FavouriteJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.DELETE_FAVOURITES) {

                        object = gson.fromJson(data, FavouriteJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.ADD_COMMENT
                            || requestObject.getConnection() == Constant.CONNECTION.ALL_COMMENT) {

                        object = gson.fromJson(data, CommentJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.LOGIN) {

                        object = gson.fromJson(data, UserJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.SIGN_UP) {

                        object = gson.fromJson(data, UserJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.FORGOT) {

                        object = gson.fromJson(data, UserJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.UPDATE) {

                        object = gson.fromJson(data, UserJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.PRIVACY_POLICY) {

                        object = gson.fromJson(data, PrivacyPolicyJson.class);
                        dataObject = DataObject.getWallpaperObject(requestObject, object);

                    } else if (requestObject.getConnection() == Constant.CONNECTION.ADMOB) {

                        AdJson adJson = gson.fromJson(data, AdJson.class);
                        dataObject = new DataObject()
                                .setCode(adJson.getCode())
                                .setMessage(adJson.getMessage())
                                .setAdmobAppId(adJson.getAds().get(0).getAdmobAppId())
                                .setAdmobBannerId(adJson.getAds().get(0).getAdmobBannerId())
                                .setAdmobInterstitialId(adJson.getAds().get(0).getAdmobInterstitialId())
                                .setAdmobPublisherId(adJson.getAds().get(0).getAdmobPublisherId())
                                .setAdmobPrivacyUrl(adJson.getAds().get(0).getAdmobPrivacyUrl());

                    }

                    responseCode = dataObject.getCode();
                    responseMessage = dataObject.getMessage();


                    if (acProgressFlower != null && acProgressFlower.isShowing())
                        acProgressFlower.dismiss();

                    if (requestObject.getConnectionCallback() != null) {

                        if (responseCode.equals(Constant.ErrorCodes.success_code)) {
                            requestObject.getConnectionCallback().onSuccess(dataObject, requestObject);
                        } else if (responseCode.equals(Constant.ErrorCodes.error_code)) {

                            if (requestObject.getConnection() == Constant.CONNECTION.BOOK_DETAIL) {
                                requestObject.getConnectionCallback().onSuccess(dataObject, requestObject);
                            } else {
                                requestObject.getConnectionCallback().onError(responseMessage, requestObject);
                            }

                        } else {
                            requestObject.getConnectionCallback().onError(responseMessage, requestObject);
                        }

                    }

                }


            });

        } else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.BACKGROUND) {

            //region All background tasking functionalites

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                Intent intent = new Intent(requestObject.getContext(), OreoIntentService.class);
                intent.putExtra(Constant.IntentKey.REQUEST_OBJECT, requestObject);
                OreoIntentService.enqueueWork(requestObject.getContext(), intent);

            } else {

                Intent intent = new Intent(requestObject.getContext(), MyIntentService.class);
                intent.putExtra(Constant.IntentKey.REQUEST_OBJECT, requestObject);
                requestObject.getContext().startService(intent);

            }

            //endregion

        } else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.DOWNLOAD) {


            //region All Downloading functionalities

            final File folder = new File(requestObject.getContext().getFilesDir(), Utility.getStringFromRes(requestObject.getContext(), R.string.app_name));
            final ACProgressFlower[] acProgressFlower = {null};

            if (!folder.exists())
                folder.mkdirs();

            int downloadId = 0;
            NotificationManager notificationManager = null;
            final String folderPath = folder.getAbsolutePath();

            String extension = requestObject.getFileExtension();
            String serverUrl = null;
            ACProgressFlower acDialog = null;

            /*if (requestObject.getPostType().equalsIgnoreCase("image")) {
                extension = ".png";
                serverUrl = Constant.ServerInformation.PICTURE_URL + requestObject.getServerUrl();
            } else if (requestObject.getPostType().equalsIgnoreCase("video")) {
                extension = ".mp4";
                serverUrl = Constant.ServerInformation.VIDEO_URL + requestObject.getServerUrl();
            }*/


            serverUrl = requestObject.getServerUrl();
            Utility.Logger(TAG, "Setting : Working : Folder Path  : " + folderPath + " : Url : " + requestObject.getServerUrl() + " File Name = " + requestObject.getTitle());

            notificationManager = createNotification(requestObject.getContext(), String.valueOf(downloadId), requestObject.getLoadingText());
            final NotificationManager finalNotificationManager = notificationManager;
            final int finalDownloadId = downloadId;
            final String finalExtension = extension;

            final ACProgressFlower finalAcDialog = acDialog;
            final String fileName = requestObject.getPictureId().replaceAll("\\s", "_") + extension;
            Utility.Logger(TAG, "FileName = " + fileName);
            downloadId = PRDownloader.download(serverUrl, folder.getAbsolutePath(), fileName)
                    .build()
                    .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                        @Override
                        public void onStartOrResume() {

                            String loadingText;

                            if (requestObject.isRead()) {
                                loadingText = requestObject.getLoadingText();
                            } else {
                                loadingText = Utility.getStringFromRes(requestObject.getContext(), R.string.downloading);
                            }

                            acProgressFlower[0] = getACProgressFlower(requestObject.getContext(), loadingText);

                            if (acProgressFlower[0] != null)
                                acProgressFlower[0].show();

                        }
                    })
                    .setOnPauseListener(new OnPauseListener() {
                        @Override
                        public void onPause() {

                        }
                    })
                    .setOnCancelListener(new OnCancelListener() {
                        @Override
                        public void onCancel() {

                        }
                    })
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(Progress progress) {
                            int pro = Integer.parseInt(Long.toString((progress.currentBytes * 100) / progress.totalBytes));

                        }
                    })
                    .start(new OnDownloadListener() {
                        @Override
                        public void onDownloadComplete() {

                            if (finalNotificationManager != null) {


                                String link = folderPath + "/" + fileName;
                                Uri uri, coverUri = null;

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                    if (requestObject.isShare())
                                        uri = FileProvider.getUriForFile(requestObject.getContext(), BuildConfig.APPLICATION_ID + ".provider", new File(link));
                                    else
                                        uri = Uri.fromFile(new File(link));

                                } else
                                    uri = Uri.fromFile(new File(link));

                                coverUri = uri;

                                /*if (requestObject.getPostType().equalsIgnoreCase("image")) {

                                    coverUri = uri;

                                } else if (requestObject.getPostType().equalsIgnoreCase("video")) {

                                    Bitmap bitmap = getMediaMetaRetriever(requestObject.getContext(), uri);
                                    coverUri = getVideoBitmapUri(requestObject.getContext(), bitmap, folderPath, requestObject.getPictureId() + ".png");

                                }*/

                                Utility.Logger(TAG, "Original Uri : " + uri.toString()
                                        + " Cover Uri = " + coverUri.toString() + " FileName = " + fileName + " Link = " + link);

                                Management management = new Management(requestObject.getContext());

                                if (!requestObject.isRead()) {
                                    management.getDataFromDatabase(new DatabaseObject()
                                            .setTypeOperation(Constant.TYPE.DOWNLOAD)
                                            .setDbOperation(Constant.DB.INSERT)
                                            .setDataObject(new DataObject()
                                                    .setTitle(requestObject.getTitle())
                                                    .setArtistName(requestObject.getArtistName())
                                                    .setBookUrl(String.valueOf(uri))
                                                    .setFileType(requestObject.getPostType())
                                                    .setCoverUrl(requestObject.getCoverUrl())
                                                    .setPostType(requestObject.getPostType())));
                                }

                                if (acProgressFlower[0].isShowing())
                                    acProgressFlower[0].dismiss();

                                if (requestObject.getConnectionCallback() != null) {

                                    requestObject.getConnectionCallback().onSuccess(new DataObject()
                                            .setDownloadUri(uri), requestObject);

                                }

                                //folderPath;
                                //requestObject.getPictureId()

                            }

                        }

                        @Override
                        public void onError(Error error) {

                            Utility.Logger(TAG, "Error : Connection = "
                                    + error.isConnectionError() + " : Server Error = " + error.isServerError());

                            if (requestObject.getConnectionCallback() != null) {
                                requestObject.getConnectionCallback().onError(Utility.getStringFromRes(requestObject.getContext(), R.string.download_error)
                                        , requestObject);
                            }

                        }
                    });

            //endregion


        }


    }


    public static class CreateConnection {
        private RequestObject requestObject;

        public CreateConnection setRequestObject(RequestObject requestObject) {
            this.requestObject = requestObject;
            return this;
        }

        public ConnectionBuilder buildConnection() {
            return new ConnectionBuilder(requestObject);
        }

    }

    /**
     * <p>It is used to Create Notification
     * with Look Up action button</p>
     *
     * @param
     * @param aMessage
     * @param context
     */
    public NotificationManager createNotification(Context context, String id, String aMessage) {
        int NOTIFY_ID = Integer.parseInt(id); // ID of notification
        //String id = context.getString(R.string.default_notification_channel_id); // default_channel_id
        String title = context.getString(R.string.app_name); // Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        Utility.Logger(TAG, "Working");

        NotificationManager notifManager = null;

        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);

            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }

            builder = new NotificationCompat.Builder(context, id);
            builder.setContentTitle(context.getString(R.string.app_name) + " " + context.getString(R.string.downloading))                            // required
                    .setSmallIcon(R.mipmap.iconobookflix)   // required
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(aMessage))
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        } else {

            builder = new NotificationCompat.Builder(context, id);
            builder.setContentTitle(context.getString(R.string.app_name) + " " + context.getString(R.string.downloading))                            // required
                    .setSmallIcon(R.mipmap.iconobookflix)   // required
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(aMessage))
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);

        }

        //Notification notification = builder.build();
        //notifManager.notify(NOTIFY_ID, notification);

        return notifManager;
    }

    /**
     * <p>It is used to retrieve Progress Dialog object</p>
     *
     * @param context
     * @param progress
     * @return
     */
    private ACProgressFlower getACProgressFlower(Context context, String progress) {

        ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(progress)
                .textTypeface(Font.ubuntu_medium_font(context))
                .fadeColor(Color.DKGRAY).build();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;


    }


    /**
     * <p>It is used to retrieve Media of save Video</p>
     *
     * @param context
     * @param mUri
     * @return
     */
    private Bitmap getMediaMetaRetriever(Context context, Uri mUri) {

        /*FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
        mmr.setDataSource(context, mUri);
        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
        Bitmap b = mmr.getFrameAtTime(2000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST); // frame at 2 seconds
        //byte [] artwork = mmr.getEmbeddedPicture();
        mmr.release();*/

        return null;

    }


    /**
     * <p>It is used to retrieve Video Bitmap Uri</p>
     *
     * @param context
     * @param bitmap
     * @param folder
     * @param name
     * @return
     */
    private Uri getVideoBitmapUri(Context context, Bitmap bitmap, String folder, String name) {

        File file = new File(folder, name);
        Uri uri;

        //if (file.exists()) file.delete();

        try {

            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        } else
            uri = Uri.fromFile(file);

        return uri;
    }

}
