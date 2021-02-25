package com.fixnowitdeveloper.bookflix.FragmentUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.folioreader.FolioReader;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.util.ReadPositionListener;
import com.folioreader.util.ReadTotalPagesListener;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.fixnowitdeveloper.bookflix.ActivityUtil.ReadBook;
import com.fixnowitdeveloper.bookflix.AdapterUtil.DownloadAdapter;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GridSpacingItemDecoration;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseObject;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.InternetObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import java.io.File;
import java.util.ArrayList;

public class DownloadBookType extends Fragment implements View.OnClickListener {
    private TextView txtMenu;
    private ImageView imageMenu;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewFavourites;
    private DownloadAdapter downloadAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private String bookType;
    private String TAG = DownloadBookType.class.getName();


    /**
     * <p>It is used to get Fragment Instance</p>
     *
     * @param
     * @return
     */
    public static Fragment getFragmentInstance(String bookType) {
        Bundle args = new Bundle();
        args.putString(Constant.IntentKey.BOOK_TYPE, bookType);
        Fragment fragment = new DownloadBookType();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_read_book_type, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getIntentData(); //Retrieve Intent Data
        initUI(view);   //Initialize UI
        initAD(view);  //Initialize Admob Banner Ads

    }

    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {

        bookType = getArguments().getString(Constant.IntentKey.BOOK_TYPE);

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());

        //Adding Place Holder in Recycler View

        objectArrayList.clear();
        objectArrayList.add(new ProgressObject());

        //Initialize Layout Manager & setup with Recycler View

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
                } else {
                    return 1;
                }
            }
        });

        recyclerViewFavourites = (RecyclerView) view.findViewById(R.id.recycler_view_book_type);
        recyclerViewFavourites.setLayoutManager(gridLayoutManager);

        int spanCount = 3; // 3 columns
        int spacing = 15; // 50px
        boolean includeEdge = true;
        recyclerViewFavourites.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        //Setup Recycler View Adapter with Adapter

        downloadAdapter = new DownloadAdapter(getActivity(), objectArrayList) {
            @Override
            public void select(int position) {

                final DataObject dataObject = (DataObject) objectArrayList.get(position);
                final ArrayList<Object> tempList = new ArrayList<>();
                Utility.Logger(TAG, "Data : " + dataObject.getTitle());

                if (dataObject.getFileType().equalsIgnoreCase(Constant.DataType.PDF)) {

                    Intent intent = new Intent(getActivity(), ReadBook.class);
                    intent.putExtra(Constant.IntentKey.BOOK_DETAIL, dataObject);
                    startActivity(intent);

                } else if (dataObject.getFileType().equalsIgnoreCase(Constant.DataType.EPUB)) {


                    //In case of Epub file , it would open FolioReader
                    //We also check out either this position is already read by user or not

                    tempList.addAll(management.getDataFromDatabase(new DatabaseObject()
                            .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                            .setDbOperation(Constant.DB.SPECIFIC_BOOK)
                            .setDataObject(new DataObject()
                                    .setBookUrl(dataObject.getBookUrl())
                                    .setFileType(Constant.DataType.EPUB))));

                    FolioReader folioReader = FolioReader.getInstance(getActivity(), new ReadTotalPagesListener() {
                        @Override
                        public void saveTotalPages(String totalPages) {

                            //It save the total No of pages in a book
                            //in case of user first time load the book

                            if (tempList.size() <= 0) {
                                management.getDataFromDatabase(new DatabaseObject()
                                        .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                                        .setDbOperation(Constant.DB.INSERT)
                                        .setDataObject(new DataObject()
                                                .setTitle(dataObject.getTitle())
                                                .setFileType(Constant.DataType.EPUB)
                                                .setBookUrl(dataObject.getBookUrl())
                                                .setCoverUrl(dataObject.getCoverUrl())
                                                .setBookPage(totalPages)));
                            }

                        }
                    });

                    folioReader.saveThemeOption(Utility.isNightMode(getActivity()));
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
                                            .setBookUrl(dataObject.getBookUrl())
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

                    String filePath = new File(Uri.parse(dataObject.getBookUrl()).getPath()).getAbsolutePath();
                    String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
                    String mimeType = null;

                    if (extension != null)
                        mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

                    Utility.Logger(TAG, "Epub File Path = " + filePath
                            + " Mime Type : " + mimeType + " Extension : " + extension);
                    new File(Uri.parse(dataObject.getBookUrl()).getPath());

                    folioReader.openBook(filePath);


                }


            }

            @Override
            public void delete(int position) {

                DataObject dataObject = (DataObject) objectArrayList.get(position);
                management.getDataFromDatabase(new DatabaseObject()
                        .setTypeOperation(Constant.TYPE.DOWNLOAD)
                        .setDbOperation(Constant.DB.DELETE)
                        .setDataObject(dataObject));

                objectArrayList.remove(position);
                if (objectArrayList.size() <= 0) {

                }

                downloadAdapter.notifyDataSetChanged();

            }
        };
        recyclerViewFavourites.setAdapter(downloadAdapter);


        //Retrieve data from Db , it's get Specific type Book 'Pdf' , 'Epub' etc

        objectArrayList.clear();
        objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(Constant.TYPE.DOWNLOAD)
                .setDbOperation(Constant.DB.SPECIFIC_TYPE)
                .setDataObject(new DataObject()
                        .setFileType(bookType))));

        downloadAdapter.notifyDataSetChanged();

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
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();


        if (objectArrayList.size() <= 0) {

            //Adding Place Holder in Recycler View

            if (bookType.equalsIgnoreCase(Constant.DataType.PDF)) {

                objectArrayList.add(new EmptyObject()
                        .setTitle(Utility.getStringFromRes(getActivity(), R.string.no_book))
                        .setDescription(Utility.getStringFromRes(getActivity(), R.string.no_book_tagline))
                        .setPlaceHolderIcon(R.drawable.em_no_book));

            } else if (bookType.equalsIgnoreCase(Constant.DataType.EPUB)) {

                objectArrayList.add(new EmptyObject()
                        .setTitle(Utility.getStringFromRes(getActivity(), R.string.no_book))
                        .setDescription(Utility.getStringFromRes(getActivity(), R.string.no_book_tagline))
                        .setPlaceHolderIcon(R.drawable.em_no_book));

            }


        }


    }
}
