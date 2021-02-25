package com.fixnowitdeveloper.bookflix.ConstantUtil;


import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.PlayerCallback;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.UpdateCallback;
import com.fixnowitdeveloper.bookflix.ObjectUtil.GlobalObject;

public class Constant {

    public static UpdateCallback updateCallback;

    public static ConnectionCallback connectionCallback;

    public static PlayerCallback playerCallback;

    public static GlobalObject globalObject;

    public static int adInterval = 0;

    public static enum DB {RETRIEVE, INSERT, DELETE, UPDATE, SPECIFIC_BOOK, SPECIFIC_TYPE, DELETE_FAVOURITES, SPECIFIC_BOOK_BY_NAME, DELETE_DATA_SPECIFIC_USER}

    public static enum CONNECTION {
        TRENDING_PHOTOS_URL, TRENDING_VIDEO_URL, ALL_CATEGORIES, CATEGORIZED_PHOTOS, CATEGORIZED_VIDEOS, POPULAR, NEWS_FEED, ARTIST_DETAIL, BOOK_DETAIL,
        SEARCH, LOGIN, SIGN_UP, FORGOT, UPDATE, ADD_COMMENT, ALL_COMMENT, ADD_FAVOURITES, DELETE_FAVOURITES, ALL_FAVOURITES, SPECIFIC_AUTHOR_DETAIL, SPECIFIC_BOOK,
        LIKE_DISLIKES, PRIVACY_POLICY, LIST_OF_PICTURE, NEXT_PAGE, DOWNLOAD, IMAGE_STATUS, VIDEO_STATUS, HOME, SINGLE_STATION, ADMOB, ALL_ARTIST, REPORT
    }

    public static enum CONNECTION_TYPE {BACKGROUND, UI, DOWNLOAD, STATUS}

    public static enum DATA_TYPE {ARTIST, COMMON, POPULAR, CATEGORIES, FEED, FEATURED, REVIEW, HISTORY, MY_ADDED_BOOKS}

    public static enum TYPE {FAVOURITES, HISTORY, DOWNLOAD, FILE_READING_STATUS, MY_ADDED_BOOKS_STATUS}

    public static enum DATETIME {DATE, TIME, HOUR24, HOUR12, BOTH12, BOTH24, LONG}

    public static enum REQUEST {
        GET, POST
    }


    /**
     * <p>It contain all Server Url</p>
     */
    public static class ServerInformation {
        public static String GOOGLE_DRIVE_LINK = "https://docs.google.com/uc?id=";
        public static String PRIVACY_URL = "https://resources.fixnowit.com/privacy-police/Bookflix-privacy.html";
        public static String FACEBOOK_HIGH_PIXEL_URL = "?type=large&redirect=true&width=300&height=300";
        public static String DEFFERED_DEEP_LINK_URL = "https://bookflix.fixnowit.page.link";

        static String BASE_URL = "https://bookflix.fixnowit.com";
        static String FOLDER = "/";

        public static String SEARCH_PHOTO_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String TRENDING_PHOTOS_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String TRENDING_VIDEOS_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String ALL_CATEGORIES_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String CATEGORIZED_PHOTOS_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String CATEGORIZED_VIDEOS_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String LIKES_DISLIKES_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String COMMENT_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String PRIVACY_POLICY_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String LOGIN_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String REGISTER_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String FORGOT_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String UPDATE_PROFILE_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String ALL_FAVOURITES_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String ADD_FAVOURITES_URL = BASE_URL + FOLDER + "books4u_home.php";
        public static String DELETE_FAVOURITES_URL = BASE_URL + FOLDER + "books4u_home.php";

        public static String PICTURE_URL = BASE_URL + FOLDER + "admin/uploads/image/";
        public static String VIDEO_URL = BASE_URL + FOLDER + "admin/uploads/video/";

        public static String DESKTOP_NEW_LINKS = BASE_URL + FOLDER + "books4u.php?post_id=%s&post_type=%s&play_store=%s";


    }


    /**
     * <p>It contain all of the Credentials </p>
     */
    public static class Credentials {

        public static String ADMOB_APP_ID = "ADMOB_APP_ID";
        public static String PUBLISHER_ID = "PUBLISHER_ID";
        public static String ADMOB_TEST_DEVICE_ID = "ADMOB_TEST_DEVICE_ID";
        public static String REWARDED_VIDEO_ADS = "ca-app-pub-3940256099942544/5224354917";
        public static String ADMOB_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712";
        public static String ADMOB_BANNER_ID = "ca-app-pub-3940256099942544/1033173712";
        public static String FB_AD_PLACEMENT_ID = "IMG_16_9_APP_INSTALL#2191812507527503_2205446212830799";

