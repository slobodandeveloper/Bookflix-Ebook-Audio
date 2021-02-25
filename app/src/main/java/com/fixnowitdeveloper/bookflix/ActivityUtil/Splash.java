package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.InternetCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends AppCompatActivity implements ConnectionCallback, InternetCallback {
    private Management management;
    private PrefObject prefObject;
    private Handler handler;
    private Runnable runnable;
    private static final long SPLASH_DISPLAY_LENGTH = 1500;
    private LinearLayout layoutConfigure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        initUI(); //Initialize UI

    }


    /**
     * <p>It is used to initialize the UI</p>
     */
    private void initUI() {


        layoutConfigure = findViewById(R.id.layout_configure);
        management = new Management(this);


        //Check Permission for Marshmallow version

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    ) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.CAMERA
                        , Manifest.permission.ACCESS_NETWORK_STATE

                }, Constant.RequestCode.PERMISSION_REQUEST_CODE);

            } else {

                checkPreference();

            }

        } else {


            checkPreference();

        }


    }


    /**
     * <p>It is used to check preference</p>
     */
    private void checkPreference() {


        //Retrieve Shared Preference regarding First Launch

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.ADMOB)
                .setInternetCallback(this)
                .setConnectionCallback(this));


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == Constant.RequestCode.PERMISSION_REQUEST_CODE) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.external_storage_permission), Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.external_storage_permission), Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.read_phone_state_permission), Toast.LENGTH_SHORT);
                    return;
                }

                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    Utility.Toaster(this, Utility.getStringFromRes(this, R.string.camera_permission), Toast.LENGTH_SHORT);
                    return;
                }


                checkPreference();


            }


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

            jsonObject.accumulate("functionality", "admob_configuration");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            DataObject dataObject = (DataObject) data;
            Constant.Credentials.ADMOB_APP_ID = dataObject.getAdmobAppId();
            Constant.Credentials.ADMOB_INTERSTITIAL_ID = dataObject.getAdmobInterstitialId();
            Constant.Credentials.ADMOB_BANNER_ID = dataObject.getAdmobBannerId();
            Constant.Credentials.PUBLISHER_ID = dataObject.getAdmobPublisherId();
            Constant.ServerInformation.PRIVACY_URL = dataObject.getAdmobPrivacyUrl();

            //Initialize Admob App ID

            MobileAds.initialize(this, Constant.Credentials.ADMOB_APP_ID);
            layoutConfigure.setVisibility(View.GONE);


            prefObject = management.getPreferences(new PrefObject()
                    .setRetrieveFirstLaunch(true));

            //Check either it's first launch or not

            if (prefObject.isFirstLaunch()) {

                management.savePreferences(new PrefObject()
                        .setFirstLaunch(false)
                        .setSaveFirstLaunch(true));

                startActivity(new Intent(this, FeedTopic.class));
                finish();

            } else {

                startActivity(new Intent(this, Base.class));
                finish();

            }


        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {

        layoutConfigure.setVisibility(View.GONE);
        if (Constant.Credentials.isSingleStation) {

            //startActivity(new Intent(this, SingleViewer.class));
            finish();

        } else {

            startActivity(new Intent(this, Base.class));
            finish();

        }

    }

    @Override
    public void onConnectivityFailed() {

        startActivity(new Intent(this, Base.class));
        finish();

    }
}
