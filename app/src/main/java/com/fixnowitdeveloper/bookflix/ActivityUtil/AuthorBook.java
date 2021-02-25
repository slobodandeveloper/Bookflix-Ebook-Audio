package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.fixnowitdeveloper.bookflix.AdapterUtil.ArtistAdapter;
import com.fixnowitdeveloper.bookflix.BuildConfig;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ArtistCallback;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.AdObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ArtistHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.AuthorObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BarObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BookHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.HomeObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.InternetObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.NativeAdObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;

public class AuthorBook extends AppCompatActivity implements View.OnClickListener, ConnectionCallback, ArtistCallback {
    private String TAG = AuthorBook.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewCategories;
    private ArtistAdapter categoriesAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArtistHeaderObject artistHeaderObject;
    private boolean isAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorized_status);

        getIntentData(); //Retrieve Intent Data
        initUI();       //Initialize UI
        initAD();       //Initialize Admob Banner Ads

    }

    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {

        artistHeaderObject = getIntent().getParcelableExtra(Constant.IntentKey.ARTIST_DETAIL);
        isAction = getIntent().getBooleanExtra(Constant.IntentKey.BACK_ACTION, false);

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        Utility.Logger(TAG, "Setting = AuthorFragment Name " + artistHeaderObject.getAuthorName()
                + " AuthorFragment Id " + artistHeaderObject.getArtistId());

        objectArrayList.clear();
        objectArrayList.add(new ProgressObject());

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.author_detail));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);


        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof InternetObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof ProgressObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof BarObject) {
                    return 1;
                } else {
                    return 1;
                }
            }
        });

        recyclerViewCategories = (RecyclerView) findViewById(R.id.recycler_view_categories);
        recyclerViewCategories.setLayoutManager(gridLayoutManager);

        //Initialize & Setup Adapter with Recycler View

        categoriesAdapter = new ArtistAdapter(this, objectArrayList, this) {
            @Override
            public void select(boolean isLocked, int position) {

                if (!isLocked) {


                }

            }
        };
        recyclerViewCategories.setAdapter(categoriesAdapter);


        //Send request to Server for retrieving TrendingPhotos Wallpapers

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.ARTIST_DETAIL)
                .setConnectionCallback(this));

        imageBack.setOnClickListener(this);

    }


    /**
     * <p>It initialize the Admob Banner Ad</p>
     */
    private void initAD() {

        if (Constant.Credentials.isAdmobBannerAds) {

            LinearLayout mAdView = findViewById(R.id.adView);
         //   mAdView.setVisibility(View.VISIBLE);

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

            jsonObject.accumulate("functionality", "specific_artist_detail");
            jsonObject.accumulate("artist_id", artistHeaderObject.getArtistId());

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

            if (isAction) {
                startActivity(new Intent(getApplicationContext(), Base.class));
            }

            finish();
        }
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {


            DataObject dataObject = (DataObject) data;
            objectArrayList.clear();
            objectArrayList.add(artistHeaderObject);

            if (Constant.Credentials.isAdmobNativeAuthorDetail && Constant.Credentials.isAdmobBannerAds)
                objectArrayList.add(new AdObject());

            for (int i = 0; i < dataObject.getHomeList().size(); i++) {

                if (dataObject.getHomeList().get(i) instanceof HomeObject) {

                    if (((HomeObject) dataObject.getHomeList().get(i)).getData_type() == Constant.DATA_TYPE.COMMON) {

                        //Placing Admob Ad Banner between Content

                        if (Constant.Credentials.isAdmobNativeAuthorDetail && Constant.Credentials.isAdmobBannerAds)
                            objectArrayList.add(new AdObject());

                    }

                }

                objectArrayList.add(dataObject.getHomeList().get(i));

            }

            categoriesAdapter.notifyDataSetChanged();

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
                .setTitle(Utility.getStringFromRes(this, R.string.no_artist))
                .setDescription(Utility.getStringFromRes(this, R.string.no_artist_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_book));

        categoriesAdapter.notifyDataSetChanged();


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
        intent.putExtra(Constant.IntentKey.BACK_ACTION, isAction);
        startActivity(intent);

    }

    @Override
    public void onShare(AuthorObject authorObject) {
        if (authorObject != null) {

            String authorDetail = String.format(Utility.getStringFromRes(this, R.string.author_detail_format)
                    , authorObject.getAuthorName(), authorObject.getAuthorBooks(), authorObject.getAuthorOverallRating()
                    , authorObject.getAuthorBio());

            int minVersion = BuildConfig.VERSION_CODE;
            Uri deepLinkUri = Uri.parse(String.format(Constant.ServerInformation.DESKTOP_NEW_LINKS
                    , artistHeaderObject.getArtistId()
                    , Constant.PostType.AUTHOR_TYPE
                    , Utility.getAppPlaystoreUrl(this)));

            buildDeepLink(deepLinkUri, minVersion);


            //Utility.shareApp(this, authorDetail);

            Utility.Logger(TAG, "Author Detail = " + authorDetail);
        }
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

    public void buildDeepLink(@NonNull Uri deepLink, int minVersion) {
        String uriPrefix = Constant.ServerInformation.DEFFERED_DEEP_LINK_URL;

        String doc = Jsoup.parse(artistHeaderObject.getAuthorDescription()).text();
        final Uri[] uri = {null};

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setDomainUriPrefix(uriPrefix)
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
                        .setMinimumVersion(minVersion)
                        .build())
                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                        .setTitle(artistHeaderObject.getAuthorName())
                        .setDescription(doc.substring(0, Math.min(doc.length(), 50)))
                        .setImageUrl(Uri.parse(Constant.ServerInformation.PICTURE_URL + artistHeaderObject.getAuthorPicture()))
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
