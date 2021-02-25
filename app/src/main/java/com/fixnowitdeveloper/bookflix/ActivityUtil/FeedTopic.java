package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.fixnowitdeveloper.bookflix.AdapterUtil.CategoriesAdapter;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GridSpacingItemDecoration;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BarObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.CategoryObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.FeedHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.InternetObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ShelfObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedTopic extends AppCompatActivity implements ConnectionCallback, View.OnClickListener {
    private String TAG = FeedTopic.class.getName();
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewCategories;
    private CategoriesAdapter categoriesAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private TextView txtMenu;
    private ImageView imageBack;
    private TextView txtContinue;
    private String categoryId;
    private boolean isContinueRequried;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI
        //initAD(); //Initialize Admob Banner Ads

    }

    /**
     * <p>It is used to get Intent Data</p>
     */
    private void getIntentData() {
        isContinueRequried = getIntent().getBooleanExtra(Constant.IntentKey.CONTINUE_REQUIRED, true);
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtContinue = findViewById(R.id.txt_continue);

        management = new Management(this);

        objectArrayList.clear();
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
                } else if (objectArrayList.get(position) instanceof FeedHeaderObject) {
                    return 3;
                } else if (objectArrayList.get(position) instanceof ShelfObject) {
                    return 3;
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

        categoriesAdapter = new CategoriesAdapter(this, objectArrayList) {
            @Override
            public void selectCategory(int position) {

                ArrayList<Object> tempArraylist = new ArrayList<>(objectArrayList);
                objectArrayList.clear();

                for (int i = 0; i < tempArraylist.size(); i++) {

                    CategoryObject categoryObject = (CategoryObject) tempArraylist.get(i);
                    if (i != position)
                        categoryObject.setSelected(false);
                    else {
                        categoryObject.setSelected(true);
                        categoryId = categoryObject.getId();
                    }
                    objectArrayList.add(categoryObject);
                }

                categoriesAdapter.notifyDataSetChanged();

            }
        };
        recyclerViewCategories.setAdapter(categoriesAdapter);


        //Send request to Server for retrieving TrendingPhotos Wallpapers

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.ALL_CATEGORIES)
                .setConnectionCallback(this));


        //imageBack.setOnClickListener(this);
        txtContinue.setOnClickListener(this);

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

            jsonObject.accumulate("functionality", "all_categories");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
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

            AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID).build();
            adView.loadAd(adRequest);
            mAdView.addView(adView);

        }

    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            objectArrayList.clear();

            if (requestObject.getConnection() == Constant.CONNECTION.ALL_CATEGORIES) {

                //objectArrayList.add(new FeedHeaderObject());
                DataObject dataObject = (DataObject) data;

                for (int i = 0; i < dataObject.getWallpaperList().size(); i++) {

                    DataObject category = dataObject.getWallpaperList().get(i);
                    objectArrayList.add(new CategoryObject()
                            .setRound(false)
                            .setSelected(false)
                            .setId(category.getId())
                            .setTitle(category.getCategoryTitle())
                            .setPicture(category.getCategoryPicture()));

                    /*if (i > 1 && (i+1) % 3 == 0) {
                        objectArrayList.add(new ShelfObject());
                    }*/


                }

            }

            categoriesAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Setting = " + data);
        objectArrayList.clear();
        categoriesAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        if (v == txtContinue) {

            if (Utility.isEmptyString(categoryId))
                return;

            management.savePreferences(new PrefObject()
                    .setSaveNewsfeed(true).setNewsfeedId(categoryId));

            management.savePreferences(new PrefObject()
                    .setSaveFirstLaunch(true).setFirstLaunch(false));

            if (isContinueRequried)
                startActivity(new Intent(this, Base.class));

            finish();

        }
        /*if (v == imageBack) {
            finish();
        }*/
    }
}
