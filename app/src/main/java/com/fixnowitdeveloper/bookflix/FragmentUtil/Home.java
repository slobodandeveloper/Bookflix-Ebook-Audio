package com.fixnowitdeveloper.bookflix.FragmentUtil;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fixnowitdeveloper.bookflix.ActivityUtil.AuthorBook;
import com.fixnowitdeveloper.bookflix.ActivityUtil.Base;
import com.fixnowitdeveloper.bookflix.ActivityUtil.Categories;
import com.fixnowitdeveloper.bookflix.ActivityUtil.CategorizedBook;
import com.fixnowitdeveloper.bookflix.ActivityUtil.History;
import com.fixnowitdeveloper.bookflix.ActivityUtil.ListOfAuthor;
import com.fixnowitdeveloper.bookflix.ActivityUtil.ListOfBooks;
import com.fixnowitdeveloper.bookflix.ActivityUtil.Profile;
import com.fixnowitdeveloper.bookflix.ActivityUtil.ReadBook;
import com.fixnowitdeveloper.bookflix.ActivityUtil.Search;
import com.fixnowitdeveloper.bookflix.ActivityUtil.Viewer;
import com.fixnowitdeveloper.bookflix.AdapterUtil.HomeAdapter;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GlideApp;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseObject;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.HomeCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ArtistHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.BookHeaderObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.HomeObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.SearchObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.FileUtils;
import com.fixnowitdeveloper.bookflix.Utility.Utility;
import com.folioreader.FolioReader;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.util.ReadPositionListener;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.ixidev.gdpr.GDPRChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Home extends Fragment implements View.OnClickListener, ConnectionCallback, HomeCallback {
    private TextView txtMenu;
    private ImageView imageMenu;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewHome;
    private HomeAdapter homeAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<DataObject> historyArraylist = new ArrayList<>();
    private String TAG = Home.class.getName();
    private ImageView imageProfile;
    private PrefObject prefObject;
    private String pictureUrl;
    private int ADD_BOOK_REQ_CODE = 101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI
        initAD();
    }


    private void initAD() {

        if (Constant.Credentials.isAdmobBannerAds) {

            LinearLayout mAdView = getActivity().findViewById(R.id.adView);
            mAdView.setVisibility(View.VISIBLE);

            AdView adView = new AdView(getContext());
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
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        imageProfile = view.findViewById(R.id.image_profile);
        imageProfile.setVisibility(View.GONE);

        if (prefObject.isLogin()) {

            if (prefObject.getLoginType().equalsIgnoreCase(Constant.LoginType.NATIVE_LOGIN)) {
                pictureUrl = Constant.ServerInformation.PICTURE_URL + prefObject.getPictureUrl();

            } else if (prefObject.getLoginType().equalsIgnoreCase(Constant.LoginType.GOOGLE_LOGIN)) {
                pictureUrl = prefObject.getPictureUrl();

            } else {
                pictureUrl = prefObject.getPictureUrl() + Constant.ServerInformation.FACEBOOK_HIGH_PIXEL_URL;

            }

            imageProfile.setVisibility(View.VISIBLE);
            GlideApp.with(this).load(pictureUrl)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.profile_picture)
                            .error(R.drawable.profile_picture)
                            .signature(new ObjectKey(System.currentTimeMillis())))
                    .into(imageProfile);

        }

        objectArrayList.add(new ProgressObject());

        gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerViewHome = view.findViewById(R.id.recycler_view_home);
        recyclerViewHome.setLayoutManager(gridLayoutManager);

        homeAdapter = new HomeAdapter(getActivity(), objectArrayList, this) {
            @Override
            public void select(boolean isLocked, int position) {

            }
        };
        recyclerViewHome.setAdapter(homeAdapter);

        //Send request to Server

        management.sendRequestToServer(new RequestObject()
                .setContext(getActivity())
                .setJson(getJson())
                .setConnection(Constant.CONNECTION.HOME)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(this));

        imageProfile.setOnClickListener(this);

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

            jsonObject.accumulate("functionality", "home");
            PrefObject prefObject = management.getPreferences(new PrefObject().setRetrieveNewsfeed(true));
            jsonObject.accumulate("cat_id", prefObject.getNewsfeedId());

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
        if (v == imageMenu) {
            Base.layoutDrawer.openDrawer(Gravity.LEFT);
        }
        if (v == imageProfile) {

            startActivity(new Intent(getActivity(), Profile.class));

        }

    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            if (data instanceof DataObject) {

                objectArrayList.clear();
                DataObject dataObject = (DataObject) data;
                objectArrayList.add(new SearchObject());
                objectArrayList.addAll(dataObject.getHomeList());

                if (historyArraylist.size() > 0)
                    objectArrayList.add(new HomeObject()
                            .setData_type(Constant.DATA_TYPE.HISTORY)
                            .setTitle(Utility.getStringFromRes(getActivity(), R.string.continue_reading))
                            .setDataObjectArrayList(historyArraylist));


                homeAdapter.notifyDataSetChanged();
            }


        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {

        if (!Utility.isEmptyString(data) && requestObject != null) {

            Utility.Logger(TAG, "Error = " + data);

        }

    }

    @Override
    public void onSelect(int parentPosition, int childPosition) {

        HomeObject homeObject = null;
        DataObject dataObject = null;


        if (parentPosition >= 0) {

            homeObject = (HomeObject) objectArrayList.get(parentPosition);
            dataObject = homeObject.getDataObjectArrayList().get(childPosition);

            if (homeObject.getData_type() == Constant.DATA_TYPE.ARTIST) {

                ArtistHeaderObject artistHeaderObject = new ArtistHeaderObject()
                        .setArtistId(dataObject.getId()).setAuthorName(dataObject.getTitle())
                        .setAuthorWork(dataObject.getAuthorWork()).setAuthorDescription(dataObject.getAuthorDescription())
                        .setBookCount(dataObject.getBookCount()).setDownloadCount(dataObject.getDownloadCount())
                        .setReviewCount(dataObject.getReviewCount()).setAuthorPicture(dataObject.getOriginalUrl());

                Intent intent = new Intent(getActivity(), AuthorBook.class);
                intent.putExtra(Constant.IntentKey.ARTIST_DETAIL, artistHeaderObject);
                startActivity(intent);

            } else if (homeObject.getData_type() == Constant.DATA_TYPE.CATEGORIES) {

                Intent intent = new Intent(getActivity(), CategorizedBook.class);
                intent.putExtra(Constant.IntentKey.CATEGORY, dataObject.getCategoryTitle());
                intent.putExtra(Constant.IntentKey.CATEGORY_ID, dataObject.getId());
                startActivity(intent);

            } else if (homeObject.getData_type() == Constant.DATA_TYPE.HISTORY) {

                if (dataObject.getFileType().equalsIgnoreCase(Constant.DataType.PDF)) {

                    Intent intent = new Intent(getActivity(), ReadBook.class);
                    intent.putExtra(Constant.IntentKey.BOOK_DETAIL, dataObject);
                    startActivity(intent);

                } else if (dataObject.getFileType().equalsIgnoreCase(Constant.DataType.EPUB)) {


                    FolioReader folioReader = FolioReader.getInstance(getActivity(), null);
                    folioReader.saveThemeOption(Utility.isNightMode(getActivity()));
                    final DataObject finalDataObject = dataObject;
                    folioReader.setReadPositionListener(new ReadPositionListener() {
                        @Override
                        public void saveReadPosition(ReadPosition readPosition) {

                            //In this part we update the last read position
                            //in db file so that user would start from where
                            //it left the book

                            management.getDataFromDatabase(new DatabaseObject()
                                    .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                                    .setDbOperation(Constant.DB.UPDATE)
                                    .setDataObject(new DataObject()
                                            .setId(finalDataObject.getId())
                                            .setCurrentPage(readPosition.toJson())));


                        }
                    });

                    if (!Utility.isEmptyString(dataObject.getCurrentPage())) {

                        Gson gson = new Gson();
                        ReadPositionImpl readPosition = gson.fromJson(dataObject.getCurrentPage(), ReadPositionImpl.class);
                        folioReader.setReadPosition(readPosition);
                    }

                    String filePath = new File(Uri.parse(dataObject.getBookUrl()).getPath()).getAbsolutePath();
                    Utility.Logger(TAG, "Epub File Path = " + filePath);
                    folioReader.openBook(filePath);


                }

            } else {

                BookHeaderObject bookHeaderObject = new BookHeaderObject()
                        .setBookId(dataObject.getId()).setBookName(dataObject.getTitle())
                        .setBookDescription(dataObject.getDescription()).setBookAuthorName(dataObject.getArtistName())
                        .setBookDownloadCount(dataObject.getDownloads()).setBookReviewCount(dataObject.getComments())
                        .setBookTag(dataObject.getTags()).setBookRating(dataObject.getRating())
                        .setBookImage(dataObject.getOriginalUrl()).setBookUrl(dataObject.getBookUrl());

                Intent intent = new Intent(getActivity(), Viewer.class);
                intent.putExtra(Constant.IntentKey.BOOK_DETAIL, bookHeaderObject);
                startActivity(intent);

            }

        } else {

            dataObject = (DataObject) objectArrayList.get(childPosition);

            BookHeaderObject bookHeaderObject = new BookHeaderObject()
                    .setBookId(dataObject.getId()).setBookName(dataObject.getTitle())
                    .setBookDescription(dataObject.getDescription()).setBookAuthorName(dataObject.getArtistName())
                    .setBookDownloadCount(dataObject.getDownloads()).setBookReviewCount(dataObject.getComments())
                    .setBookTag(dataObject.getTags()).setBookRating(dataObject.getRating())
                    .setBookImage(dataObject.getOriginalUrl()).setBookUrl(dataObject.getBookUrl());

            Intent intent = new Intent(getActivity(), Viewer.class);
            intent.putExtra(Constant.IntentKey.BOOK_DETAIL, bookHeaderObject);
            startActivity(intent);

        }

        Utility.Logger(TAG, "Data = " + dataObject.toString());

    }

    @Override
    public void onSelectSearch() {
        Utility.Logger(TAG, "OnSearch Clicking");
        startActivity(new Intent(getActivity(), Search.class));
    }

    @Override
    public void onAddBook() {
        Utility.Logger(TAG, "Add Book Clicking");
        //startActivity(new Intent(getActivity(), Search.class));
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        String[] extraMimeTypes = {"application/pdf", "application/doc", "application/EPUB", "application/DOCX", "application/RTF",
                "application/MOBI", "application/AZW3", "application/DJVU", "application/FB2", "application/TXT", "application/ODT", "application/CHM"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, ADD_BOOK_REQ_CODE);
    }

    @Override
    public void onMore(Constant.DATA_TYPE dataType) {

        if (dataType == Constant.DATA_TYPE.POPULAR) {

            Intent intent = new Intent(getActivity(), ListOfBooks.class);
            intent.putExtra(Constant.IntentKey.PLAYLIST_NAME, Utility.getStringFromRes(getActivity(), R.string.popular));
            intent.putExtra(Constant.IntentKey.CONNECTION, Constant.CONNECTION.POPULAR.name());
            startActivity(intent);

        } else if (dataType == Constant.DATA_TYPE.CATEGORIES) {

            startActivity(new Intent(getActivity(), Categories.class));

        } else if (dataType == Constant.DATA_TYPE.FEED) {

            Intent intent = new Intent(getActivity(), ListOfBooks.class);
            intent.putExtra(Constant.IntentKey.PLAYLIST_NAME, Utility.getStringFromRes(getActivity(), R.string.personalized));
            intent.putExtra(Constant.IntentKey.CONNECTION, Constant.CONNECTION.NEWS_FEED.name());
            startActivity(intent);

        } else if (dataType == Constant.DATA_TYPE.ARTIST) {

            startActivity(new Intent(getActivity(), ListOfAuthor.class));

        } else if (dataType == Constant.DATA_TYPE.HISTORY) {

            startActivity(new Intent(getActivity(), History.class));

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Object> history = new ArrayList<>();
        history.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                .setDbOperation(Constant.DB.RETRIEVE)
                .setDataObject(new DataObject())));

        historyArraylist.clear();
        int historySize = history.size() > 6 ? 6 : history.size();
        for (int i = 0; i < historySize; i++) {

            DataObject dtObject = (DataObject) history.get(i);
            historyArraylist.add(dtObject);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  Handle activity result here

        String s = "";
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_BOOK_REQ_CODE) {
                ClipData clipData = data.getClipData();

                //Both approach work

                if (clipData == null) {
                    s += data.getData().toString();
                } else {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri uri = item.getUri();
                        s += uri.toString() + "\n";
                    }
                }

                /*
                if(data.getData() == null){
                    s = "data.getData() == null\n";
                    for(int i=0; i<clipData.getItemCount(); i++){
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri uri = item.getUri();
                        s += uri.toString() + "\n";
                    }
                }else{
                    s = "data.getData() != null\n";
                    s += data.getData().toString();
                }
                */
                Uri uri = data.getData();

                String path = FileUtils.getPath(uri);
                File file = new File(uri.getPath());
                String fileExt = MimeTypeMap.getFileExtensionFromUrl(file.toString());
                DataObject dataObject = new DataObject();
                dataObject.setTitle(file.getName());
                dataObject.setType(fileExt);
                int totalPages = 0;
                try {
                    totalPages = countPages(file);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }

                management = new Management(getActivity());
                management.getDataFromDatabase(new DatabaseObject()
                        .setTypeOperation(Constant.TYPE.MY_ADDED_BOOKS_STATUS)
                        .setDbOperation(Constant.DB.INSERT)
                        .setDataObject(new DataObject()
                                .setTitle(file.getName())
                                .setFileType(Constant.DataType.PDF)
                                .setBookUrl(uri + "")
                                .setCoverUrl("")
                                .setBookPage(String.valueOf(totalPages))
                                .setCurrentPage(String.valueOf(0))));


            }
        }
       // Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
    private int countPages(File pdfFile) throws IOException {
        int totalpages=0;
        try {

            ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
            PdfRenderer pdfRenderer = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                pdfRenderer = new PdfRenderer(parcelFileDescriptor);
                totalpages = pdfRenderer.getPageCount();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG,e.getMessage(),e);
        }
        return totalpages;
    }
}
