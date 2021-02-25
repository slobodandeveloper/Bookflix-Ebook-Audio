package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseObject;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PdfContentObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import java.util.ArrayList;

public class ReadBook extends AppCompatActivity implements View.OnClickListener, OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {

    private TextView txtMenu;
    private ImageView imageMenu;
    private Management management;
    private String TAG = ReadBook.class.getName();
    private String bookUrl;
    private PDFView pdfView;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<PdfContentObject> pdfList = new ArrayList<>();
    private int pdfPage = 0;
    private DataObject detailObject;

    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        getIntentData(); //Receive Intent Data
        initUI(); //Initialize UI

    }


    /**
     * <p>It is used to receive Intent Data</p>
     */
    private void getIntentData() {
        detailObject = getIntent().getParcelableExtra(Constant.IntentKey.BOOK_DETAIL);
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        bookUrl = detailObject.getBookUrl();
        Utility.Logger(TAG, "Book Url = " + bookUrl + " Title= " + detailObject.getTitle());

        management = new Management(this);

        if(detailObject.getDataType().name() == Constant.DataType.MY_ADDED_BOOKS) {
            objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.MY_ADDED_BOOKS_STATUS)
                    .setDbOperation(Constant.DB.SPECIFIC_BOOK)
                    .setDataObject(new DataObject()
                            .setBookUrl(bookUrl)
                            .setFileType(Constant.DataType.PDF))));
        }else {

            objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                    .setDbOperation(Constant.DB.SPECIFIC_BOOK)
                    .setDataObject(new DataObject()
                            .setBookUrl(bookUrl)
                            .setFileType(Constant.DataType.PDF))));
        }

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.book_reader));

        imageMenu = findViewById(R.id.image_back);
        imageMenu.setVisibility(View.VISIBLE);
        imageMenu.setImageResource(R.drawable.ic_back);

        //Utility.Logger(TAG, "Formatted Url = " + formattedUrl);

        pdfView = findViewById(R.id.pdfView);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
            return;
        }else {
            displayFromUri(Uri.parse(bookUrl));

            /*if (URLUtil.isFileUrl(bookUrl) || URLUtil.isValidUrl(bookUrl)) {


                if (objectArrayList.size() > 0) {

                    DataObject data = (DataObject) objectArrayList.get(0);
                    pdfPage = Integer.parseInt(data.getCurrentPage());


                    pdfView.fromUri(Uri.parse(bookUrl))
                            .defaultPage(pdfPage)
                            .onPageChange(this)
                            .enableAnnotationRendering(true)
                            .onLoad(this)
                            .scrollHandle(new DefaultScrollHandle(this))
                            .spacing(10) // in dp
                            .onPageError(this)
                            .enableAntialiasing(true)
                            .load();

                } else {
                   Uri uri =  Uri.parse(bookUrl);
                    pdfPage = 0;
                    pdfView.fromUri(uri)
                            .defaultPage(pdfPage)
                            .onPageChange(this)
                            .enableAnnotationRendering(true)
                            .scrollHandle(new DefaultScrollHandle(this))
                            .spacing(10) // in dp
                            .onPageError(this)
                            .enableAntialiasing(true)
                            .onLoad(new OnLoadCompleteListener() {
                                @Override
                                public void loadComplete(int nbPages) {



                                    management.getDataFromDatabase(new DatabaseObject()
                                            .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                                            .setDbOperation(Constant.DB.INSERT)
                                            .setDataObject(new DataObject()
                                                    .setTitle(detailObject.getTitle())
                                                    .setFileType(Constant.DataType.PDF)
                                                    .setBookUrl(bookUrl)
                                                    .setCoverUrl(detailObject.getCoverUrl())
                                                    .setBookPage(String.valueOf(nbPages))
                                                    .setCurrentPage(String.valueOf(pdfPage))));

                                }
                            })
                            .load();
                }
            }*/
        }

        imageMenu.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == imageMenu) {
            finish();
        }
    }

    @Override
    public void loadComplete(int nbPages) {
        Utility.Logger(TAG, "Pages = " + nbPages + " " + pdfView.getTableOfContents().size());

        //getTableOfContent(pdfView.getTableOfContents(), "-");

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        Utility.Logger(TAG, "PageChange = Page No. " + page + " PageCount. " + pageCount);
    }

    @Override
    public void onPageError(int page, Throwable t) {
        t.printStackTrace();
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.DATA_TYPE type = null;
        if(objectArrayList!=null && objectArrayList.size()>0){
            DataObject dataObject = (DataObject) objectArrayList.get(0);
            type = dataObject.getDataType();
        }

        objectArrayList.clear();
        if(type.name() == Constant.DataType.MY_ADDED_BOOKS) {
            objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.MY_ADDED_BOOKS_STATUS)
                    .setDbOperation(Constant.DB.SPECIFIC_BOOK)
                    .setDataObject(new DataObject()
                            .setBookUrl(bookUrl)
                            .setFileType(Constant.DataType.PDF))));
        }else {
            objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                    .setDbOperation(Constant.DB.SPECIFIC_BOOK)
                    .setDataObject(new DataObject()
                            .setBookUrl(bookUrl)
                            .setFileType(Constant.DataType.PDF))));
        }

        if (objectArrayList.size() > 0) {

            DataObject dataObject = (DataObject) objectArrayList.get(0);

            if(dataObject.getDataType().name() == Constant.DataType.MY_ADDED_BOOKS){
                management.getDataFromDatabase(new DatabaseObject()
                        .setTypeOperation(Constant.TYPE.MY_ADDED_BOOKS_STATUS)
                        .setDbOperation(Constant.DB.UPDATE)
                        .setDataObject(dataObject
                                .setFileType(Constant.DataType.PDF)
                                .setCurrentPage(String.valueOf(pdfView.getCurrentPage()))));
            }else {
                management.getDataFromDatabase(new DatabaseObject()
                        .setTypeOperation(Constant.TYPE.FILE_READING_STATUS)
                        .setDbOperation(Constant.DB.UPDATE)
                        .setDataObject(dataObject
                                .setFileType(Constant.DataType.PDF)
                                .setCurrentPage(String.valueOf(pdfView.getCurrentPage()))));
            }
        }

    }*/

    private void displayFromUri(Uri uri) {
        pdfView.fromUri(uri)
                .defaultPage(0)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .onRender(new OnRenderListener() {
                    @Override
                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                        Log.e(TAG,"as");
                    }
                })
                .load();
    }

    /**
     * Listener for response to user permission request
     *
     * @param requestCode  Check that permission request code matches
     * @param permissions  Permissions that requested
     * @param grantResults Whether permissions granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initUI();
            }
        }
    }
}
