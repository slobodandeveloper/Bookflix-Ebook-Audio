package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.FontUtil.Font;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, ConnectionCallback {
    private TextView txtMenu;
    private ImageView imageBack;
    private EditText editEmail;
    private EditText editPassword;
    private AppCompatCheckBox checkboxRemember;
    private TextView txtForgot;
    private TextView txtLogin;
    private TextView txtSignUp;
    private Management management;
    private PrefObject userData;
    private String TAG = Login.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI(); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.login));

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        editEmail = (EditText) findViewById(R.id.edit_email);
        editPassword = (EditText) findViewById(R.id.edit_password);

        checkboxRemember = (AppCompatCheckBox) findViewById(R.id.checkbox_remember);
        checkboxRemember.setTypeface(Font.ubuntu_regular_font(this));  //Setting Font

        txtForgot = (TextView) findViewById(R.id.txt_forgot);
        txtLogin = (TextView) findViewById(R.id.txt_login);
        txtSignUp = (TextView) findViewById(R.id.txt_sign_up);


        management = new Management(this);
        userData = management.getPreferences(new PrefObject()
                .setRetrieveUserRemember(true)
                .setRetrieveUserCredential(true));

        //Check either user remember Email or Password

        if (userData.isUserRemember()) {

            checkboxRemember.setChecked(userData.isUserRemember());
            editEmail.setText(userData.getUserEmail());
            editPassword.setText(userData.getUserPassword());

        } else
            checkboxRemember.setChecked(false);

        txtLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        txtForgot.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        checkboxRemember.setOnCheckedChangeListener(this);

    }


    /**
     * <p>It is used to send request to server for user authentication</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.LOGIN)
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

            jsonObject.accumulate("functionality", "login");
            jsonObject.accumulate("email", editEmail.getText().toString());
            jsonObject.accumulate("password", editPassword.getText().toString());
            jsonObject.accumulate("userType", Constant.LoginType.NATIVE_LOGIN);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);
        return json;

    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String userId) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "favourites");
            jsonObject.accumulate("user_id", userId);

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
        if (v == txtLogin) {

            if (Utility.isEmptyString(editEmail.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_empty), Toast.LENGTH_LONG);
                return;
            }

            if (Utility.isEmptyString(editPassword.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_empty), Toast.LENGTH_LONG);
                return;
            }

            sendServerRequest();

        }
        if (v == txtSignUp) {
            startActivity(new Intent(this, SignUp.class).putExtra(Constant.IntentKey.LOGIN_REQUIRED, true));
            finish();
        }
        if (v == txtForgot) {
            startActivity(new Intent(this, ForgotPassword.class));
        }
        if (v == imageBack) {
            finish();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == checkboxRemember) {

        }
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            DataObject dataObject = (DataObject) data;

            if (checkboxRemember.isChecked()) {

                management.savePreferences(new PrefObject()
                        .setSaveUserRemember(true)
                        .setUserRemember(true));

            }

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


            //Send request to Server for retrieving
            // Specific User Favourites into Local Db

            management.sendRequestToServer(new RequestObject()
                    .setJson(getJson(dataObject.getUserId()))
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                    .setConnection(Constant.CONNECTION.ALL_FAVOURITES)
                    .setConnectionCallback(this));


            finish();


        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Toaster(this, data, Toast.LENGTH_SHORT);
    }
}
