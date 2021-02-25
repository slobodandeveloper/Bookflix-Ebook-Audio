package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseObject;
import com.fixnowitdeveloper.bookflix.FragmentUtil.AuthorFragment;
import com.fixnowitdeveloper.bookflix.FragmentUtil.CategoriesFragment;
import com.fixnowitdeveloper.bookflix.FragmentUtil.Home;
import com.fixnowitdeveloper.bookflix.FragmentUtil.LatestFragment;
import com.fixnowitdeveloper.bookflix.FragmentUtil.Setting;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import java.util.ArrayList;

public class Base extends AppCompatActivity implements View.OnClickListener {
    public static DrawerLayout layoutDrawer;
    private PrefObject prefObject;
    private Management management;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private String TAG = Base.class.getName();
    private LinearLayout layoutHome, layoutCategories;
    private LinearLayout layoutSearch, layoutProfile;
    private LinearLayout layoutFavourite;
    private ImageView imageHome, imageCategories;
    private ImageView imageSearch, imageProfile;
    private ImageView imageFavourite;
    private TextView txtHome, txtCategories;
    private TextView txtSearch, txtProfile;
    private TextView txtFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initGDPRChecker(); //Initialize GDPR Checker
        initUI(); //Initialize UI

    }


    /**
     * <p>It is used to init. GDPR Checker</p>
     */
    private void initGDPRChecker() {

        new GDPRChecker()
                .withContext(this)
                .withPrivacyUrl(Constant.ServerInformation.PRIVACY_URL) // your privacy url
                .withPublisherIds(Constant.Credentials.PUBLISHER_ID) // your admob account Publisher id
                //.withTestMode() // remove this on real project
                .check();


    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        management = new Management(this);


        //Init bottom Navigation

        layoutHome = (LinearLayout) findViewById(R.id.layout_home);
        imageHome = (ImageView) findViewById(R.id.image_home);
        txtHome = (TextView) findViewById(R.id.txt_home);

        layoutCategories = (LinearLayout) findViewById(R.id.layout_categories);
        imageCategories = (ImageView) findViewById(R.id.image_categories);
        txtCategories = (TextView) findViewById(R.id.txt_categories);

        layoutSearch = (LinearLayout) findViewById(R.id.layout_search);
        imageSearch = (ImageView) findViewById(R.id.image_search);
        txtSearch = (TextView) findViewById(R.id.txt_search);

        layoutFavourite = findViewById(R.id.layout_favourite);
        imageFavourite = findViewById(R.id.image_favourite);
        txtFavourite = findViewById(R.id.txt_favourite);

        layoutProfile = (LinearLayout) findViewById(R.id.layout_profile);
        imageProfile = (ImageView) findViewById(R.id.image_profile);
        txtProfile = (TextView) findViewById(R.id.txt_profile);

        //Always open Home Fragment at beginning

        onFragmentSelection(layoutHome);

        //listviewDrawer.setOnItemClickListener(this);
        layoutHome.setOnClickListener(this);
        layoutCategories.setOnClickListener(this);
        layoutSearch.setOnClickListener(this);
        layoutFavourite.setOnClickListener(this);
        layoutProfile.setOnClickListener(this);

    }


    /**
     * <o>It is used to change colour of selection
     * & open specific fragment</o>
     */
    @SuppressLint("ResourceType")
    private void onFragmentSelection(View view) {


        /*if (Constant.getLatitude() == 0.0) {
            Utility.Toaster(getApplicationContext(),Constant.ToastMessage.RES_START_APP, Toast.LENGTH_SHORT);
            return;
        }*/

        if (view == layoutHome) {

            txtHome.setTextColor(Utility.getAttrColor(this, R.attr.colorSelector));
            txtCategories.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtSearch.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtFavourite.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtProfile.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));

            imageHome.setColorFilter(Utility.getAttrColor(this, R.attr.colorSelector));
            imageCategories.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageSearch.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageFavourite.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageProfile.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));

            openFragment(new Home());


        } else if (view == layoutCategories) {

            txtHome.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtCategories.setTextColor(Utility.getAttrColor(this, R.attr.colorSelector));
            txtSearch.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtFavourite.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtProfile.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));

            imageHome.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageCategories.setColorFilter(Utility.getAttrColor(this, R.attr.colorSelector));
            imageSearch.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageFavourite.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageProfile.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));


            openFragment(new CategoriesFragment());


        } else if (view == layoutSearch) {


            txtHome.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtCategories.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtSearch.setTextColor(Utility.getAttrColor(this, R.attr.colorSelector));
            txtFavourite.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtProfile.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));

            imageHome.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageCategories.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageSearch.setColorFilter(Utility.getAttrColor(this, R.attr.colorSelector));
            imageFavourite.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageProfile.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));


            openFragment(new LatestFragment());


        } else if (view == layoutProfile) {


            txtHome.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtCategories.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtSearch.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtProfile.setTextColor(Utility.getAttrColor(this, R.attr.colorSelector));
            txtFavourite.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));


            imageHome.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageCategories.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageSearch.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageProfile.setColorFilter(Utility.getAttrColor(this, R.attr.colorSelector));
            imageFavourite.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));


            openFragment(new Setting());


        } else if (view == layoutFavourite) {


            txtHome.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtCategories.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtSearch.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));
            txtFavourite.setTextColor(Utility.getAttrColor(this, R.attr.colorSelector));
            txtProfile.setTextColor(Utility.getAttrColor(this, R.attr.colorTagline));

            imageHome.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageCategories.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageSearch.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));
            imageFavourite.setColorFilter(Utility.getAttrColor(this, R.attr.colorSelector));
            imageProfile.setColorFilter(Utility.getAttrColor(this, R.attr.colorTagline));

            openFragment(new AuthorFragment());


        }


    }


    /**
     * <p>It is used to open Fragment</p>
     *
     * @param fragment
     */
    private void openFragment(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_container, fragment);
            fragmentTransaction.commit();

        }
    }


    @Override
    public void onClick(View v) {
        if (v == layoutHome || v == layoutCategories
                || v == layoutSearch || v == layoutProfile
                || v == layoutFavourite) {

            onFragmentSelection(v);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        objectArrayList.clear();
        objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setDataObject(new DataObject())
                .setTypeOperation(Constant.TYPE.FAVOURITES)
                .setDbOperation(Constant.DB.RETRIEVE)));

        Utility.Logger(TAG, "Downloads = " + objectArrayList.size());


    }

    public void refreshNightMode() {

        startActivity(new Intent(this, Base.class));
        finish();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
