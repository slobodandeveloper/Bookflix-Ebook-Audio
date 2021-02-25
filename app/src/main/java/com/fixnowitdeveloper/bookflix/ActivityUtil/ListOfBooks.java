package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.fixnowitdeveloper.bookflix.AdapterUtil.BookAdapter;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GridSpacingItemDecoration;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BarObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BookHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.InternetObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.NativeAdObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfBooks extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private String TAG = ListOfBooks.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewCategories;
    private BookAdapter categoriesAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private String menuName;
    private Constant.CONNECTION connection;
    private ImageView imageStyle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorized_status);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI
        initAD();

    }

    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {

        menuName = getIntent().getStringExtra(Constant.IntentKey.PLAYLIST_NAME);
        String connectType = getIntent().getStringExtra(Constant.IntentKey.CONNECTION);
        if (!Utility.isEmptyString(connectType)) {
            connection = Constant.CONNECTION.valueOf(connectType);
        }

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        Utility.Logger(TAG, "Setting = Category " + menuName + " Connection = " + connection.name());

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(menuName);

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageStyle = findViewById(R.id.image_share);
        imageStyle.setVisibility(View.VISIBLE);
        imageStyle.setTag(0);
        imageStyle.setImageResource(R.drawable.ic_list);

        management = new Management(this);
        objectArrayList.add(new ProgressObject());

        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 3;
                } else if (objectArrayList.get(position) instanceof InternetObject) {
                    return 3;
                } else if (objectArrayList.get(position) instanceof ProgressObject) {
                    return 3;
                } else if (objectArrayList.get(position) instanceof BarObject) {
                    return 3;
                } else if (objectArrayList.get(position) instanceof DataObject) {

                    DataObject dataObject = (DataObject) objectArrayList.get(position);
                    if (dataObject.isList()) {
                        return 3;
                    } else {
                        return 1;
                    }

                } else {
                    return 1;
                }
            }
        });

        recyclerViewCategories = (RecyclerView) findViewById(R.id.recycler_view_categories);
        recyclerViewCategories.setLayoutManager(gridLayoutManager);

        int spanCount = 3; // 3 columns
        int spacing = 15; // 50px
        boolean includeEdge = true;
        recyclerViewCategories.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        //Initialize & Setup Adapter with Recycler View

        categoriesAdapter = new BookAdapter(this, objectArrayList) {
            @Override
            public void select(boolean isLocked, int position) {

                if (!isLocked) {

                    DataObject dataObject = (DataObject) objectArrayList.get(position);
                    BookHeaderObject bookHeaderObject = new BookHeaderObject()
                            .setBookId(dataObject.getId()).setBookName(dataObject.getTitle())
                            .setBookDescription(dataObject.getDescription()).setBookAuthorName(dataObject.getArtistName())
                            .setBookDownloadCount(dataObject.getDownloads()).setBookReviewCount(dataObject.getComments())
                            .setBookTag(dataObject.getTags()).setBookRating(dataObject.getRating())
                            .setBookImage(dataObject.getOriginalUrl()).setBookUrl(dataObject.getBookUrl());

                    Intent intent = new Intent(getApplicationContext(), Viewer.class);
                    intent.putExtra(Constant.IntentKey.BOOK_DETAIL, bookHeaderObject);
                    startActivity(intent);

                }

            }
        };
        recyclerViewCategories.setAdapter(categoriesAdapter);


        //Send request to Server for retrieving TrendingPhotos Wallpapers

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(connection)
                .setConnectionCallback(this));


        imageBack.setOnClickListener(this);
        imageStyle.setOnClickListener(this);

    }


    /**
     * <p>It initialize the Admob Banner Ad</p>
     */
    private void initAD() {

        if (Constant.Credentials.isAdmobBannerAds) {

            LinearLayout mAdView = findViewById(R.id.adView);
            mAdView.setVisibility(View.VISIBLE);

            AdView adView = new AdView(this);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(Constant.Credentials.ADMOB_BANNER_ID);

            AdRequest.Builder adRequest = new AdRequest.Builder().addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID);

            GDPRChecker.Request request = GDPRChecker.getRequest();
            if (request == GDPRChecker.Request.NON_PERSONALIZED) {
                // load non Personalized ads
                Bundle extras = new Bundle();
                extras.putString("npa", "1");
                adRequest.addNetworkExtrasBundle(AdMobAdapter.class, extras);
            } // else do nothing , it will load PERSONALIZED ads

            adView.loadAd(adRequest.build());
            mAdView.addView(adView);

        }

    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson() {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (connection == Constant.CONNECTION.POPULAR)
                jsonObject.accumulate("functionality", "popular");
            else if (connection == Constant.CONNECTION.NEWS_FEED) {

                jsonObject.accumulate("functionality", "newsfeed");
                PrefObject prefObject = management.getPreferences(new PrefObject().setRetrieveNewsfeed(true));
                jsonObject.accumulate("cat_id", prefObject.getNewsfeedId());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p>It is used to Change layout style dynamically</p>
     */
    private void changeStyle(boolean isList) {

        ArrayList<Object> tempArraylist = new ArrayList<>(objectArrayList);
        objectArrayList.clear();

        for (int i = 0; i < tempArraylist.size(); i++) {

            if (tempArraylist.get(i) instanceof DataObject) {

                DataObject dataObject = (DataObject) tempArraylist.get(i);
                if (isList)
                    dataObject.setList(true);
                else
                    dataObject.setList(false);

                objectArrayList.add(dataObject);

            }
        }
        if (objectArrayList.size() > 0)
            categoriesAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }
        if (v == imageStyle) {

            int tag = (int) imageStyle.getTag();
            if (tag == 0) {

                imageStyle.setImageResource(R.drawable.ic_grid);
                imageStyle.setTag(1);
                changeStyle(false);

            } else if (tag == 1) {

                imageStyle.setImageResource(R.drawable.ic_list);
                imageStyle.setTag(0);
                changeStyle(true);

            }

        }
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (requestObject.getConnection() == Constant.CONNECTION.POPULAR
                || requestObject.getConnection() == Constant.CONNECTION.NEWS_FEED) {

            objectArrayList.clear();
            DataObject dataObject = (DataObject) data;

            for (int i = 0; i < dataObject.getWallpaperList().size(); i++) {

                if (i != 0 && 0 == i % Constant.Credentials.nativeAdInterval && Constant.Credentials.isFbNativeAds)
                    objectArrayList.add(new NativeAdObject());

                /*if (Utility.isLocked(i)) {
                    objectArrayList.add(dataObject.getWallpaperList().get(i).setLocked(true));
                } else {*/
                objectArrayList.add(dataObject.getWallpaperList().get(i));
                //}

            }

            categoriesAdapter.notifyDataSetChanged();


        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Error = " + data);
        objectArrayList.clear();
        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(this, R.string.no_book))
                .setDescription(Utility.getStringFromRes(this, R.string.no_book_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_book));
        categoriesAdapter.notifyDataSetChanged();
    }
}