        public static boolean isAdmobBannerAds = true;
        public static boolean isAdmobNativeBookDetail = true;
        public static boolean isAdmobNativeAuthorDetail = true;
        public static boolean isInterstitialAdsEnable = true;

        public static boolean isFbNativeAds = false;
        public static boolean isScreenInterstitialAdsEnable = true;
        public static boolean isRewardedVideoEnable = true;
        public static boolean isSingleStation = false;
        public static boolean isFacebookHashKeyRequired = true;
        public static int screenInterstitialAdsCount = 5;
        public static int nativeAdInterval = 6;

    }


    /**
     * <p>It contain all of the Important Messages</p>
     */
    public static class ImportantMessages {
        public static final String CONNECTION_ERROR = "Connection Error";

    }


    /**
     * <p>It contain all of the Toast messages</p>
     */
    public static class ToastMessage {
        public static String NO_INTERNET_MESSAGE = "No Internet Connection";
        public static String EMPTY_BOX = "Kindly write any word";
        public static String NO_DATA = "No Result";
    }


    /**
     * <p>It contain all of the Key of Share Preferences</p>
     */
    public static class SharedPref {
        public static String PREF_TAGS = "TAGS";
        public static String NEXT_URL = "NEXT_URL";
        public static String POSITION = "POSITION";
        public static String FIRST_LAUNCH = "FIRST_LAUNCH";
        public static String LOGIN = "LOGIN";
        public static String USER_ID = "USER_ID";
        public static String USER_REMEMBER = "USER_REMEMBER";
        public static String USER_EMAIL = "USER_EMAIL";
        public static String USER_PASSWORD = "USER_PASSWORD";
        public static String USER_FIRST_NAME = "USER_FIRST_NAME";
        public static String USER_LAST_NAME = "USER_LAST_NAME";
        public static String USER_PICTURE = "USER_PICTURE";
        public static String NEWS_FEED = "NEWS_FEED";
        public static String NIGHT_MODE = "NIGHT_MODE";
        public static String DOWNLOAD_WIFI = "DOWNLOAD_WIFI";
        public static String PUSH_NOTIFICATION = "PUSH_NOTIFICATION";
        public static String LOGIN_TYPE = "LOGIN_TYPE";
        public static String UID = "UID";
        public static String MY_SAVED_BOOKS = "MY_SAVED_BOOKS";
    }


    /**
     * <p>It contain all of the Request Code</p>
     */
    public static class RequestCode {
        public static int PERMISSION_REQUEST_CODE = 0;
        public static int REQUEST_CODE_GALLERY = 1;
        public static int REQUEST_CODE_CAMERA = 2;
        public static int PERMISSION_OVERLAY_PERMISSION = 3;
        public static int REQUEST_CODE_PERSON = 4;
        public static int REQUEST_CODE_TITLE = 5;
        public static int OVERLAY_PERMISSION_REQ_CODE = 6;
        public static int PLAYLIST_CODE = 7;
        public static int GOOGLE_SIGN_IN_CODE = 8;
    }


    /**
     * <p>It contain all of the Key of Intent Sharing</p>
     */
    public static class IntentKey {
        public static String REQUEST_OBJECT = "REQUEST_OBJECT";
        public static String CATEGORY = "CATEGORY";
        public static String CATEGORY_ID = "CATEGORY_ID";
        public static String FUNCTIONALITY = "FUNCTIONALITY";
        public static String POST_TYPE = "POST_TYPE";
        public static String POST_ID = "POST_ID";
        public static String FILE_PATH = "FILE_PATH";
        public static String ARTIST_ID = "ARTIST_ID";
        public static String ARTIST_NAME = "ARTIST_NAME";
        public static String PLAYLIST = "PLAYLIST";
        public static String PLAYLIST_ID = "PLAYLIST_ID";
        public static String PLAYLIST_NAME = "PLAYLIST_NAME";
        public static String CONNECTION = "CONNECTION";
        public static String ARTIST_WORK = "ARTIST_WORK";
        public static String ARTIST_DETAIL = "ARTIST_DETAIL";
        public static String BOOK_DETAIL = "BOOK_DETAIL";
        public static String BOOK_URL = "BOOK_URL";
        public static String BOOK_TYPE = "BOOK_TYPE";
        public static String MY_ADDED_BOOK = "MY_ADDED_BOOK";
        public static String ON_BOARD = "ON_BOARD";
        public static String LOGIN_REQUIRED = "LOGIN_REQUIRED";
        public static String CONTINUE_REQUIRED = "CONTINUE_REQUIRED";
        public static String BACK_ACTION = "BACK_ACTION";
    }


