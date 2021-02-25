package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.folioreader.FolioReader;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.util.ReadPositionListener;
import com.folioreader.util.ReadTotalPagesListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.gson.Gson;
import com.fixnowitdeveloper.bookflix.AdapterUtil.BookDetailAdapter;
import com.fixnowitdeveloper.bookflix.BuildConfig;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseObject;
import com.fixnowitdeveloper.bookflix.FontUtil.Font;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.BookDetailCallback;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.AdObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BookHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.HomeObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;
import com.hsalf.smilerating.SmileRating;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.File;
import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;


public class Viewer extends AppCompatActivity implements View.OnClickListener, ConnectionCallback, BookDetailCallback {

    private String TAG = Viewer.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<Object> favouriteArraylist = new ArrayList<>();
    private PrefObject prefObject;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewBook;
    private BookDetailAdapter bookDetailAdapter;
    private BookHeaderObject bookDetail;
    private String bookUrl;
    private ImageView imageShare;
    private ImageView imageFavourite;
    private String extension = null;
    private ImageView imageReport;
    private boolean isAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_viewer);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI

        //region For Showing Interstitial Ads
        Constant.adInterval++;
        if (Constant.adInterval == Constant.Credentials.screenInterstitialAdsCount) {
            Utility.showInterstitialAd(this, getACProgressFlower(this, Utility.getStringFromRes(this, R.string.load_ad)));
            Constant.adInterval = 0;
        }
        //endregion


    }


    /**
     * <p>It is used to retrieve intent data</p>
     */
    private void getIntentData() {

        bookDetail = getIntent().getParcelableExtra(Constant.IntentKey.BOOK_DETAIL);
        isAction = getIntent().getBooleanExtra(Constant.IntentKey.BACK_ACTION, false);
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        objectArrayList.add(new ProgressObject());

        management = new Management(this);
        bookUrl = bookDetail.getBookUrl();

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.book_detail));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);


        imageShare = findViewById(R.id.image_backward);
        imageShare.setVisibility(View.GONE);
        imageShare.setImageResource(R.drawable.ic_share);

        imageFavourite = findViewById(R.id.image_favourite);
        imageFavourite.setVisibility(View.VISIBLE);
        imageFavourite.setTag(0);
        imageFavourite.setImageResource(R.drawable.ic_btn_unmark_favourite);

        imageReport = findViewById(R.id.image_share);
        imageReport.setVisibility(View.GONE);
        imageReport.setImageResource(R.drawable.ic_ban);

        //Initialize Recycler View Layout Manager

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerViewBook = (RecyclerView) findViewById(R.id.recycler_view_book);
        recyclerViewBook.setLayoutManager(gridLayoutManager);

        //Initialize & Setup Adapter with Recycler View

        bookDetailAdapter = new BookDetailAdapter(this, objectArrayList, this) {
            @Override
            public void select(boolean isLocked, int position) {

                if (!isLocked) {


                }

            }
        };
        recyclerViewBook.setAdapter(bookDetailAdapter);

        //Sending request to Server for retrieving book detail

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson(bookDetail.getBookId(), bookDetail.getBookTag()))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.BOOK_DETAIL)
                .setConnectionCallback(this));


        imageBack.setOnClickListener(this);
        imageShare.setOnClickListener(this);
        imageReport.setOnClickListener(this);
        imageFavourite.setOnClickListener(this);


    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String functionality, String postId, String userId) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", functionality);
            jsonObject.accumulate("user_id", userId);
            jsonObject.accumulate("post_id", postId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String postId, String postTags) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "specific_book_detail");
            jsonObject.accumulate("tags", postTags);
            jsonObject.accumulate("post_id", postId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String userId, String postId, String comment, String rating) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "add_comments");
            jsonObject.accumulate("user_id", userId);
            jsonObject.accumulate("post_id", postId);
            jsonObject.accumulate("comment", comment);
            jsonObject.accumulate("rating", rating);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getDownloadJson(String functionality, String postId, String requiredAction) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", functionality);
            jsonObject.accumulate("required", requiredAction);
            jsonObject.accumulate("post_id", postId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getReportJson(String userId, String postId, String comment) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "add_report");
            jsonObject.accumulate("user_id", userId);
            jsonObject.accumulate("post_id", postId);
            jsonObject.accumulate("comment", comment);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    @Override
    protected void onResume() {
        super.onResume();


        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserId(true).setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        if (!prefObject.isLogin())
            return;

        //Check either this book is saved by user or not
        //It would send request to 'db' for checking it

        favouriteArraylist.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(Constant.TYPE.FAVOURITES)
                .setDbOperation(Constant.DB.SPECIFIC_TYPE)
                .setDataObject(new DataObject()
                        .setId(bookDetail.getBookId())
                        .setUserId(prefObject.getUserId()))));

        if (favouriteArraylist.size() > 0) {

            imageFavourite.setTag(1);
            imageFavourite.setImageResource(R.drawable.ic_btn_mark_favourite);

        }


    }

    @Override
    public void onClick(View v) {
        if (v == imageBack) {

            if (isAction) {
                startActivity(new Intent(getApplicationContext(), Base.class));
            }
            finish();
        }

        if (v == imageFavourite) {

            if (!prefObject.isLogin()) {
                startActivity(new Intent(this, OnBoarding.class));
                return;
            }

            int tag = (int) imageFavourite.getTag();
            if (tag == 0) {

                imageFavourite.setImageResource(R.drawable.ic_btn_mark_favourite);
                imageFavourite.setTag(1);

                management.getDataFromDatabase(new DatabaseObject()
                        .setTypeOperation(Constant.TYPE.FAVOURITES)
                        .setDbOperation(Constant.DB.INSERT)
                        .setDataObject(new DataObject()
                                .setId(bookDetail.getBookId())
                                .setUserId(prefObject.getUserId())
                                .setTitle(bookDetail.getBookName())
                                .setBookUrl(bookDetail.getBookUrl())
                                .setCoverUrl(bookDetail.getBookImage())
                                .setArtistName(bookDetail.getBookAuthorName())
                                .setOriginalUrl(bookDetail.getBookImage())));

                management.sendRequestToServer(new RequestObject()
                        .setJson(getJson("add_favourites", bookDetail.getBookId(), prefObject.getUserId()))
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setConnection(Constant.CONNECTION.ADD_FAVOURITES)
                        .setConnectionCallback(this));

            } else if (tag == 1) {

                imageFavourite.setImageResource(R.drawable.ic_btn_unmark_favourite);
                imageFavourite.setTag(0);

                if (!Utility.isEmptyString(bookDetail.getBookId()))
                    management.getDataFromDatabase(new DatabaseObject()
                            .setTypeOperation(Constant.TYPE.FAVOURITES)
                            .setDbOperation(Constant.DB.DELETE)
                            .setDataObject(new DataObject()
                                    .setId(bookDetail.getBookId())
                                    .setUserId(prefObject.getUserId())));

                management.sendRequestToServer(new RequestObject()
                        .setJson(getJson("delete_favourites", bookDetail.getBookId(), prefObject.getUserId()))
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setConnection(Constant.CONNECTION.DELETE_FAVOURITES)
                        .setConnectionCallback(this));

            }

        }

        if (v == imageShare) {


            int minVersion = BuildConfig.VERSION_CODE;
            Uri deepLinkUri = Uri.parse(String.format(Constant.ServerInformation.DESKTOP_NEW_LINKS
                    , bookDetail.getBookId()
                    , Constant.PostType.POST_TYPE
                    , Utility.getAppPlaystoreUrl(this)));

            buildDeepLink(deepLinkUri, minVersion);

            //region Old File Downloading Function

            /*<p>Un Comment below lines to enable File downloading and sharing functionality</p>*/

            /*if (Utility.isEmptyString(bookUrl))
                return;

            if (URLUtil.isValidUrl(bookUrl)) {
                Utility.shareApp(getApplicationContext(), bookUrl);
            } else {

                if (bookUrl.endsWith(".pdf")) {
                    extension = ".pdf";
                } else if (bookUrl.endsWith(".epub")) {
                    extension = ".epub";
                }

                management.sendRequestToServer(new RequestObject()
                        .setServerUrl(Constant.ServerInformation.VIDEO_URL + bookUrl)
                        .setTitle(bookDetail.getBookName())
                        .setArtistName(bookDetail.getBookAuthorName())
                        .setCoverUrl(bookDetail.getBookImage())
                        .setShare(true)
                        .setRead(false)
                        .setFileExtension(extension)
                        .setConnectionType(Constant.CONNECTION_TYPE.DOWNLOAD)
                        .setConnection(Constant.CONNECTION.DOWNLOAD)
                        .setPictureId(bookDetail.getBookUrl())
                        .setConnectionCallback(this));

            }*/
            //endregion

        }

        if (v == imageReport) {

            if (!prefObject.isLogin()) {
                startActivity(new Intent(this, OnBoarding.class));
                return;
            }

            showReportSheet(this);

        }

    }


    @Override
    public void onSuccess(final Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {
            final ArrayList<Object> tempList = new ArrayList<>();

            if (requestObject.getConnection() == Constant.CONNECTION.DOWNLOAD) {

                //It send the request to server to increase the download statistics of book

                management.sendRequestToServer(new RequestObject()
                        .setJson(getDownloadJson("counter", bookDetail.getBookId(), "download"))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.DOWNLOAD)
                        .setConnectionCallback(Viewer.this));

                if (requestObject.isShare()) {

                    //In case of sharing option , it would share the book with friends

                    Utility.shareBook(this, ((DataObject) data).getDownloadUri());

                } else if (requestObject.isRead()) {

                    //Read Book if user click Read button
                    //It also identifies either it's pdf or Epub file

                    if (String.valueOf(((DataObject) data).getDownloadUri()).endsWith(".pdf")) {

                        //In case of Pdf file , it would open ReadBook.class activity

                        DataObject dataObject = (DataObject) data;
                        dataObject.setTitle(bookDetail.getBookName())
                                .setCoverUrl(bookDetail.getBookImage())
                                .setBookUrl(String.valueOf(((DataObject) data).getDownloadUri()));

                        Intent intent = new Intent(this, ReadBook.class);
                        intent.putExtra(Constant.IntentKey.BOOK_DETAIL, dataObject);
                        startActivity(intent);

                    } else {


                        //In case of Epub file , it would open FolioReader
                        //We also check out either this position is already read by user or not

                        tempList.addAll(management.getDataFromDatabase(new DatabaseObject()
                                .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                                .setDbOperation(Constant.DB.SPECIFIC_BOOK)
                                .setDataObject(new DataObject()
                                        .setBookUrl(String.valueOf(((DataObject) data).getDownloadUri()))
                                        .setFileType(Constant.DataType.EPUB))));


                        FolioReader folioReader = FolioReader.getInstance(getApplicationContext(),
                                new ReadTotalPagesListener() {
                                    @Override
                                    public void saveTotalPages(String totalPages) {


                                        //It save the total No of pages in a book
                                        //in case of user first time load the book

                                        if (tempList.size() <= 0) {
                                            management.getDataFromDatabase(new DatabaseObject()
                                                    .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                                                    .setDbOperation(Constant.DB.INSERT)
                                                    .setDataObject(new DataObject()
                                                            .setTitle(bookDetail.getBookName())
                                                            .setFileType(Constant.DataType.EPUB)
                                                            .setBookUrl(String.valueOf(((DataObject) data).getDownloadUri()))
                                                            .setCoverUrl(bookDetail.getBookImage())
                                                            .setBookPage(totalPages)));
                                        }

                                    }
                                });

                        folioReader.saveThemeOption(Utility.isNightMode(this));
                        folioReader.setReadPositionListener(new ReadPositionListener() {
                            @Override
                            public void saveReadPosition(ReadPosition readPosition) {

                                //It saves the last read position at which user left off
                                //It retrieve data from Db for getting Book id
                                //so that we would update the last read position

                                tempList.clear();
                                tempList.addAll(management.getDataFromDatabase(new DatabaseObject()
                                        .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                                        .setDbOperation(Constant.DB.SPECIFIC_BOOK)
                                        .setDataObject(new DataObject()
                                                .setBookUrl(String.valueOf(((DataObject) data).getDownloadUri()))
                                                .setFileType(Constant.DataType.EPUB))));

                                if (tempList.size() > 0) {

                                    //In this part we update the last read position
                                    //in db file so that user would start from where
                                    //it left the book

                                    DataObject dbObject = (DataObject) tempList.get(0);
                                    management.getDataFromDatabase(new DatabaseObject()
                                            .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                                            .setDbOperation(Constant.DB.UPDATE)
                                            .setDataObject(new DataObject()
                                                    .setId(dbObject.getId())
                                                    .setCurrentPage(readPosition.toJson())));

                                }

                            }
                        });


                        //In case of user already read this book
                        //then it would open specified pages at which user left off

                        if (tempList.size() > 0) {

                            DataObject folioBookData = (DataObject) tempList.get(0);
                            Gson gson = new Gson();
                            ReadPositionImpl readPosition = gson.fromJson(folioBookData.getCurrentPage(), ReadPositionImpl.class);
                            folioReader.setReadPosition(readPosition);

                        }

                        String filePath = new File(((DataObject) data).getDownloadUri().getPath()).getAbsolutePath();
                        Utility.Logger(TAG, "Epub File Path = " + filePath + " Uri = " + ((DataObject) data).getDownloadUri());
                        folioReader.openBook(filePath);
                    }


                } else {

                    //In case of user only Download file , rather than 'Share' or either 'Read'
                    //It would show Interstitial Advertisement

                    Utility.showInterstitialAd(this, getACProgressFlower(this, Utility.getStringFromRes(this, R.string.load_ad)));

                }

            } else if (requestObject.getConnection() == Constant.CONNECTION.BOOK_DETAIL) {

                //It get data from server regarding any specific book
                //Show the book detail in recycler view

                DataObject dataObject = (DataObject) data;
                objectArrayList.clear();
                objectArrayList.add(bookDetail);

                if (Constant.Credentials.isAdmobNativeBookDetail && Constant.Credentials.isAdmobBannerAds) {

                    if (dataObject.getHomeList().size() > 1)
                        objectArrayList.add(new AdObject());
                }

                for (int i = 0; i < dataObject.getHomeList().size(); i++) {

                    if (dataObject.getHomeList().get(i) instanceof HomeObject) {

                        if (((HomeObject) dataObject.getHomeList().get(i)).getData_type() == Constant.DATA_TYPE.COMMON) {

                            //Placing Admob Ad Banner between Content

                            if (Constant.Credentials.isAdmobNativeBookDetail && Constant.Credentials.isAdmobBannerAds)
                                objectArrayList.add(new AdObject());

                        }

                    }

                    objectArrayList.add(dataObject.getHomeList().get(i));

                }

                //objectArrayList.addAll(dataObject.getHomeList());

                bookDetailAdapter.notifyDataSetChanged();

            } else if (requestObject.getConnection() == Constant.CONNECTION.ADD_COMMENT) {

                //In case of successfully adding 'Comment'
                //It notify the recycler view for showing comment data

                bookDetailAdapter.notifyDataSetChanged();

            }


        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Error = " + data);

        //It would trigger when error generate
        //in result of any server request

        if (requestObject.getConnection() == Constant.CONNECTION.BOOK_DETAIL) {

            objectArrayList.clear();
            objectArrayList.add(bookDetail);
            bookDetailAdapter.notifyDataSetChanged();

        } else if (requestObject.getConnection() == Constant.CONNECTION.ADD_COMMENT) {

            int pos = (objectArrayList.size() - 1);
            objectArrayList.remove(pos);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


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


    @Override
    public void onSelect(int parentPosition, int childPosition) {

        HomeObject homeObject = null;
        DataObject dataObject = null;

        if (parentPosition >= 0) {

            homeObject = (HomeObject) objectArrayList.get(parentPosition);
            dataObject = homeObject.getDataObjectArrayList().get(childPosition);

        } else {

            dataObject = (DataObject) objectArrayList.get(childPosition);

        }

        BookHeaderObject bookHeaderObject = new BookHeaderObject()
                .setBookId(dataObject.getId()).setBookName(dataObject.getTitle())
                .setBookDescription(dataObject.getDescription()).setBookAuthorName(dataObject.getArtistName())
                .setBookDownloadCount(dataObject.getDownloads()).setBookReviewCount(dataObject.getComments())
                .setBookTag(dataObject.getTags()).setBookRating(dataObject.getRating())
                .setBookImage(dataObject.getOriginalUrl()).setBookUrl(dataObject.getBookUrl());

        Intent intent = new Intent(this, Viewer.class);
        intent.putExtra(Constant.IntentKey.BOOK_DETAIL, bookHeaderObject);
        intent.putExtra(Constant.IntentKey.BACK_ACTION, true);
        startActivity(intent);
        finish();

    }


    @Override
    public void addReview() {

        if (!prefObject.isLogin()) {

            startActivity(new Intent(this, OnBoarding.class));

            return;
        }

        //It would trigger when we click 'Add Review' button
        //It would open Bottom Sheet for adding Review to Book

        showReviewSheet(this);

    }

    @Override
    public void readBook() {

        final ArrayList<Object> tempList = new ArrayList<>();
        String serverUrl;


        //When we click 'Read' button , it would be trigger
        //It start downloading book as well as open it
        //for reading once , it completed

        //In case of directly file link like 'http://'
        //it would open directly without downloading it

        if (URLUtil.isValidUrl(bookUrl)) {

            serverUrl = bookUrl;

        } else {

            serverUrl = Constant.ServerInformation.VIDEO_URL + bookUrl;

        }

        Utility.Logger(TAG, "Book Url = " + bookUrl);


        //It check either it's pdf file or Epub
        //Also send file link for downloading

        if (bookUrl.endsWith(".pdf")) {
            extension = ".pdf";

            //Check either it already saved or not
            //In case of already saved , we directly
            //open the file link form 'db'

            tempList.addAll(management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                    .setDbOperation(Constant.DB.SPECIFIC_BOOK_BY_NAME)
                    .setDataObject(new DataObject()
                            .setTitle(bookDetail.getBookName())
                            .setFileType(Constant.DataType.PDF))));

            if (tempList.size() > 0) {

                DataObject dbObject = (DataObject) tempList.get(0);
                Intent intent = new Intent(this, ReadBook.class);
                intent.putExtra(Constant.IntentKey.BOOK_DETAIL, dbObject);
                startActivity(intent);

                return;
            }

        } else if (bookUrl.endsWith(".epub")) {
            extension = ".epub";


            //In case of Epub file , it would open FolioReader
            //We also check out either this position is already read by user or not

            tempList.addAll(management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                    .setDbOperation(Constant.DB.SPECIFIC_BOOK_BY_NAME)
                    .setDataObject(new DataObject()
                            .setTitle(bookDetail.getBookName())
                            .setFileType(Constant.DataType.EPUB))));

            if (tempList.size() > 0) {

                //In case of user already read this book
                //then it would open specified pages at which user left off

                FolioReader folioReader = FolioReader.getInstance(getApplicationContext(),
                        new ReadTotalPagesListener() {
                            @Override
                            public void saveTotalPages(String totalPages) {
                            }
                        });
                folioReader.saveThemeOption(Utility.isNightMode(this));
                folioReader.setReadPositionListener(new ReadPositionListener() {
                    @Override
                    public void saveReadPosition(ReadPosition readPosition) {

                        //It saves the last read position at which user left off
                        //It retrieve data from Db for getting Book id
                        //so that we would update the last read position

                        if (tempList.size() > 0) {

                            //In this part we update the last read position
                            //in db file so that user would start from where
                            //it left the book

                            DataObject dbObject = (DataObject) tempList.get(0);
                            management.getDataFromDatabase(new DatabaseObject()
                                    .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                                    .setDbOperation(Constant.DB.UPDATE)
                                    .setDataObject(new DataObject()
                                            .setId(dbObject.getId())
                                            .setCurrentPage(readPosition.toJson())));

                        }

                    }
                });


                DataObject folioBookData = (DataObject) tempList.get(0);
                Gson gson = new Gson();
                ReadPositionImpl readPosition = gson.fromJson(folioBookData.getCurrentPage(), ReadPositionImpl.class);
                folioReader.setReadPosition(readPosition);
                folioReader.openBook(Uri.parse(folioBookData.getBookUrl()).getPath());

                return;
            }


        }

        management.sendRequestToServer(new RequestObject()
                .setServerUrl(serverUrl)
                .setTitle(bookDetail.getBookName())
                .setArtistName(bookDetail.getBookAuthorName())
                .setCoverUrl(bookDetail.getBookImage())
                .setLoadingText(Utility.getStringFromRes(this, R.string.loading))
                .setShare(false)
                .setRead(true)
                .setFileExtension(extension)
                .setConnectionType(Constant.CONNECTION_TYPE.DOWNLOAD)
                .setConnection(Constant.CONNECTION.DOWNLOAD)
                .setPictureId(bookDetail.getBookName())
                .setConnectionCallback(this));


    }

    @Override
    public void downloadBook() {

        PrefObject pref = management.getPreferences(new PrefObject()
                .setRetrieveDownloadWifi(true));

        if (pref.isDownloadWifi()) {

            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (!mWifi.isConnected()) {
                // Do whatever
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.download_wifi_setting), Toast.LENGTH_SHORT);
                return;
            }

        }


        //It would trigger when we click 'Download' button
        //It would download it in your smartphone

        String serverUrl;
        String fileType = null;

        if (URLUtil.isValidUrl(bookUrl)) {

            serverUrl = bookUrl;

        } else {

            serverUrl = Constant.ServerInformation.VIDEO_URL + bookUrl;

        }

        //Check for checking either it's pdf or Epub

        if (bookUrl.endsWith(".pdf")) {
            extension = ".pdf";
            fileType = Constant.DataType.PDF;
        } else if (bookUrl.endsWith(".epub")) {
            extension = ".epub";
            fileType = Constant.DataType.EPUB;
        }

        management.sendRequestToServer(new RequestObject()
                .setServerUrl(serverUrl)
                .setTitle(bookDetail.getBookName())
                .setArtistName(bookDetail.getBookAuthorName())
                .setCoverUrl(bookDetail.getBookImage())
                .setLoadingText(Utility.getStringFromRes(this, R.string.downloading))
                .setShare(false)
                .setFileExtension(extension)
                .setPostType(fileType)
                .setConnectionType(Constant.CONNECTION_TYPE.DOWNLOAD)
                .setConnection(Constant.CONNECTION.DOWNLOAD)
                .setPictureId(bookDetail.getBookName())
                .setConnectionCallback(this));

    }


    /**
     * <p>Used to show bottom sheet dialog for Adding Review to Book</p>
     */
    private void showReviewSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.add_review_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();


        final SmileRating smileRating = (SmileRating) view.findViewById(R.id.smile_rating);
        final EditText txtReview = (EditText) view.findViewById(R.id.txt_review);
        TextView txtSubmit = (TextView) view.findViewById(R.id.txt_submit);

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.

                txtReview.setVisibility(View.VISIBLE);

            }
        });

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.isEmptyString(txtReview.getText().toString()))
                    return;

                objectArrayList.add(new DataObject()
                        .setReviewPersonName(prefObject.getFirstName() + " " + prefObject.getLastName())
                        .setReviewPersonReview(txtReview.getText().toString())
                        .setReviewPersonPicture(prefObject.getPictureUrl())
                        .setDataType(Constant.DATA_TYPE.REVIEW)
                        .setReviewPersonRating(String.valueOf(smileRating.getRating())));

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();


                management.sendRequestToServer(new RequestObject()
                        .setJson(getJson(prefObject.getUserId(), bookDetail.getBookId(), txtReview.getText().toString(), String.valueOf(smileRating.getRating())))
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setConnection(Constant.CONNECTION.ADD_COMMENT)
                        .setConnectionCallback(Viewer.this));

            }
        });

    }

    /**
     * <p>Used to show bottom sheet dialog for Report Book</p>
     */
    private void showReportSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.add_report_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();


        final EditText txtReview = (EditText) view.findViewById(R.id.txt_review);
        TextView txtSubmit = (TextView) view.findViewById(R.id.txt_submit);

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.isEmptyString(txtReview.getText().toString()))
                    return;


                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();


                management.sendRequestToServer(new RequestObject()
                        .setJson(getReportJson(prefObject.getUserId(), bookDetail.getBookId(), txtReview.getText().toString()))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.REPORT)
                        .setConnectionCallback(Viewer.this));

            }
        });

    }


    /**
     * <p>It is used to build Deeplink for Direct Sharing</p>
     *
     * @param deepLink   the deep link your app will open. This link must be a valid URL and use the
     *                   HTTP or HTTPS scheme.
     * @param minVersion the {@code versionCode} of the minimum version of your app that can open
     *                   the deep link. If the installed app is an older version, the user is taken
     *                   to the Play store to upgrade the app. Pass 0 if you do not
     *                   require a minimum version.
     * @return a {@link Uri} representing a properly formed deep link.
     */

    private void buildDeepLink(@NonNull Uri deepLink, int minVersion) {
        String uriPrefix = Constant.ServerInformation.DEFFERED_DEEP_LINK_URL;

        String doc = Jsoup.parse(bookDetail.getBookDescription()).text();
        final Uri[] uri = {null};

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setDomainUriPrefix(uriPrefix)
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
                        .setMinimumVersion(minVersion)
                        .build())
                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                        .setTitle(bookDetail.getBookName())
                        .setDescription(doc.substring(0, Math.min(doc.length(), 50)))
                        .setImageUrl(Uri.parse(Constant.ServerInformation.PICTURE_URL + bookDetail.getBookImage()))
                        .build())
                .setLink(deepLink)
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {

                        if (task.isSuccessful()) {
                            // Short link created
                            uri[0] = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Utility.Logger(TAG, "ShortLink = " + uri[0].toString() + " flowChartLink = " + flowchartLink.toString());
                            Utility.shareApp(getApplicationContext(), uri[0].toString());

                        } else {
                            Utility.Logger(TAG, "Error = " + task.getException().getMessage());
                        }
                    }
                });

        // [END build_dynamic_link]


    }

}
