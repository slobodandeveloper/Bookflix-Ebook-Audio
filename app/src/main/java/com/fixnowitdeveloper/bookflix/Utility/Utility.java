package com.fixnowitdeveloper.bookflix.Utility;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatDelegate;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.FontUtil.Font;
import com.fixnowitdeveloper.bookflix.ManagementUtil.Management;
import com.fixnowitdeveloper.bookflix.MyApplication;
import com.fixnowitdeveloper.bookflix.ObjectUtil.Base64Object;
import com.fixnowitdeveloper.bookflix.ObjectUtil.DateTimeObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PrefObject;
import com.fixnowitdeveloper.bookflix.R;
import com.ixidev.gdpr.GDPRChecker;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class Utility {


    /**
     * <p>Show the Toast in Activity</p>
     *
     * @param context context of activity or either Fragment
     * @param message your message you want to show in Toast
     * @param length  length of Toast
     */
    public static void Toaster(Context context, String message, int length) {
        Toast.makeText(context, message, length).show();
    }


    /**
     * <p>Show the Message in Logcat</p>
     *
     * @param tag     tag you want to use in your logger
     * @param message message you want to show in logcat
     */
    public static void Logger(String tag, String message) {
        Log.e(tag, message);
    }


    public static void extraData(String TAG, String message) {
        int maxLogSize = 2000;
        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            //Log.e(TAG, message.substring(start, end));
        }
    }


    /**
     * <p>Share your app  with friend & Colleagues</p>
     *
     * @param context reference from the acitivty or fragment
     */
    public static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getStringFromRes(context, R.string.sharetext) + getStringFromRes(context, R.string.app_name) + ": https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    /**
     * <p>Share your app  with friend & Colleagues</p>
     *
     * @param context reference from the acitivty or fragment
     */
    public static void shareApp(Context context, String wallpaperLink) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, wallpaperLink);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    /**
     * <p>Check the Connection status either it is available or not</p>
     *
     * @param context reference from activity or either fragment
     * @return true if internet connection available
     */
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) { // connected to the internet
            ////Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }


    /**
     * <p>Check any specific text either it's null or not</p>
     *
     * @param text text about which we want to know about
     * @return true if text is Empty
     */
    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }


    /**
     * <p>It used to show Interstitial Ad of Admob</p>
     *
     * @param context
     */
    public static void showInterstitialAd(Context context) {

        if (!Constant.Credentials.isInterstitialAdsEnable)
            return;

        final InterstitialAd mInterstitial = new InterstitialAd(context);
        mInterstitial.setAdUnitId(Constant.Credentials.ADMOB_INTERSTITIAL_ID);

        AdRequest.Builder adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID);

        //GDPRChecker.Request request = GDPRChecker.getRequest();

     /*   if (request == GDPRChecker.Request.NON_PERSONALIZED) {
            // load non Personalized ads
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            adRequest.addNetworkExtrasBundle(AdMobAdapter.class, extras);
        }*/ // else do nothing , it will load PERSONALIZED ads

        mInterstitial.loadAd(adRequest.build());
        mInterstitial.show();

        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitial.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Utility.Logger("Interstitial Ads", "Error " + i);
            }
        });

    }


    /**
     * <p>It used to show Interstitial Ad of Admob</p>
     *
     * @param context
     */
    public static void showInterstitialAd(Context context, final ACProgressFlower acProgressFlower) {

        if (!Constant.Credentials.isInterstitialAdsEnable)
            return;

        if (acProgressFlower != null)
            acProgressFlower.show();

        final InterstitialAd mInterstitial = new InterstitialAd(context);
        mInterstitial.setAdUnitId(Constant.Credentials.ADMOB_INTERSTITIAL_ID);

        AdRequest.Builder adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID);

        GDPRChecker.Request request = GDPRChecker.getRequest();

        if (request == GDPRChecker.Request.NON_PERSONALIZED) {
            // load non Personalized ads
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            adRequest.addNetworkExtrasBundle(AdMobAdapter.class, extras);
        } // else do nothing , it will load PERSONALIZED ads

        mInterstitial.loadAd(adRequest.build());
        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                if (acProgressFlower != null && acProgressFlower.isShowing()) {
                    acProgressFlower.dismiss();
                }

                mInterstitial.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Utility.Logger("Interstitial Ads", "Error " + i);

                if (acProgressFlower != null && acProgressFlower.isShowing()) {
                    acProgressFlower.dismiss();
                }

            }
        });

    }


    /**
     * <p>It show alert to User about Internet Availability</p>
     *
     * @param context
     */
    public static void showNoInternetDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        /*dialog.setContentView(R.layout.internet_not_available_dialog);

        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
        TextView txt_description = (TextView) dialog.findViewById(R.id.txt_description);


        LinearLayout layout_close = (LinearLayout) dialog.findViewById(R.id.layout_cancel);
        LinearLayout layout_done = (LinearLayout) dialog.findViewById(R.id.layout_done);

        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        layout_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });*/

        if (dialog == null)
            return;

        dialog.show();

    }


    /**
     * <p>It is used to open playstore app link for rating</p>
     *
     * @param context
     */
    public static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }


    /**
     * <p>It is used to open web url</p>
     *
     * @param context
     * @param url
     */
    public static void openWebUrl(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }


    /**
     * <p>It is used to copy textual data </p>
     *
     * @param context
     * @param text
     */
    public static void copyData(Context context, String text) {

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Place Address", text);
        clipboard.setPrimaryClip(clip);

    }


    /**
     * <p>It is used to open phone dialer with number</p>
     *
     * @param context
     * @param phone
     */
    public static void openDialer(Context context, String phone) {
        String mobileNo = phone;

        Intent intent = new Intent(Intent.ACTION_DIAL).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + mobileNo));
        context.startActivity(intent);
    }


    /**
     * <p>It is used to Save Image into App Private Memorys</p>
     *
     * @param bitmapImage
     * @param name
     * @return
     */
    public static String saveToInternalStorage(Context context, Bitmap bitmapImage, String name) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, name);

        Utility.Logger("Image Path", mypath.toString());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.toString();
    }


    /**
     * <p>It is used to Capitalize the Word first letter</p>
     *
     * @param capString
     * @return
     */
    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }


    /**
     * <p>It is used to get Colour from Resource file</p>
     *
     * @param context
     * @param colour
     * @return
     */
    public static int getColourFromRes(Context context, int colour) {
        return context.getResources().getColor(colour);
    }


    /**
     * <p>It is used to get String values from Resource file</p>
     *
     * @param context
     * @param label
     * @return
     */
    public static String getStringFromRes(Context context, int label) {
        if (context == null || label == 0)
            return null;
        return context.getResources().getString(label);
    }


    /**
     * <p>It is used to get Current Time</p>
     *
     * @return
     */
    public static String getCurrentTime() {
        String date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.TimeDateFormat.timeDateFormat12);
        date = simpleDateFormat.format(new Date());
        return date;
    }


    /**
     * <p>It is used to read Json file from Asset Folder</p>
     *
     * @param context
     * @param textFileName
     * @return
     */
    public static String getFileData(Context context, String textFileName) {
        String strJSON;
        StringBuilder buf = new StringBuilder();
        InputStream json;
        try {
            json = context.getAssets().open(textFileName);

            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));

            while ((strJSON = in.readLine()) != null) {
                buf.append(strJSON);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buf.toString();
    }


    /**
     * <p>It is used to round off value to n decimal points</p>
     *
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    /**
     * <p>It is used to dismiss Keyboard</p>
     *
     * @param context
     * @param editText
     */
    public static void hideKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    /**
     * <p>It is used to set Wallpaper</p>
     *
     * @param context
     * @param uri
     */
    public static void setWallpaper(Context context, Uri uri) {

        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        //intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        //intent.putExtra("mimeType", "image/*");
        context.startActivity(intent);


    }


    /**
     * <p>It is used to share wallpaper with other app</p>
     *
     * @param context
     * @param uri
     */
    public static void shareBook(Context context, Uri uri) {

        Utility.Logger("Sharing Uri", uri.toString());

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        //shareIntent.putExtra(Intent.EXTRA_TEXT, getStringFromRes(context, R.string.download_wallpaper));
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "Share with"));

    }


    /**
     * <p>It is used to parse Time & Date into required format</p>
     *
     * @param
     * @return
     */
    public static DateTimeObject parseTimeDate(DateTimeObject dateTimeObject) {
        DateTimeObject timeObject = null;
        Date dateObject = null;
        String time = null;
        String date = null;


        if (dateTimeObject.getDatetimeType() == Constant.DATETIME.BOTH12) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.TimeDateFormat.timeDateFormat12);

            try {

                if (dateTimeObject.isDateInLong()) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(dateTimeObject.getDateTimeInLong());

                    dateObject = calendar.getTime();

                } else if (dateTimeObject.isCurrentDate()) {
                    dateObject = new Date();
                } else {
                    dateObject = simpleDateFormat.parse(dateTimeObject.getDatetime());
                }

                date = new SimpleDateFormat(Constant.TimeDateFormat.dateFormat12).format(dateObject);
                time = new SimpleDateFormat(Constant.TimeDateFormat.timeFormat12).format(dateObject);

                timeObject = new DateTimeObject().setDate(date).setTime(time).setDatetime(date + " " + time);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else if (dateTimeObject.getDatetimeType() == Constant.DATETIME.BOTH24) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.TimeDateFormat.timeDateFormat24);

            try {

                if (dateTimeObject.isDateInLong()) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(dateTimeObject.getDateTimeInLong());

                    dateObject = calendar.getTime();

                } else if (dateTimeObject.isCurrentDate()) {
                    dateObject = new Date();
                } else {
                    dateObject = simpleDateFormat.parse(dateTimeObject.getDatetime());
                }

                date = new SimpleDateFormat(Constant.TimeDateFormat.dateFormat24).format(dateObject);
                time = new SimpleDateFormat(Constant.TimeDateFormat.timeFormat24).format(dateObject);

                timeObject = new DateTimeObject().setDate(date).setTime(time).setDatetime(date + " " + time);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        return timeObject;
    }


    /**
     * <p>It is used to get calculated values of counters></p>
     *
     * @param counter
     * @return
     */
    public static String getCalculatedValue(String counter) {
        int count = Integer.parseInt(counter);
        double calculatedAmount;
        String k = "k";

        if (count > 999) {
            calculatedAmount = count / 1000;
            k = String.valueOf(calculatedAmount) + "k";
        } else {
            calculatedAmount = count;
            k = String.valueOf((int) calculatedAmount);
        }

        return k;
    }


    /**
     * <p>It is used to check Numeric</p>
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }


    /**
     * <p>It is used to convert Bitmap into String</p>
     *
     * @param image
     * @return
     */
    public static String bitmapIntoString(Bitmap image) {
        if (image == null)
            return null;

        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }


    /**
     * <p>It is used to convert String into Bitmap</p>
     *
     * @param input
     * @return
     */
    public static Bitmap stringIntoBitmap(String input) {

        if (Utility.isEmptyString(input))
            return null;

        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    /**
     * <p>It is used to convert dp into px</p>
     *
     * @param i
     * @return
     */
    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }


    /**
     * <p>It is used to get Current Date Time</p>
     *
     * @param dateTimeObject
     * @return
     */
    public static DateTimeObject getCurrentDateTime(DateTimeObject dateTimeObject) {


        String time;
        String date;
        DateTimeObject dateObject = new DateTimeObject();
        SimpleDateFormat simpleDateFormat = null;

        if (dateTimeObject.getDatetimeType() == Constant.DATETIME.DATE) {

            date = new SimpleDateFormat(Constant.TimeDateFormat.dayFormat).format(new Date());
            dateObject.setDate(date);

        } else if (dateTimeObject.getDatetimeType() == Constant.DATETIME.TIME) {

            time = new SimpleDateFormat(Constant.TimeDateFormat.timeFormat24).format(new Date());
            dateObject.setTime(time);

        }

        return dateObject;

    }


    /**
     * <p>It is used to check either Service running or not</p>
     *
     * @param serviceClassName
     * @return
     */
    public static boolean isServiceRunning(String serviceClassName) {
        final ActivityManager activityManager = (ActivityManager) MyApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isLocked(int pos) {

        boolean isLocked = false;
        //Random r = new Random();
        //int random = r.nextInt(45 - 12) + 12;

        if (pos % 25 == 0 && pos != 0) {
            isLocked = true;
        }

        return isLocked;
    }


    public static String encodeUrl(String url) {
        String encodedText = null;

        try {
            encodedText = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedText;
    }


    /**
     * <p>It is used for conversion of Text & Bitmap into Base64 & vice versa </p>
     *
     * @param base64Object
     * @return
     */

    public static String base64Converter(Base64Object base64Object) {
        String result = null;

        //Utility.Logger("Base64", base64Object.toString());

        if (base64Object.getBitmap() != null && base64Object.isEncode()) {

            float aspectRatio = base64Object.getBitmap().getWidth() /
                    (float) base64Object.getBitmap().getHeight();
            int width = 280;
            int height = Math.round(width / aspectRatio);

            Bitmap yourSelectedImage = Bitmap.createScaledBitmap(
                    base64Object.getBitmap(), width, height, false);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byte[] byteFormat = stream.toByteArray();
            // get the base 64 string
            result = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        } else if (base64Object.isEncode() && base64Object.getText() != null) {

            /*byte[] data = new byte[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                data = base64Object.getText().getBytes(StandardCharsets.UTF_8);
            } else {

                try {
                    data = base64Object.getText().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            result = Base64.encodeToString(data, Base64.NO_WRAP);*/

        } else if (base64Object.isDecode() && base64Object.getText() != null) {

            byte[] data = Base64.decode(base64Object.getText(), Base64.NO_WRAP);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                result = new String(data, StandardCharsets.UTF_8);
            } else {

                try {
                    result = new String(data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }


        Utility.extraData("Base64 Resized = ", result);

        return result;
    }

    public static boolean isImage(String file) {
        return ((file.endsWith(".jpg") || file.endsWith(".png") || file.endsWith(".jpeg") || file.endsWith(".gif"))) ? true : false;

    }

    public static boolean isVideo(String file) {

        return ((file.endsWith(".mp4") || file.endsWith(".avi") || file.endsWith(".mkv"))) ? true : false;

    }


    /**
     * <p>It trigger to Show Interstitial Ad </p>
     *
     * @param context
     */
    public static void loadInterstitialAd(Context context) {
        final ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(Utility.getStringFromRes(context, R.string.load_ad))
                .textTypeface(Font.ubuntu_medium_font(context))
                .fadeColor(Color.DKGRAY).build();
        dialog.setCanceledOnTouchOutside(false);

        if (dialog != null)
            dialog.show();

        if (!Constant.Credentials.isInterstitialAdsEnable)
            return;

        final InterstitialAd mInterstitial = new InterstitialAd(context);
        mInterstitial.setAdUnitId(Constant.Credentials.ADMOB_INTERSTITIAL_ID);

        AdRequest.Builder adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(Constant.Credentials.ADMOB_TEST_DEVICE_ID);
        mInterstitial.loadAd(adRequest.build());
        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();

                mInterstitial.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Utility.Logger("Interstitial Ads", "Error " + i);

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();

            }
        });


    }


    public static int getAuthorCardColour(Context context, int position) {

        final int min = 0;
        final int max = 6;
        position = new Random().nextInt((max - min) + 1) + min;

        int colour = 0;

        if (position == 0) {
            colour = getColourFromRes(context, R.color.yellowEnd);
        } else if (position == 1) {
            colour = getColourFromRes(context, R.color.purpleStart);
        } else if (position == 2) {
            colour = getColourFromRes(context, R.color.blueEnd);
        } else if (position == 3) {
            colour = getColourFromRes(context, R.color.orangeEnd);
        } else if (position == 4) {
            colour = getColourFromRes(context, R.color.orangeStart);
        } else if (position == 5) {
            colour = getColourFromRes(context, R.color.roseStart);
        } else if (position == 6) {
            colour = getColourFromRes(context, R.color.brownStart);
        }

        return colour;

    }


    /**
     * <p>It is used to change app theme </p>
     *
     * @param context
     */
    public static void changeAppTheme(Context context) {
        Management management = new Management(context);
        PrefObject prefObject = management.getPreferences(new PrefObject()
                .setRetrieveNightMode(true));

        if (!prefObject.isNightMode()) {

            context.setTheme(R.style.AppTheme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        } else {

            context.setTheme(R.style.AppTheme_Base_Night);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }

    }


    /**
     * <p>It is used to get Night Mode</p>
     *
     * @param context
     * @return
     */
    public static boolean isNightMode(Context context) {

        Management management = new Management(context);
        PrefObject prefObject = management.getPreferences(new PrefObject()
                .setRetrieveNightMode(true));

        return prefObject.isNightMode();
    }

    /**
     * <p>It is used to get Color from Attribute</p>
     *
     * @param context
     * @param attr
     * @return
     */
    public static int getAttrColor(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attr, typedValue, true);
        @ColorInt int color = typedValue.data;
        return color;
    }


    /**
     * <p>It is used to Playstore Url</p>
     *
     * @param context
     * @return
     */
    public static String getAppPlaystoreUrl(Context context) {
        final String appPackageName = context.getPackageName();
        return "https://play.google.com/store/apps/details?id=" + appPackageName;
    }

    /**
     * <p>It is used to split the String by Space
     * For example "User Name" , after splitting
     * it would be converted into "User" , "Name"</p>
     *
     * @param str
     * @return
     */
    public static String[] splitingBySpace(String str) {
        String[] splited = new String[10];
        if (!str.matches("\\S+")) {
            splited = str.split("\\s+");

        } else {
            splited[0] = str;
        }
        return splited;
    }


    /**
     * <p>Show the Message in Logcat</p>
     *
     * @param tag     tag you want to use in your logger
     * @param message message you want to show in logcat
     */
    public static void LoggerForImportantMessages(String tag, String message) {
        Log.i(tag, message);
    }


}
