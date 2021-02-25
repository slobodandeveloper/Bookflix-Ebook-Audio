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
import com.fixnowitdeveloper.bookflix.AdapterUtil.ArtistAdapter;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GridSpacingItemDecoration;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ArtistHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BarObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.InternetObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.NativeAdObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfAuthor extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private String TAG = ListOfAuthor.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewCategories;
    private ArtistAdapter categoriesAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private String menuName;
    private Constant.CONNECTION connection;
    private ImageView imageStyle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorized_status);

        initUI(); //Initialize UI
        initAD();

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {


        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.list_of_author));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageStyle = findViewById(R.id.image_share);
        imageStyle.setVisibility(View.GONE);
        imageStyle.setTag(0);
        imageStyle.setImageResource(R.drawable.ic_list);

        objectArrayList.add(new ProgressObject());

        management = new Management(this);


        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 2;
                } else if (objectArrayList.get(position) instanceof InternetObject) {
                    return 2;
                } else if (objectArrayList.get(position) instanceof ProgressObject) {
                    return 2;
                } else if (objectArrayList.get(position) instanceof BarObject) {
                    return 2;
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

        categoriesAdapter = new ArtistAdapter(this, objectArrayList) {
            @Override
            public void select(boolean isLocked, int position) {

                if (!isLocked) {

                    DataObject dataObject = (DataObject) objectArrayList.get(position);
                    ArtistHeaderObject artistHeaderObject = new ArtistHeaderObject()
                            .setArtistId(dataObject.getId()).setAuthorName(dataObject.getTitle())
                            .setAuthorWork(dataObject.getAuthorWork()).setAuthorDescription(dataObject.getAuthorDescription())
                            .setBookCount(dataObject.getBookCount()).setDownloadCount(dataObject.getDownloadCount())
                            .setReviewCount(dataObject.getReviewCount()).setAuthorPicture(dataObject.getOriginalUrl());

                    Intent intent = new Intent(getApplicationContext(), AuthorBook.class);
                    intent.putExtra(Constant.IntentKey.ARTIST_DETAIL, artistHeaderObject);
                    startActivity(intent);

                }

            }
        };
        recyclerViewCategories.setAdapter(categoriesAdapter);


        //Send request to Server for retrieving TrendingPhotos Wallpapers

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.ALL_ARTIST)
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


            jsonObject.accumulate("functionality", "all_artist");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }

    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (requestObject.getConnection() == Constant.CONNECTION.ALL_ARTIST) {

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
        Utility.Logger(TAG, "Setting = " + data);
        objectArrayList.clear();
        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(this, R.string.no_artist))
                .setDescription(Utility.getStringFromRes(this, R.string.no_artist_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_author));
        categoriesAdapter.notifyDataSetChanged();
    }
}