    /**
     * <p>It contain al lof the database columns</p>
     */
    public static class DatabaseColumn {
        public static final String TAG = "Database";
        public static String TABLE_NAME = "Favourites";
        public static String COLUMN_ID = "id";
        public static String COLUMN_BOOK_TITLE = "title";
        public static String COLUMN_ARTIST_NAME = "artist_name";
        public static String COLUMN_COVER_URL = "cover_url";
        public static String COLUMN_BOOK_URL = "book_url";
        public static String COLUMN_USER_ID = "user_id";
        public static String COLUMN_BOOK_ID = "book_id";
        public static String COLUMN_TWITTER_URL = "twitter_url";


        public static String FILE_TABLE_NAME = "ReadFiles";
        public static String FILE_ID_COLUMN = "id";
        public static String FILE_TITLE_COLUMN = "title";
        public static String FILE_TYPE_COLUMN = "file_type";
        public static String FILE_URL_COLUMN = "file_url";
        public static String FILE_PAGES_COLUMN = "total_pages";
        public static String FILE_READ_PAGES_COLUMN = "current_pages";
        public static String FILE_COVER_COLUMN = "book_cover";


        public static String DOWNLOAD_TABLE_NAME = "Download";
        public static String DOWNLOAD_COLUMN_ID = "id";
        public static String DOWNLOAD_COLUMN_FILE_TYPE = "file_type";
        public static String DOWNLOAD_COLUMN_TITLE = "title";
        public static String DOWNLOAD_COLUMN_ARTIST_NAME = "artist_name";
        public static String DOWNLOAD_COLUMN_COVER_URL = "cover_url";
        public static String DOWNLOAD_COLUMN_MEDIA_URL = "stream_url";

        public static String PLAYLIST_MP3_TABLE_NAME = "Mp3";
        public static String PLAYLIST_MP3_COLUMN_ID = "id";
        public static String PLAYLIST_MP3_COLUMN_SERVER_ID = "server_id";
        public static String PLAYLIST_MP3_COLUMN_PLAYLIST_ID = "playlist_id";
        public static String PLAYLIST_MP3_COLUMN_TITLE = "title";
        public static String PLAYLIST_MP3_COLUMN_ARTIST_NAME = "artist_name";
        public static String PLAYLIST_MP3_COLUMN_COVER_URL = "cover_url";
        public static String PLAYLIST_MP3_COLUMN_MEDIA_URL = "stream_url";

        public static String MY_ADDED_BOOK_TABLE_NAME = "MyAddedBooks";
        public static String MY_ADDED_BOOK_ID_COLUMN = "id";
        public static String MY_ADDED_BOOK_TITLE_COLUMN = "title";
        public static String MY_ADDED_BOOK_TYPE_COLUMN = "file_type";
        public static String MY_ADDED_BOOK_URL_COLUMN = "file_url";
        public static String MY_ADDED_BOOK_PAGES_COLUMN = "total_pages";
        public static String MY_ADDED_BOOK_READ_PAGES_COLUMN = "current_pages";
        public static String MY_ADDED_BOOK_COVER_COLUMN = "book_cover";

    }


    /**
     * <p>It contain list of Time & Date Formats</p>
     */
    public static class TimeDateFormat {
        public static String timeDateFormat24 = "dd/MM/yyyy hh:mm";
        public static String dateFormat24 = "dd MMM yyyy";
        public static String timeFormat24 = "hh:mm";

        public static String timeDateFormat12 = "dd/MM/yyyy hh:mm a";
        public static String dateFormat12 = "dd MMM yyyy";
        public static String timeFormat12 = "hh:mm a";

        public static String hourFormat = "hh a";
        public static String dayFormat = "E , MMMM dd";
        public static String internationalTimeFormat = "hh:mm";
    }


    /**
     * <p>It contain all of error code which may come from Server Request</p>
     */
    public static class ErrorCodes {
        public static String success_code = "202";
        public static String error_code = "206";
    }


    public static class DataType {
        public static String FAVOURITES = "FAVOURITES";
        public static String DOWNLOAD = "DOWNLOAD";
        public static String TRENDING = "TRENDING";
        public static String FILE_READING_STATUS = "FILE_READING_STATUS";
        public static String PDF = "PDF";
        public static String EPUB = "EPUB";
        public static String MY_ADDED_BOOKS = "MY_ADDED_BOOKS";
    }


    public static class PostType {
        public static String AUTHOR_TYPE = "author";
        public static String POST_TYPE = "book";

    }


    /**
     * <p>It consist list of difficulty level</p>
     */
    public static class LoginType {

        public static String NATIVE_LOGIN = "native";
        public static String FACEBOOK_LOGIN = "facebook";
        public static String GOOGLE_LOGIN = "google";

    }

}
