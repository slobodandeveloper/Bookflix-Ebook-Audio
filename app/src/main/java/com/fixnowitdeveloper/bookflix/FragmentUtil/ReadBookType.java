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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fixnowitdeveloper.bookflix.ActivityUtil.ReadBook;
import com.fixnowitdeveloper.bookflix.AdapterUtil.ReadingAdapter;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseObject;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.InternetObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.R;
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

import java.io.File;
import java.util.ArrayList;

public class ReadBookType extends Fragment implements View.OnClickListener {
    private TextView txtMenu;
    private ImageView imageMenu;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewFavourites;
    private ReadingAdapter readingAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private String bookType;
    private int isMyAddedBook;
    private String TAG = ReadBookType.class.getName();


    /**
     * <p>It is used to get Fragment Instance</p>
     *
     * @param
     * @return
     */
    public static Fragment getFragmentInstance(String bookType) {
        Bundle args = new Bundle();
        args.putString(Constant.IntentKey.BOOK_TYPE, bookType);
        Fragment fragment = new ReadBookType();
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment getFragmentInstance(String bookType, int isMyAddedBook) {
        Bundle args = new Bundle();
        args.putString(Constant.IntentKey.BOOK_TYPE, bookType);
        args.putInt(Constant.IntentKey.MY_ADDED_BOOK, isMyAddedBook);
        Fragment fragment = new ReadBookType();
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
        isMyAddedBook = getArguments().getInt(Constant.IntentKey.MY_ADDED_BOOK, 0);

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

        //Setup Recycler View Adapter with Adapter

        readingAdapter = new ReadingAdapter(getActivity(), objectArrayList) {
            @Override
            public void selectWallpaper(int position) {

                final DataObject dataObject = (DataObject) objectArrayList.get(position);
                Utility.Logger(TAG, "Data : " + dataObject.getTitle());

                if (dataObject.getFileType().equalsIgnoreCase(Constant.DataType.PDF)) {

                    Intent intent = new Intent(getActivity(), ReadBook.class);
                    intent.putExtra(Constant.IntentKey.BOOK_DETAIL, dataObject);
                    startActivity(intent);

                } else if (dataObject.getFileType().equalsIgnoreCase(Constant.DataType.EPUB)) {


                    FolioReader folioReader = FolioReader.getInstance(getActivity(), null);
                    folioReader.saveThemeOption(Utility.isNightMode(getActivity()));
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
                                            .setId(dataObject.getId())
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


            }
        };
        recyclerViewFavourites.setAdapter(readingAdapter);


        //Retrieve data from Db , it's get Specific type Book 'Pdf' , 'Epub' etc

        objectArrayList.clear();
        if (isMyAddedBook == 1) {
            objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.MY_ADDED_BOOKS_STATUS)
                    .setDbOperation(Constant.DB.SPECIFIC_TYPE)
                    .setDataObject(new DataObject()
                            .setFileType(bookType))));

        } else {
            objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                    .setDbOperation(Constant.DB.SPECIFIC_TYPE)
                    .setDataObject(new DataObject()
                            .setFileType(bookType))));
        }
        readingAdapter.notifyDataSetChanged();

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
