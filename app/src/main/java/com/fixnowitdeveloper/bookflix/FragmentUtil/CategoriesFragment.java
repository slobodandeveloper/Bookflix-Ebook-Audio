package com.fixnowitdeveloper.bookflix.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.fixnowitdeveloper.bookflix.ActivityUtil.CategorizedBook;
import com.fixnowitdeveloper.bookflix.AdapterUtil.CategoriesAdapter;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GridSpacingItemDecoration;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BarObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.CategoryObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.InternetObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment implements ConnectionCallback, View.OnClickListener {
    private String TAG = CategoriesFragment.class.getName();
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewCategories;
    private CategoriesAdapter categoriesAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private TextView txtMenu;
    private ImageView imageBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_categories, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initUI(view); //Initialize UI
        initAD(view); //Initialize Admob Banner Ads

    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.categories));

        management = new Management(getActivity());


        objectArrayList.add(new ProgressObject());

        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
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
                } else {
                    return 1;
                }
            }
        });

        recyclerViewCategories = (RecyclerView) view.findViewById(R.id.recycler_view_categories);
        recyclerViewCategories.setLayoutManager(gridLayoutManager);

        int spanCount = 3; // 3 columns
        int spacing = 15; // 50px
        boolean includeEdge = true;
        recyclerViewCategories.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        //Initialize & Setup Adapter with Recycler View

        categoriesAdapter = new CategoriesAdapter(getActivity(), objectArrayList) {
            @Override
            public void selectCategory(int position) {
                CategoryObject categoryObject = (CategoryObject) objectArrayList.get(position);

                Intent intent = new Intent(getActivity(), CategorizedBook.class);
                intent.putExtra(Constant.IntentKey.CATEGORY, categoryObject.getTitle());
                intent.putExtra(Constant.IntentKey.CATEGORY_ID, categoryObject.getId());
                startActivity(intent);

            }
        };
        recyclerViewCategories.setAdapter(categoriesAdapter);


        //Send request to Server for retrieving TrendingPhotos Wallpapers

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.ALL_CATEGORIES)
                .setConnectionCallback(this));


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
    private void initAD(View view) {

        if (Constant.Credentials.isAdmobBannerAds) {

            LinearLayout mAdView = view.findViewById(R.id.adView);
            mAdView.setVisibility(View.VISIBLE);

            AdView adView = new AdView(getActivity());
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


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            objectArrayList.clear();

            if (requestObject.getConnection() == Constant.CONNECTION.ALL_CATEGORIES) {

                DataObject dataObject = (DataObject) data;

                for (int i = 0; i < dataObject.getWallpaperList().size(); i++) {

                    DataObject category = dataObject.getWallpaperList().get(i);
                    objectArrayList.add(new CategoryObject()
                            .setId(category.getId())
                            .setTitle(category.getCategoryTitle())
                            .setPicture(category.getCategoryPicture()));

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

    }
}
