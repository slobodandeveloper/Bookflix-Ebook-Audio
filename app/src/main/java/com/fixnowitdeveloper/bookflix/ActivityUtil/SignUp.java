package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.Base64Object;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SignUp extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private TextView txtMenu;
    private ImageView imageBack;
    private EditText editFirstName;
    private EditText editLastName;
    private ImageView imageProfile;
    private EditText editEmail;
    private EditText editPassword;
    private TextView txtSignUp;
    private TextView txtLogin;
    private EditText editConfirmPassword;
    private boolean isPictureSelected;
    private String base64Picture;
    private Management management;
    private Bitmap selectedBitmap;
    private boolean isLoginRequired;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getIntentData();  //Retrieve Intent Data
        initUI(); //Initialize UI

    }

    /**
     * <p>It is used to get Intent Data</p>
     */
    private void getIntentData() {
        isLoginRequired = getIntent().getBooleanExtra(Constant.IntentKey.LOGIN_REQUIRED, false);
    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.create_account));

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);

        editFirstName = (EditText) findViewById(R.id.edit_firstName);
        editLastName = (EditText) findViewById(R.id.edit_lastName);
        imageProfile = (ImageView) findViewById(R.id.image_profile);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
        txtSignUp = (TextView) findViewById(R.id.txt_sign_up);
        txtLogin = (TextView) findViewById(R.id.txt_login);

        txtSignUp.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        imageProfile.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }


    /**
     * <p>It is used to send request to server for user registration</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.SIGN_UP)
                .setConnectionCallback(this));

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

            jsonObject.accumulate("functionality", "register");
            jsonObject.accumulate("first_name", editFirstName.getText().toString());
            jsonObject.accumulate("last_name", editLastName.getText().toString());
            jsonObject.accumulate("email", editEmail.getText().toString());
            jsonObject.accumulate("password", editPassword.getText().toString());
            jsonObject.accumulate("userType", Constant.LoginType.NATIVE_LOGIN);
            jsonObject.accumulate("picture", base64Picture);
            Log.d("aca","aca");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.extraData("JSON", json);
        return json;

    }


    @Override
    public void onClick(View v) {
        if (v == txtSignUp) {

            if (Utility.isEmptyString(editFirstName.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.empty_first_name), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editLastName.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.empty_last_name), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editEmail.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_empty), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(editPassword.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_empty), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editConfirmPassword.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_empty), Toast.LENGTH_SHORT);
                return;
            }

            if (!isPictureSelected) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.select_picture), Toast.LENGTH_SHORT);
                return;
            }

            if (!(editPassword.getText().toString().equals(editConfirmPassword.getText().toString()))) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_mis_match), Toast.LENGTH_SHORT);
                return;
            }

            base64Picture = Utility.base64Converter(new Base64Object(true, false, selectedBitmap));

            sendServerRequest();

        }
        if (v == txtLogin) {

            if (isLoginRequired)
                startActivity(new Intent(this, Login.class));

            finish();
        }
        if (v == imageProfile) {
            onPictureSelector(this);
        }
        if (v == imageBack) {

            startActivity(new Intent(this, Login.class));
            finish();
        }
    }


    /**
     * <p>It trigger dialog for picture selection </p>
     *
     * @param context
     */
    private void onPictureSelector(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.custom_dialog_layout);

        LinearLayout layout_camera = (LinearLayout) dialog.findViewById(R.id.layout_camera);
        layout_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectCamera();
                dialog.dismiss();
            }
        });

        LinearLayout layout_gallery = (LinearLayout) dialog.findViewById(R.id.layout_gallery);
        layout_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSelectGallery();
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    /**
     * <P>It is used to initialize Camera for picture capture</P>
     */
    private void onSelectCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, Constant.RequestCode.REQUEST_CODE_CAMERA);//zero can be replaced with any action code
    }


    /**
     * <p>It is used to open Gallery for picture selection</p>
     */
    private void onSelectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, Constant.RequestCode.REQUEST_CODE_GALLERY);//one can be replaced with any action code
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            selectedBitmap = photo;
            imageProfile.setImageBitmap(photo);
            isPictureSelected = true;
        }

        if (requestCode == Constant.RequestCode.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            imageProfile.setImageURI(selectedImage);

            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            isPictureSelected = true;
        }


    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            DataObject dataObject = (DataObject) data;

            management.savePreferences(new PrefObject()
                    .setSaveLogin(true)
                    .setLogin(true));

            management.savePreferences(new PrefObject()
                    .setSaveUserCredential(true)
                    .setUserId(dataObject.getUserId())
                    .setLoginType(dataObject.getLoginType())
                    .setFirstName(dataObject.getFirstName())
                    .setLastName(dataObject.getLastName())
                    .setUserPassword(dataObject.getPassword())
                    .setUserEmail(dataObject.getEmail())
                    .setPictureUrl(dataObject.getPicture()));


            finish();

        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Toaster(this, data, Toast.LENGTH_SHORT);
    }
}