package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.fixnowitdeveloper.bookflix.AdapterUtil.FeaturePager;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.CustomUtil.ExtensiblePageIndicator;
import com.fixnowitdeveloper.bookflix.FragmentUtil.OnBoardFragment;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DataObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.OnBoardObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/*import com.ixidev.gdpr.GDPRChecker;*/

public class OnBoarding extends AppCompatActivity implements View.OnClickListener, FacebookCallback<LoginResult>, ConnectionCallback {
    private TextView txtLogin;
    private TextView txtSignUp;
    private TextView txtSkip;
    private ExtensiblePageIndicator flexibleIndicator;
    private ViewPager viewPagerBoarding;
    private FeaturePager featurePager;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private Management management;
    private ImageView imageClose;
    private FirebaseAuth mAuth;
    private LinearLayout layoutFacebook;
    private LoginButton loginButton;
    private CallbackManager mCallbackManager;
    private String TAG = OnBoarding.class.getName();
    private GoogleSignInClient mGoogleSignInClient;
    private LinearLayout layoutGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_on_boarding);

        initUI(); //Initialize UI


    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtLogin = (TextView) findViewById(R.id.txt_login);
        txtSignUp = (TextView) findViewById(R.id.txt_sign_up);
        imageClose = findViewById(R.id.image_close);

        layoutFacebook = findViewById(R.id.layout_facebook);
        layoutGoogle = findViewById(R.id.layout_google);

        management = new Management(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Add On Boarding Data into Arraylist

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ic_graduate
                , Utility.getStringFromRes(this, R.string.title_01)
                , Utility.getStringFromRes(this, R.string.board_01))));

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ic_fav
                , Utility.getStringFromRes(this, R.string.title_02)
                , Utility.getStringFromRes(this, R.string.board_02))));

        fragmentArrayList.add(OnBoardFragment.getFragmentInstance(new OnBoardObject(R.drawable.ic_down
                , Utility.getStringFromRes(this, R.string.title_03)
                , Utility.getStringFromRes(this, R.string.board_03))));

        //Initialize Pager & its indicator as well as connect it with its adapter

        flexibleIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        viewPagerBoarding = (ViewPager) findViewById(R.id.view_pager_boarding);

        featurePager = new FeaturePager(getSupportFragmentManager(), fragmentArrayList);
        viewPagerBoarding.setAdapter(featurePager);

        flexibleIndicator.initViewPager(viewPagerBoarding);


        txtLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        imageClose.setOnClickListener(this);
        layoutFacebook.setOnClickListener(this);
        layoutGoogle.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (v == txtLogin) {
            startActivity(new Intent(this, Login.class));
            finish();
        }
        if (v == txtSignUp) {
            startActivity(new Intent(this, SignUp.class).putExtra(Constant.IntentKey.LOGIN_REQUIRED, true));
            finish();
        }
        if (v == imageClose) {
            finish();
        }
        if (v == layoutFacebook) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        }
        if (v == layoutGoogle) {

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, Constant.RequestCode.GOOGLE_SIGN_IN_CODE);

        }

    }


    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        if (requestCode != Constant.RequestCode.GOOGLE_SIGN_IN_CODE)
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        else
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == Constant.RequestCode.GOOGLE_SIGN_IN_CODE) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Utility.Logger(TAG, "Google sign in failed = " + e);
                    e.printStackTrace();
                    // ...
                }
            }

    }


    /**
     * <p>It is used to handle Facebook access token</p>
     *
     * @param token
     */
    private void handleFacebookAccessToken(AccessToken token) {
        Utility.Logger(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Utility.Logger(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Utility.Logger(TAG, "Name = " + user.getDisplayName() + " Email = " + user.getEmail()
                                    + " Picture = " + user.getPhotoUrl().toString() + " UID = " + user.getUid());

                            String requestJson = getRegisterJson(user.getDisplayName(), user.getEmail(), user.getUid(), user.getPhotoUrl().toString(), Constant.LoginType.FACEBOOK_LOGIN);
                            sendServerRequest(Constant.CONNECTION.SIGN_UP, requestJson);

                        } else {
                            // If sign in fails, display a message to the user.
                            Utility.Logger(TAG, "signInWithCredential:failure " + task.getException());


                        }

                        // ...
                    }
                });
    }


    /**
     * <p>It is used to authenticate Google Sign In</p>
     *
     * @param acct
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Utility.Logger(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Utility.Logger(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Utility.Logger(TAG, "Name = " + user.getDisplayName() + " Picture = " + user.getPhotoUrl()
                                    + " Email = " + user.getEmail() + " Phone = " + user.getPhoneNumber());

                            String requestJson = getRegisterJson(user.getDisplayName(), user.getEmail(), user.getUid(), user.getPhotoUrl().toString(), Constant.LoginType.GOOGLE_LOGIN);
                            sendServerRequest(Constant.CONNECTION.SIGN_UP, requestJson);

                        } else {
                            // If sign in fails, display a message to the user.
                            Utility.Logger(TAG, "signInWithCredential:failure " + task.getException());

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Utility.Logger(TAG, "facebook:onSuccess : " + loginResult);
        handleFacebookAccessToken(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {
        Utility.Logger(TAG, "facebook:onCancel");
        // ...
    }

    @Override
    public void onError(FacebookException error) {
        Utility.Logger(TAG, "facebook:onError = " + error);
        // ...
    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {
            if (requestObject.getConnection() == Constant.CONNECTION.SIGN_UP) {

                DataObject dataObject = (DataObject) data;

                management.savePreferences(new PrefObject()
                        .setSaveLogin(true)
                        .setLogin(true));

                management.savePreferences(new PrefObject()
                        .setSaveUserCredential(true)
                        .setUid(dataObject.getUid())
                        .setLoginType(dataObject.getLoginType())
                        .setUserId(dataObject.getUserId())
                        .setFirstName(dataObject.getFirstName())
                        .setLastName(dataObject.getLastName())
                        .setUserPassword(dataObject.getPassword())
                        .setUserEmail(dataObject.getEmail())
                        .setPictureUrl(dataObject.getPicture()));

                finish();

            }
        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {

    }


    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getRegisterJson(String userName, String userEmail, String userUID, String picture, String loginType) {
        String json = "";

        String[] name = Utility.splitingBySpace(userName);
        String fName = name[0];
        String lName = name.length > 1 ? name[1] : name[0];

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "register");
            jsonObject.accumulate("first_name", fName);
            jsonObject.accumulate("last_name", lName);
            jsonObject.accumulate("email", userEmail);
            jsonObject.accumulate("uid", userUID);
            jsonObject.accumulate("picture", picture);
            jsonObject.accumulate("userType", loginType);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.extraData("JSON", json);
        return json;

    }


    /**
     * <p>It is used to send request to server for user registration
     * in case of social login</p>
     */
    private void sendServerRequest(Constant.CONNECTION connection, String json) {

        management.sendRequestToServer(new RequestObject()
                .setJson(json)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(connection)
                .setConnectionCallback(this));

    }

}
