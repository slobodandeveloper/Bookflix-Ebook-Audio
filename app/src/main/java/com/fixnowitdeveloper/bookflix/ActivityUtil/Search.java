package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.fixnowitdeveloper.bookflix.AdapterUtil.SearchAdapter;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ArtistHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BookHeaderObject;
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

public class Search extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, TextWatcher, ConnectionCallback {
    private String TAG = Search.class.getName();
    private ImageView imageBack;
    private EditText editSearch;
    private ImageView imageClose;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewSearch;
    private SearchAdapter searchAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private String nextPageUrl;
    private int totalItemCount;
    private int lastVisibleItem;
    private boolean loading = false;
    private int visibleThreshold = 5;
    private ImageView imageFilter;
    private String type = "book";
    private TextView txtMenu;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initUI(); //Initialize UI
        initAD(); //Initialize Admob Banner Ads


    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        management = new Management(this);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.search));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageFilter = findViewById(R.id.image_share);
        imageFilter.setVisibility(View.VISIBLE);
        imageFilter.setImageResource(R.drawable.ic_filter);

        editSearch = (EditText) findViewById(R.id.edit_search);
        imageClose = (ImageView) findViewById(R.id.image_close);


        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(this, R.string.search_title))
                .setDescription(Utility.getStringFromRes(this, R.string.search_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_search));


        //Initialize Layout Manager & setup with Recycler View

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
                } else if (objectArrayList.get(position) instanceof DataObject) {
                    DataObject dataObject = (DataObject) objectArrayList.get(position);

                    if (dataObject.getDataType() == Constant.DATA_TYPE.POPULAR) {
                        return 1;
                    } else {


                        Utility.Logger(TAG, "Data Type = " + dataObject.getDataType().name());

                        return 1;
                    }

                } else {
                    return 2;
                }
            }
        });

        recyclerViewSearch = (RecyclerView) findViewById(R.id.recycler_view_search);
        recyclerViewSearch.setLayoutManager(gridLayoutManager);

        //Setup Recycler View Adapter with Adapter

        searchAdapter = new SearchAdapter(this, objectArrayList) {
            @Override
            public void select(boolean isLocked, int position) {

                DataObject dataObject = (DataObject) objectArrayList.get(position);

                if (type.equalsIgnoreCase("book")) {

                    BookHeaderObject bookHeaderObject = new BookHeaderObject()
                            .setBookId(dataObject.getId()).setBookName(dataObject.getTitle())
                            .setBookDescription(dataObject.getDescription()).setBookAuthorName(dataObject.getArtistName())
                            .setBookDownloadCount(dataObject.getDownloads()).setBookReviewCount(dataObject.getComments())
                            .setBookTag(dataObject.getTags()).setBookRating(dataObject.getRating())
                            .setBookImage(dataObject.getOriginalUrl()).setBookUrl(dataObject.getBookUrl());

                    Intent intent = new Intent(getApplicationContext(), Viewer.class);
                    intent.putExtra(Constant.IntentKey.BOOK_DETAIL, bookHeaderObject);
                    startActivity(intent);

                } else if (type.equalsIgnoreCase("author")) {

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
        recyclerViewSearch.setAdapter(searchAdapter);


        imageBack.setOnClickListener(this);
        imageFilter.setOnClickListener(this);
        imageClose.setOnClickListener(this);
        //imageFilter.setOnClickListener(this);
        editSearch.addTextChangedListener(this);
        editSearch.setOnEditorActionListener(this);
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
     * <p>It trigger by Search & find required search terms</p>
     */
    private void performSearch(String searchText) {

        Constant.CONNECTION connection = null;
        Utility.Logger(TAG, "Search : " + searchText);

        if (!Utility.isEmptyString(editSearch.getText().toString())) {

            //For showing Progressing Animation

            Utility.hideKeyboard(this, editSearch);

            //Send Request to Server regarding specific Category

            if (type.equalsIgnoreCase("book")) {
                connection = Constant.CONNECTION.SEARCH;
            } else if (type.equalsIgnoreCase("author")) {
                connection = Constant.CONNECTION.ALL_ARTIST;
            }

            management.sendRequestToServer(new RequestObject()
                    .setJson(getJson())
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(connection)
                    .setConnectionCallback(this));

            objectArrayList.clear();
            objectArrayList.add(new ProgressObject());
            searchAdapter.notifyDataSetChanged();

        }

    }


    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getJson() {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "search");
            jsonObject.accumulate("search_type", type);
            jsonObject.accumulate("search_item", editSearch.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);
        return json;

    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }
        if (v == imageClose) {

            imageClose.setVisibility(View.GONE);
            objectArrayList.clear();
            editSearch.setText(null);

            objectArrayList.add(new EmptyObject()
                    .setTitle(Utility.getStringFromRes(this, R.string.search_title))
                    .setDescription(Utility.getStringFromRes(this, R.string.search_tagline))
                    .setPlaceHolderIcon(R.drawable.em_no_search));

            searchAdapter.notifyDataSetChanged();

        }
        if (v == imageFilter) {
            showFilterBottomSheet(this);
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            performSearch(v.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!Utility.isEmptyString(s.toString())) {
            imageClose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null) {

            objectArrayList.clear();
            DataObject dataObject = (DataObject) data;
            objectArrayList.addAll(dataObject.getWallpaperList());


            searchAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Error = " + data);
        objectArrayList.clear();

        if (type.equalsIgnoreCase("book")) {

            objectArrayList.add(new EmptyObject()
                    .setTitle(Utility.getStringFromRes(this, R.string.no_book))
                    .setDescription(Utility.getStringFromRes(this, R.string.no_book_tagline))
                    .setPlaceHolderIcon(R.drawable.em_no_book));

        } else if (type.equalsIgnoreCase("author")) {

            objectArrayList.add(new EmptyObject()
                    .setTitle(Utility.getStringFromRes(this, R.string.no_artist))
                    .setDescription(Utility.getStringFromRes(this, R.string.no_artist_tagline))
                    .setPlaceHolderIcon(R.drawable.em_no_author));

        }

        searchAdapter.notifyDataSetChanged();
    }


    /**
     * <p>Used to show bottom sheet dialog for selection of Wallpaper</p>
     */
    private void showFilterBottomSheet(final Context context) {

        final String[] selection = new String[1];
        View view = getLayoutInflater().inflate(R.layout.filter_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        RelativeLayout layoutBook = (RelativeLayout) view.findViewById(R.id.layout_book);
        final ImageView imageBookSelected = (ImageView) view.findViewById(R.id.image_book_selected);

        RelativeLayout layoutAuthor = (RelativeLayout) view.findViewById(R.id.layout_author);
        final ImageView imageAuthorSelected = (ImageView) view.findViewById(R.id.image_author_selected);

        layoutBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageBookSelected.setVisibility(View.VISIBLE);
                imageAuthorSelected.setVisibility(View.GONE);
                selection[0] = "book";

            }
        });

        layoutAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageAuthorSelected.setVisibility(View.VISIBLE);
                imageBookSelected.setVisibility(View.GONE);
                selection[0] = "author";

            }
        });

        TextView txtSubmit = (TextView) view.findViewById(R.id.txt_submit);
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.isEmptyString(selection[0])) {
                    return;
                }

                type = selection[0];

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

            }
        });

    }

}
