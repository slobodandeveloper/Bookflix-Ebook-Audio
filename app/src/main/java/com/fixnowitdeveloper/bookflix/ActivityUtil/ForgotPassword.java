package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.EditTextUtil.UbuntuRegularEditText;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.TextviewUtil.UbuntuLightTextview;
import com.fixnowitdeveloper.bookflix.TextviewUtil.UbuntuMediumTextview;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private TextView txtMenu;
    private ImageView imageBack;
    private TextView appName;
    private TextView txtInformation;
    private EditText editEmail;
    private TextView txtForgot;
    private Management management;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initUI(); //Initialize UI

    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.forgot_password));

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);

        appName = (TextView) findViewById(R.id.app_name);
        txtInformation = (UbuntuLightTextview) findViewById(R.id.txt_information);
        editEmail = (UbuntuRegularEditText) findViewById(R.id.edit_email);
        txtForgot = (UbuntuMediumTextview) findViewById(R.id.txt_forgot);

        txtForgot.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    /**
     * <p>It is used to send request to server for user authentication</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.FORGOT)
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

            jsonObject.accumulate("functionality","forgot_password");
            jsonObject.accumulate("email", editEmail.getText().toString());


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
        if (v == txtForgot) {

            if (Utility.isEmptyString(editEmail.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_empty), Toast.LENGTH_LONG);
                return;
            }

            sendServerRequest();

        }
        if (v == imageBack) {
            finish();
        }
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (requestObject != null) {

            //Utility.Toaster(this,Utility.getStringFromRes(this,R.string.pas));
            editEmail.setText(null);
        }
        Log.d("Forgot","success"  + data + " " + requestObject);
        Toast.makeText(this,"Se envio el correo",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Log.d("Forgot","error " + data + " " + requestObject);
        Toast.makeText(this,"Error al enviar el correo",Toast.LENGTH_SHORT).show();
    }
}
