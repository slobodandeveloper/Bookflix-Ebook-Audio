package com.fixnowitdeveloper.bookflix.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fixnowitdeveloper.bookflix.ActivityUtil.MyAddedBooks;
import com.google.firebase.auth.FirebaseAuth;
import com.fixnowitdeveloper.bookflix.ActivityUtil.AboutUs;
import com.fixnowitdeveloper.bookflix.ActivityUtil.Base;
import com.fixnowitdeveloper.bookflix.ActivityUtil.Download;
import com.fixnowitdeveloper.bookflix.ActivityUtil.Favourites;
import com.fixnowitdeveloper.bookflix.ActivityUtil.FeedTopic;
import com.fixnowitdeveloper.bookflix.ActivityUtil.History;
import com.fixnowitdeveloper.bookflix.ActivityUtil.OnBoarding;
import com.fixnowitdeveloper.bookflix.ActivityUtil.Profile;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.GlideApp;
import com.fixnowitdeveloper.bookflix.DatabaseUtil.DatabaseObject;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;
import com.onesignal.OneSignal;

public class Setting extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private String TAG = Setting.class.getName();
    private TextView txtMenu;
    private RelativeLayout layoutFvt;
    private RelativeLayout layoutMyAddedBooks;
    private RelativeLayout layoutDownload;
    private RelativeLayout layoutRate;
    private RelativeLayout layoutShare;
    private RelativeLayout layoutOverlay;
    private RelativeLayout layoutPrivacy;
    private RelativeLayout layoutPlaylist;
    private RelativeLayout layoutProfile;
    private RelativeLayout layoutLogout;
    private RelativeLayout layoutFeed;
    private CardView cardProfile;
    private Management management;
    private PrefObject prefObject;
    private Switch switchNight;
    private Switch switchPush;
    private Switch switchWifi;
    private PrefObject preference;
    private LinearLayout layoutEdit;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtDividerLogout;
    private ImageView imageUser;
    private String pictureUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_setting, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI

    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());

        txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.setting));

        layoutProfile = view.findViewById(R.id.layout_profile);
        layoutLogout = view.findViewById(R.id.layout_logout);
        layoutFvt = view.findViewById(R.id.layout_fvt);
        layoutMyAddedBooks = view.findViewById(R.id.layout_my_added_books);
        layoutDownload = view.findViewById(R.id.layout_download);
        layoutPlaylist = view.findViewById(R.id.layout_playlist);
        layoutRate = view.findViewById(R.id.layout_rate);
        layoutFeed = view.findViewById(R.id.layout_feed);
        layoutShare = view.findViewById(R.id.layout_share);
        layoutPrivacy = view.findViewById(R.id.layout_privacy);
        layoutEdit = view.findViewById(R.id.layout_edit);
        cardProfile = view.findViewById(R.id.card_profile);

        imageUser = view.findViewById(R.id.image_user);
        txtName = view.findViewById(R.id.txt_name);
        txtEmail = view.findViewById(R.id.txt_email);
        txtDividerLogout = view.findViewById(R.id.txt_divider_logout);

        switchWifi = view.findViewById(R.id.switch_wifi);
        switchPush = view.findViewById(R.id.switch_push);
        switchNight = view.findViewById(R.id.switch_night);

        preference = management.getPreferences(new PrefObject()
                .setRetrieveNightMode(true)
                .setRetrieveDownloadWifi(true)
                .setRetrievePush(true)
                .setRetrieveUserCredential(true));

        if (preference.isNightMode())
            switchNight.setChecked(true);
        else
            switchNight.setChecked(false);

        if (preference.isDownloadWifi())
            switchWifi.setChecked(true);
        else
            switchWifi.setChecked(false);

        if (preference.isPush())
            switchPush.setChecked(true);
        else
            switchPush.setChecked(false);


        layoutLogout.setOnClickListener(this);
        layoutProfile.setOnClickListener(this);
        layoutFvt.setOnClickListener(this);
        layoutMyAddedBooks.setOnClickListener(this);
        layoutDownload.setOnClickListener(this);
        layoutPlaylist.setOnClickListener(this);
        layoutRate.setOnClickListener(this);
        layoutShare.setOnClickListener(this);
        layoutPrivacy.setOnClickListener(this);
        layoutFeed.setOnClickListener(this);
        layoutEdit.setOnClickListener(this);

        switchPush.setOnCheckedChangeListener(this);
        switchWifi.setOnCheckedChangeListener(this);
        switchNight.setOnCheckedChangeListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v == layoutFvt) {

            if (prefObject == null)
                return;

            if (prefObject.isLogin()) {

                startActivity(new Intent(getActivity(), Favourites.class));

            } else {

                startActivity(new Intent(getActivity(), OnBoarding.class));

            }


        }
        if (v == layoutDownload) {

            startActivity(new Intent(getActivity(), Download.class));
        }
        if (v == layoutShare) {
            Utility.shareApp(getActivity());
        }
        if (v == layoutRate) {
            Utility.rateApp(getActivity());
        }
        if (v == layoutPrivacy) {
            startActivity(new Intent(getActivity(), AboutUs.class));
        }
        if (v == layoutPlaylist) {
            startActivity(new Intent(getActivity(), History.class));
        }
        if (v == layoutMyAddedBooks) {
            startActivity(new Intent(getActivity(), MyAddedBooks.class));
        }
        if (v == layoutFeed) {
            startActivity(new Intent(getActivity(), FeedTopic.class).putExtra(Constant.IntentKey.CONTINUE_REQUIRED, false));
        }
        if (v == layoutProfile) {

            if (prefObject == null)
                return;

            //Open Profile activity upon Successful login
            //otherwise login screen

            if (prefObject.isLogin()) {

                startActivity(new Intent(getActivity(), Profile.class));

            } else {

                startActivity(new Intent(getActivity(), OnBoarding.class));

            }

        }
        if (v == layoutLogout) {

            management.savePreferences(new PrefObject()
                    .setSaveLogin(true)
                    .setLogin(false));

            layoutLogout.setVisibility(View.GONE);
            cardProfile.setVisibility(View.GONE);
            txtDividerLogout.setVisibility(View.GONE);

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FAVOURITES)
                    .setDbOperation(Constant.DB.DELETE_FAVOURITES)
                    .setDataObject(new DataObject()
                            .setUserId(prefObject.getUserId())));

            if (preference.getLoginType().equalsIgnoreCase(Constant.LoginType.FACEBOOK_LOGIN)
                    || preference.getLoginType().equalsIgnoreCase(Constant.LoginType.GOOGLE_LOGIN))
                FirebaseAuth.getInstance().signOut();

            onResume();

        }
        if (v == layoutEdit) {

            if (prefObject == null)
                return;

            if (prefObject.isLogin()) {

                startActivity(new Intent(getActivity(), Profile.class));

            } else {

                startActivity(new Intent(getActivity(), OnBoarding.class));

            }

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == switchPush.getId()) {

            if (isChecked) {

                management.savePreferences(new PrefObject()
                        .setSavePush(true)
                        .setPush(true));

                OneSignal.setSubscription(true);

            } else {

                management.savePreferences(new PrefObject()
                        .setSavePush(true)
                        .setPush(false));

                OneSignal.setSubscription(false);

            }

        } else if (buttonView.getId() == switchWifi.getId()) {

            if (isChecked) {

                management.savePreferences(new PrefObject()
                        .setSaveDownloadWifi(true)
                        .setDownloadWifi(true));

            } else {

                management.savePreferences(new PrefObject()
                        .setSaveDownloadWifi(true)
                        .setDownloadWifi(false));

            }

        } else if (buttonView.getId() == switchNight.getId()) {

            if (isChecked) {

                management.savePreferences(new PrefObject()
                        .setSaveNightMode(true)
                        .setNightMode(true));

                ((Base) getActivity()).refreshNightMode();

            } else {

                management.savePreferences(new PrefObject()
                        .setSaveNightMode(true)
                        .setNightMode(false));

                ((Base) getActivity()).refreshNightMode();

            }

        }

    }


    @Override
    public void onResume() {
        super.onResume();

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        if (prefObject.isLogin()) {

            /*layoutLogout.setVisibility(View.VISIBLE);
            cardProfile.setVisibility(View.VISIBLE);
            txtDividerLogout.setVisibility(View.VISIBLE);*/

            layoutLogout.setVisibility(View.GONE);
            cardProfile.setVisibility(View.GONE);
            txtDividerLogout.setVisibility(View.GONE);

            txtName.setText(prefObject.getFirstName() + " " + prefObject.getLastName());
            txtEmail.setText(prefObject.getUserEmail());

            if (prefObject.getLoginType().equalsIgnoreCase(Constant.LoginType.NATIVE_LOGIN)) {
                pictureUrl = Constant.ServerInformation.PICTURE_URL + prefObject.getPictureUrl();

            } else {
                pictureUrl = prefObject.getPictureUrl() + Constant.ServerInformation.FACEBOOK_HIGH_PIXEL_URL;

            }

            GlideApp.with(this).load(pictureUrl)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.profile_picture)
                            .error(R.drawable.profile_picture)
                            .signature(new ObjectKey(System.currentTimeMillis())))
                    .into(imageUser);
        }
    }


}
