package com.fixnowitdeveloper.bookflix.ObjectUtil;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.JsonUtil.CategoriesUtil.CategoriesJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.CategoriesUtil.Category;
import com.fixnowitdeveloper.bookflix.JsonUtil.CommentUtil.Comment;
import com.fixnowitdeveloper.bookflix.JsonUtil.CommentUtil.CommentJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.FavouriteUtil.Favourite;
import com.fixnowitdeveloper.bookflix.JsonUtil.FavouriteUtil.FavouriteJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.PrivacyPolicyUtil.PrivacyPolicyJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.TrendingUtil.Artist;
import com.fixnowitdeveloper.bookflix.JsonUtil.TrendingUtil.Featured;
import com.fixnowitdeveloper.bookflix.JsonUtil.TrendingUtil.Newly;
import com.fixnowitdeveloper.bookflix.JsonUtil.TrendingUtil.Trending;
import com.fixnowitdeveloper.bookflix.JsonUtil.TrendingUtil.TrendingJson;
import com.fixnowitdeveloper.bookflix.JsonUtil.UserUtil.UserJson;
import com.fixnowitdeveloper.bookflix.MyApplication;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import java.util.ArrayList;

public class DataObject implements Parcelable {

    /*Variable for Wallpaper Object*/

    private String id;
    private String nextPage;
    private String portraitUrl;
    private String medium;
    private String small;
    private String tiny;
    private String large;
    private String tags;
    private String type;
    private String pictureid;
    private boolean isLocked = false;
    private ArrayList<DataObject> wallpaperList = new ArrayList<>();
    private ArrayList<Object> homeList = new ArrayList<>();

    /*Variable for History*/

    private String historyId;
    private String historyTags;
    private String historyUrl;

    /*Variable for History*/

    private String myAddedBookId;
    private String myAddedBookTags;
    private String myAddedBookUrl;

    /*Variable for Videos*/

    private String uid;
    private String loginType;
    private String postId;
    private String catId;
    private String artistName;
    private String streamName;
    private String artistId;
    private String title;
    private String description;
    private String bookUrl;
    private String postType;
    private String coverUrl;
    private String originalUrl;
    private String webUrl;
    private String fbUrl;
    private String twitterUrl;
    private String likes;
    private String dislikes;
    private String downloads;
    private String comments;
    private String rating;

    /*Variable for Categories*/

    private String categoryTitle;
    private String categoryPicture;

    /*Variable for User*/

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String picture;
    private String password;

    /*Variable for Message*/

    private String code;
    private String message;

    private String comment;
    private String privacyPolicy;

    private String admobAppId;
    private String admobBannerId;
    private String admobInterstitialId;
    private String admobPublisherId;
    private String admobPrivacyUrl;

    private String playlistId;
    private String serverId;

    private Uri downloadUri;
    private ArrayList<String> fileList = new ArrayList<>();

    private Constant.DATA_TYPE dataType;

    private String authorWork;
    private String authorDescription;
    private String bookCount;
    private String downloadCount;
    private String reviewCount;

    private String reviewPersonName;
    private String reviewPersonRating;
    private String reviewPersonReview;
    private String reviewPersonPicture;

    private String fileType;
    private String bookPage;
    private String currentPage;

    private boolean isList = true;
    private boolean isLongTap = false;


    public String getAdmobPublisherId() {
        return admobPublisherId;
    }

    public DataObject setAdmobPublisherId(String admobPublisherId) {
        this.admobPublisherId = admobPublisherId;
        return this;
    }

    public String getAdmobPrivacyUrl() {
        return admobPrivacyUrl;
    }

    public DataObject setAdmobPrivacyUrl(String admobPrivacyUrl) {
        this.admobPrivacyUrl = admobPrivacyUrl;
        return this;
    }

    public boolean isLongTap() {
        return isLongTap;
    }

    public DataObject setLongTap(boolean longTap) {
        isLongTap = longTap;
        return this;
    }

    public boolean isList() {
        return isList;
    }

    public DataObject setList(boolean list) {
        isList = list;
        return this;
    }

    public String getNextPage() {
        return nextPage;
    }

    public DataObject setNextPage(String nextPage) {
        this.nextPage = nextPage;
        return this;
    }


    public String getBookPage() {
        return bookPage;
    }

    public DataObject setBookPage(String bookPage) {
        this.bookPage = bookPage;
        return this;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public DataObject setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public DataObject setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public DataObject setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
        return this;
    }

    public ArrayList<DataObject> getWallpaperList() {
        return wallpaperList;
    }

    public DataObject setWallpaperList(ArrayList<DataObject> wallpaperList) {
        this.wallpaperList = wallpaperList;
        return this;
    }

    public ArrayList<Object> getHomeList() {
        return homeList;
    }

    public DataObject setHomeList(ArrayList<Object> homeList) {
        this.homeList = homeList;
        return this;
    }

    public String getMedium() {
        return medium;
    }

    public DataObject setMedium(String medium) {
        this.medium = medium;
        return this;
    }

    public String getSmall() {
        return small;
    }

    public DataObject setSmall(String small) {
        this.small = small;
        return this;
    }

    public String getTiny() {
        return tiny;
    }

    public DataObject setTiny(String tiny) {
        this.tiny = tiny;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public DataObject setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public String getArtistName() {
        return artistName;
    }

    public DataObject setArtistName(String artistName) {
        this.artistName = artistName;
        return this;
    }

    public String getStreamName() {
        return streamName;
    }

    public DataObject setStreamName(String streamName) {
        this.streamName = streamName;
        return this;
    }

    public String getType() {
        return type;
    }

    public DataObject setType(String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public DataObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getServerId() {
        return serverId;
    }

    public DataObject setServerId(String serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getHistoryId() {
        return historyId;
    }

    public DataObject setHistoryId(String historyId) {
        this.historyId = historyId;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public DataObject setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getHistoryTags() {
        return historyTags;
    }

    public DataObject setHistoryTags(String historyTags) {
        this.historyTags = historyTags;
        return this;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public DataObject setWebUrl(String webUrl) {
        this.webUrl = webUrl;
        return this;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public DataObject setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
        return this;
    }

    public String getFbUrl() {
        return fbUrl;
    }

    public DataObject setFbUrl(String fbUrl) {
        this.fbUrl = fbUrl;
        return this;
    }

    public String getArtistId() {
        return artistId;
    }

    public DataObject setArtistId(String artistId) {
        this.artistId = artistId;
        return this;
    }

    public String getHistoryUrl() {
        return historyUrl;
    }

    public DataObject setHistoryUrl(String historyUrl) {
        this.historyUrl = historyUrl;
        return this;
    }

    public String getMyAddedBookId() {
        return myAddedBookId;
    }

    public void setMyAddedBookId(String myAddedBookId) {
        this.myAddedBookId = myAddedBookId;
    }

    public String getMyAddedBookTags() {
        return myAddedBookTags;
    }

    public void setMyAddedBookTags(String myAddedBookTags) {
        this.myAddedBookTags = myAddedBookTags;
    }

    public String getMyAddedBookUrl() {
        return myAddedBookUrl;
    }

    public void setMyAddedBookUrl(String myAddedBookUrl) {
        this.myAddedBookUrl = myAddedBookUrl;
    }

    public String getLarge() {
        return large;
    }

    public DataObject setLarge(String large) {
        this.large = large;
        return this;
    }

    public String getPictureid() {
        return pictureid;
    }

    public DataObject setPictureid(String pictureid) {
        this.pictureid = pictureid;
        return this;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public DataObject setLocked(boolean locked) {
        isLocked = locked;
        return this;
    }

    public String getCatId() {
        return catId;
    }

    public DataObject setCatId(String catId) {
        this.catId = catId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DataObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DataObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPostType() {
        return postType;
    }

    public DataObject setPostType(String postType) {
        this.postType = postType;
        return this;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public DataObject setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public DataObject setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
        return this;
    }

    public String getLikes() {
        return likes;
    }

    public DataObject setLikes(String likes) {
        this.likes = likes;
        return this;
    }

    public String getDislikes() {
        return dislikes;
    }

    public DataObject setDislikes(String dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    public String getDownloads() {
        return downloads;
    }

    public DataObject setDownloads(String downloads) {
        this.downloads = downloads;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public DataObject setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public DataObject setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
        return this;
    }

    public String getCategoryPicture() {
        return categoryPicture;
    }

    public DataObject setCategoryPicture(String categoryPicture) {
        this.categoryPicture = categoryPicture;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public DataObject setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public DataObject setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public DataObject setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public DataObject setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public DataObject setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DataObject setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPostId() {
        return postId;
    }

    public DataObject setPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public DataObject setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DataObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public DataObject setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public DataObject setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
        return this;
    }

    public Uri getDownloadUri() {
        return downloadUri;
    }

    public DataObject setDownloadUri(Uri downloadUri) {
        this.downloadUri = downloadUri;
        return this;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public DataObject setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
        return this;
    }

    public ArrayList<String> getFileList() {
        return fileList;
    }

    public DataObject setFileList(ArrayList<String> fileList) {
        this.fileList = fileList;
        return this;
    }

    public DataObject setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
        return this;
    }

    public String getAdmobAppId() {
        return admobAppId;
    }

    public DataObject setAdmobAppId(String admobAppId) {
        this.admobAppId = admobAppId;
        return this;
    }

    public String getAdmobBannerId() {
        return admobBannerId;
    }

    public DataObject setAdmobBannerId(String admobBannerId) {
        this.admobBannerId = admobBannerId;
        return this;
    }

    public String getAdmobInterstitialId() {
        return admobInterstitialId;
    }

    public DataObject setAdmobInterstitialId(String admobInterstitialId) {
        this.admobInterstitialId = admobInterstitialId;
        return this;
    }

    public Constant.DATA_TYPE getDataType() {
        return dataType;
    }

    public DataObject setDataType(Constant.DATA_TYPE dataType) {
        this.dataType = dataType;
        return this;
    }

    public String getAuthorWork() {
        return authorWork;
    }

    public DataObject setAuthorWork(String authorWork) {
        this.authorWork = authorWork;
        return this;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public DataObject setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
        return this;
    }

    public String getBookCount() {
        return bookCount;
    }

    public DataObject setBookCount(String bookCount) {
        this.bookCount = bookCount;
        return this;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public DataObject setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
        return this;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public DataObject setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
        return this;
    }

    public String getReviewPersonName() {
        return reviewPersonName;
    }

    public DataObject setReviewPersonName(String reviewPersonName) {
        this.reviewPersonName = reviewPersonName;
        return this;
    }

    public String getReviewPersonRating() {
        return reviewPersonRating;
    }

    public DataObject setReviewPersonRating(String reviewPersonRating) {
        this.reviewPersonRating = reviewPersonRating;
        return this;
    }

    public String getReviewPersonReview() {
        return reviewPersonReview;
    }

    public DataObject setReviewPersonReview(String reviewPersonReview) {
        this.reviewPersonReview = reviewPersonReview;
        return this;
    }

    public String getReviewPersonPicture() {
        return reviewPersonPicture;
    }

    public DataObject setReviewPersonPicture(String reviewPersonPicture) {
        this.reviewPersonPicture = reviewPersonPicture;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public DataObject setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getLoginType() {
        return loginType;
    }

    public DataObject setLoginType(String loginType) {
        this.loginType = loginType;
        return this;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "id='" + id + '\'' +
                ", nextPage='" + nextPage + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", medium='" + medium + '\'' +
                ", small='" + small + '\'' +
                ", tiny='" + tiny + '\'' +
                ", large='" + large + '\'' +
                ", tags='" + tags + '\'' +
                ", type='" + type + '\'' +
                ", pictureid='" + pictureid + '\'' +
                ", isLocked=" + isLocked +
                ", wallpaperList=" + wallpaperList +
                ", historyId='" + historyId + '\'' +
                ", historyTags='" + historyTags + '\'' +
                ", historyUrl='" + historyUrl + '\'' +
                ", postId='" + postId + '\'' +
                ", catId='" + catId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", postType='" + postType + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", likes='" + likes + '\'' +
                ", dislikes='" + dislikes + '\'' +
                ", downloads='" + downloads + '\'' +
                ", comments='" + comments + '\'' +
                ", categoryTitle='" + categoryTitle + '\'' +
                ", categoryPicture='" + categoryPicture + '\'' +
                ", userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", comment='" + comment + '\'' +
                ", privacyPolicy='" + privacyPolicy + '\'' +
                ", downloadUri=" + downloadUri +
                ", fileList=" + fileList +
                '}';
    }

    public static DataObject getWallpaperObject(RequestObject requestObject, Object data) {

        DataObject dataObject = null;
        String nextPage = null;

        if (requestObject.getConnection() == Constant.CONNECTION.HOME) {

            //region Home Screen data parsing functionality

            ArrayList<DataObject> trendingWallpaperList = new ArrayList<>();
            ArrayList<Object> homeList = new ArrayList<>();
            TrendingJson trendingPhotosJson = (TrendingJson) data;
            HomeObject homeObject = null;

            for (int i = 0; i < trendingPhotosJson.getTrending().size(); i++) {

                Trending trending = trendingPhotosJson.getTrending().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(trending.getId())
                        .setStreamName(trending.getTitle())
                        .setArtistName(trending.getArticleName())
                        .setWebUrl(trending.getWebUrl())
                        .setFbUrl(trending.getFbUrl())
                        .setTwitterUrl(trending.getTwitterUrl())
                        .setBookUrl(trending.getBookUrl())
                        .setCatId(trending.getCatId())
                        .setTitle(trending.getTitle())
                        .setTags(trending.getTags())
                        .setDescription(trending.getDescription())
                        .setPostType(trending.getPostType())
                        .setCoverUrl(trending.getCoverUrl())
                        .setOriginalUrl(trending.getOriginalUrl())
                        .setLikes(trending.getLikes())
                        .setDislikes(trending.getDislikes())
                        .setDownloads(trending.getDownloads())
                        .setDataType(Constant.DATA_TYPE.POPULAR)
                        .setRating(trending.getRating())
                        .setComments(String.valueOf(trending.getComments())));

            }

            if (trendingWallpaperList.size() > 0) {
                homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.top))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.trending))
                        .setData_type(Constant.DATA_TYPE.POPULAR)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));
                homeList.add(homeObject);
            }
            trendingWallpaperList.clear();


            for (int i = 0; i < trendingPhotosJson.getCategories().size(); i++) {

                Category category = trendingPhotosJson.getCategories().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(category.getId())
                        .setCategoryTitle(category.getCategory())
                        .setDataType(Constant.DATA_TYPE.CATEGORIES)
                        .setCategoryPicture(category.getPictureUrl()));

            }

            if (trendingWallpaperList.size() > 0) {

                homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.top))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.collection))
                        .setData_type(Constant.DATA_TYPE.CATEGORIES)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));
                homeList.add(homeObject);

            }
            trendingWallpaperList.clear();


            for (int i = 0; i < trendingPhotosJson.getNewly().size(); i++) {

                Newly trending = trendingPhotosJson.getNewly().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(trending.getId())
                        .setStreamName(trending.getTitle())
                        .setArtistName(trending.getArticleName())
                        .setWebUrl(trending.getWebUrl())
                        .setFbUrl(trending.getFbUrl())
                        .setTwitterUrl(trending.getTwitterUrl())
                        .setBookUrl(trending.getBookUrl())
                        .setCatId(trending.getCatId())
                        .setTitle(trending.getTitle())
                        .setTags(trending.getTags())
                        .setDescription(trending.getDescription())
                        .setPostType(trending.getPostType())
                        .setCoverUrl(trending.getCoverUrl())
                        .setOriginalUrl(trending.getOriginalUrl())
                        .setLikes(trending.getLikes())
                        .setDislikes(trending.getDislikes())
                        .setDownloads(trending.getDownloads())
                        .setDataType(Constant.DATA_TYPE.FEED)
                        .setRating(trending.getRating())
                        .setComments(trending.getComments().toString()));

            }

            if (trendingWallpaperList.size() > 0) {

                homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.newly))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.for_you))
                        .setData_type(Constant.DATA_TYPE.COMMON)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));
                homeList.add(homeObject);


                /*homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.newly))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.streams))
                        .setData_type(Constant.DATA_TYPE.FEED)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));*/

                homeList.addAll(new ArrayList<>(trendingWallpaperList));

            }
            trendingWallpaperList.clear();


            for (int i = 0; i < trendingPhotosJson.getArtist().size(); i++) {

                Artist trending = trendingPhotosJson.getArtist().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(trending.getId())
                        .setTitle(trending.getCategory())
                        .setAuthorWork(trending.getBookCount())
                        .setAuthorDescription(trending.getDescription())
                        .setBookCount(trending.getBookCount())
                        .setDownloadCount(trending.getDownloadcount())
                        .setReviewCount(trending.getReviewCount())
                        .setDataType(Constant.DATA_TYPE.ARTIST)
                        .setOriginalUrl(trending.getPictureUrl()));

            }

            if (trendingWallpaperList.size() > 0) {
                homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.top))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.artist))
                        .setData_type(Constant.DATA_TYPE.ARTIST)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));
                homeList.add(homeObject);
            }
            trendingWallpaperList.clear();


            for (int i = 0; i < trendingPhotosJson.getFeatured().size(); i++) {

                Featured trending = trendingPhotosJson.getFeatured().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(trending.getId())
                        .setStreamName(trending.getTitle())
                        .setArtistName(trending.getArticleName())
                        .setWebUrl(trending.getWebUrl())
                        .setFbUrl(trending.getFbUrl())
                        .setTwitterUrl(trending.getTwitterUrl())
                        .setBookUrl(trending.getBookUrl())
                        .setCatId(trending.getCatId())
                        .setTitle(trending.getTitle())
                        .setTags(trending.getTags())
                        .setDescription(trending.getDescription())
                        .setPostType(trending.getPostType())
                        .setCoverUrl(trending.getCoverUrl())
                        .setOriginalUrl(trending.getOriginalUrl())
                        .setLikes(trending.getLikes())
                        .setDislikes(trending.getDislikes())
                        .setDownloads(trending.getDownloads())
                        .setDataType(Constant.DATA_TYPE.FEATURED)
                        .setRating(trending.getRating())
                        .setComments(String.valueOf(trending.getComments())));

            }

            if (trendingWallpaperList.size() > 0) {
                homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.featured))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.special))
                        .setData_type(Constant.DATA_TYPE.FEATURED)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));
                homeList.add(homeObject);
            }
            trendingWallpaperList.clear();


            //endregion

            dataObject = new DataObject()
                    .setCode(trendingPhotosJson.getCode())
                    .setMessage(trendingPhotosJson.getMessage())
                    .setHomeList(homeList);

        } else if (requestObject.getConnection() == Constant.CONNECTION.TRENDING_PHOTOS_URL
                || requestObject.getConnection() == Constant.CONNECTION.TRENDING_VIDEO_URL
                || requestObject.getConnection() == Constant.CONNECTION.CATEGORIZED_PHOTOS
                || requestObject.getConnection() == Constant.CONNECTION.CATEGORIZED_VIDEOS
                || requestObject.getConnection() == Constant.CONNECTION.SEARCH
                || requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_BOOK
                || requestObject.getConnection() == Constant.CONNECTION.SINGLE_STATION
                || requestObject.getConnection() == Constant.CONNECTION.POPULAR
                || requestObject.getConnection() == Constant.CONNECTION.NEWS_FEED) {

            //region All parsing functionality

            ArrayList<DataObject> trendingWallpaperList = new ArrayList<>();
            TrendingJson trendingPhotosJson = (TrendingJson) data;

            for (int i = 0; i < trendingPhotosJson.getTrending().size(); i++) {

                Trending trending = trendingPhotosJson.getTrending().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(trending.getId())
                        .setStreamName(trending.getTitle())
                        .setArtistName(trending.getArticleName())
                        .setWebUrl(trending.getWebUrl())
                        .setFbUrl(trending.getFbUrl())
                        .setTwitterUrl(trending.getTwitterUrl())
                        .setBookUrl(trending.getBookUrl())
                        .setCatId(trending.getCatId())
                        .setTitle(trending.getTitle())
                        .setTags(trending.getTags())
                        .setDescription(trending.getDescription())
                        .setPostType(trending.getPostType())
                        .setCoverUrl(trending.getCoverUrl())
                        .setOriginalUrl(trending.getOriginalUrl())
                        .setLikes(trending.getLikes())
                        .setDislikes(trending.getDislikes())
                        .setDownloads(trending.getDownloads())
                        .setDataType(Constant.DATA_TYPE.POPULAR)
                        .setRating(trending.getRating())
                        .setComments(String.valueOf(trending.getComments())));

            }

            //endregion

            dataObject = new DataObject()
                    .setCode(trendingPhotosJson.getCode())
                    .setMessage(trendingPhotosJson.getMessage())
                    .setWallpaperList(trendingWallpaperList);

        } else if (requestObject.getConnection() == Constant.CONNECTION.ALL_CATEGORIES) {

            //region All Categories functionalities

            ArrayList<DataObject> trendingWallpaperList = new ArrayList<>();
            CategoriesJson categoriesJson = (CategoriesJson) data;
            //nextPage = trendingPhotosJson.getNextPage();

            for (int i = 0; i < categoriesJson.getCategories().size(); i++) {

                Category category = categoriesJson.getCategories().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(category.getId())
                        .setCategoryTitle(category.getCategory())
                        .setCategoryPicture(category.getPictureUrl()));

            }
            //endregion

            dataObject = new DataObject()
                    .setCode(categoriesJson.getCode())
                    .setMessage(categoriesJson.getMessage())
                    .setWallpaperList(trendingWallpaperList);

        } else if (requestObject.getConnection() == Constant.CONNECTION.ALL_ARTIST) {

            //region All AuthorFragment
            ArrayList<DataObject> trendingWallpaperList = new ArrayList<>();
            TrendingJson trendingPhotosJson = (TrendingJson) data;

            for (int i = 0; i < trendingPhotosJson.getArtist().size(); i++) {

                Artist trending = trendingPhotosJson.getArtist().get(i);
                trendingWallpaperList.add(new DataObject()
                        .setId(trending.getId())
                        .setTitle(trending.getCategory())
                        .setAuthorWork(trending.getBookCount())
                        .setAuthorDescription(trending.getDescription())
                        .setBookCount(trending.getBookCount())
                        .setDownloadCount(trending.getDownloadcount())
                        .setReviewCount(trending.getReviewCount())
                        .setDataType(Constant.DATA_TYPE.ARTIST)
                        .setOriginalUrl(trending.getPictureUrl()));

            }
            //endregion

            dataObject = new DataObject()
                    .setCode(trendingPhotosJson.getCode())
                    .setMessage(trendingPhotosJson.getMessage())
                    .setWallpaperList(trendingWallpaperList);

        } else if (requestObject.getConnection() == Constant.CONNECTION.LOGIN
                || requestObject.getConnection() == Constant.CONNECTION.SIGN_UP
                || requestObject.getConnection() == Constant.CONNECTION.UPDATE) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage())
                    .setUserId(userJson.getUserId())
                    .setFirstName(userJson.getFName())
                    .setLastName(userJson.getLName())
                    .setEmail(userJson.getEmail())
                    .setPassword(userJson.getPass())
                    .setUid(userJson.getUid())
                    .setLoginType(userJson.getUserType())
                    .setPicture(userJson.getAvatar());

        } else if (requestObject.getConnection() == Constant.CONNECTION.FORGOT) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage());

        } else if (requestObject.getConnection() == Constant.CONNECTION.ALL_FAVOURITES
                || requestObject.getConnection() == Constant.CONNECTION.DELETE_FAVOURITES
                || requestObject.getConnection() == Constant.CONNECTION.ADD_FAVOURITES) {

            //region All Favourites functionalities

            ArrayList<DataObject> trendingWallpaperList = new ArrayList<>();
            FavouriteJson favouriteJson = (FavouriteJson) data;

            for (int i = 0; i < favouriteJson.getFavourites().size(); i++) {

                Favourite favourite = favouriteJson.getFavourites().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(favourite.getPostId())
                        .setStreamName(favourite.getTitle())
                        .setArtistName(favourite.getArticleName())
                        .setWebUrl(favourite.getWebUrl())
                        .setFbUrl(favourite.getFbUrl())
                        .setTwitterUrl(favourite.getTwitterUrl())
                        .setBookUrl(favourite.getStreamUrl())
                        .setCatId(favourite.getCatId())
                        .setTitle(favourite.getTitle())
                        .setTags(favourite.getTags())
                        .setDescription(favourite.getDescription())
                        .setPostType(favourite.getPostType())
                        .setCoverUrl(favourite.getCoverUrl())
                        .setOriginalUrl(favourite.getOriginalUrl())
                        .setLikes(favourite.getLikes())
                        .setDislikes(favourite.getDislikes())
                        .setDownloads(favourite.getDownloads())
                        .setDataType(Constant.DATA_TYPE.POPULAR)
                        .setRating(favourite.getRating())
                        .setComments(String.valueOf(favourite.getComments())));

            }
            //endregion

            dataObject = new DataObject()
                    .setCode(favouriteJson.getCode())
                    .setMessage(favouriteJson.getMessage())
                    .setWallpaperList(trendingWallpaperList);

        } else if (requestObject.getConnection() == Constant.CONNECTION.ADD_COMMENT
                || requestObject.getConnection() == Constant.CONNECTION.ALL_COMMENT) {

            //region All Comment functionalities
            ArrayList<DataObject> trendingWallpaperList = new ArrayList<>();
            CommentJson commentJson = (CommentJson) data;

            for (int i = 0; i < commentJson.getComments().size(); i++) {

                Comment comment = commentJson.getComments().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(comment.getId())
                        .setPostId(comment.getPostId())
                        .setUserId(comment.getUserId())
                        .setFirstName(comment.getFName())
                        .setLastName(comment.getLName())
                        .setEmail(comment.getEmail())
                        .setPicture(comment.getAvatar())
                        .setComments(comment.getComments()));

            }
            //endregion

            dataObject = new DataObject()
                    .setCode(commentJson.getCode())
                    .setMessage(commentJson.getMessage())
                    .setWallpaperList(trendingWallpaperList);

        } else if (requestObject.getConnection() == Constant.CONNECTION.PRIVACY_POLICY) {

            PrivacyPolicyJson privacyPolicyJson = (PrivacyPolicyJson) data;
            dataObject = new DataObject()
                    .setCode(privacyPolicyJson.getCode())
                    .setMessage(privacyPolicyJson.getMessage())
                    .setPrivacyPolicy(privacyPolicyJson.getPrivacy());

        } else if (requestObject.getConnection() == Constant.CONNECTION.ARTIST_DETAIL) {

            //region For getting artist detail

            ArrayList<DataObject> trendingWallpaperList = new ArrayList<>();
            ArrayList<Object> homeList = new ArrayList<>();
            TrendingJson trendingPhotosJson = (TrendingJson) data;
            HomeObject homeObject = null;

            for (int i = 0; i < trendingPhotosJson.getTrending().size(); i++) {

                Trending trending = trendingPhotosJson.getTrending().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(trending.getId())
                        .setStreamName(trending.getTitle())
                        .setArtistName(trending.getArticleName())
                        .setWebUrl(trending.getWebUrl())
                        .setFbUrl(trending.getFbUrl())
                        .setTwitterUrl(trending.getTwitterUrl())
                        .setBookUrl(trending.getBookUrl())
                        .setCatId(trending.getCatId())
                        .setTitle(trending.getTitle())
                        .setTags(trending.getTags())
                        .setDescription(trending.getDescription())
                        .setPostType(trending.getPostType())
                        .setCoverUrl(trending.getCoverUrl())
                        .setOriginalUrl(trending.getOriginalUrl())
                        .setLikes(trending.getLikes())
                        .setDislikes(trending.getDislikes())
                        .setDownloads(trending.getDownloads())
                        .setDataType(Constant.DATA_TYPE.POPULAR)
                        .setRating(trending.getRating())
                        .setComments(String.valueOf(trending.getComments())));

            }

            if (trendingWallpaperList.size() > 0) {
                homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.top))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.popular_book))
                        .setData_type(Constant.DATA_TYPE.POPULAR)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));
                homeList.add(homeObject);
            }
            trendingWallpaperList.clear();


            for (int i = 0; i < trendingPhotosJson.getNewly().size(); i++) {

                Newly trending = trendingPhotosJson.getNewly().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(trending.getId())
                        .setStreamName(trending.getTitle())
                        .setArtistName(trending.getArticleName())
                        .setWebUrl(trending.getWebUrl())
                        .setFbUrl(trending.getFbUrl())
                        .setTwitterUrl(trending.getTwitterUrl())
                        .setBookUrl(trending.getBookUrl())
                        .setCatId(trending.getCatId())
                        .setTitle(trending.getTitle())
                        .setTags(trending.getTags())
                        .setDescription(trending.getDescription())
                        .setPostType(trending.getPostType())
                        .setCoverUrl(trending.getCoverUrl())
                        .setOriginalUrl(trending.getOriginalUrl())
                        .setLikes(trending.getLikes())
                        .setDislikes(trending.getDislikes())
                        .setDownloads(trending.getDownloads())
                        .setDataType(Constant.DATA_TYPE.FEED)
                        .setRating(trending.getRating())
                        .setComments(trending.getComments().toString()));

            }

            if (trendingWallpaperList.size() > 0) {

                homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.newly))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.reading_list))
                        .setData_type(Constant.DATA_TYPE.COMMON)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));
                homeList.add(homeObject);


                /*homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.newly))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.streams))
                        .setData_type(Constant.DATA_TYPE.FEED)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));*/

                homeList.addAll(new ArrayList<>(trendingWallpaperList));

            }

            //endregion

            dataObject = new DataObject().setCode(trendingPhotosJson.getCode())
                    .setMessage(trendingPhotosJson.getMessage())
                    .setHomeList(homeList);

        } else if (requestObject.getConnection() == Constant.CONNECTION.BOOK_DETAIL) {

            //region For getting book detail

            ArrayList<DataObject> trendingWallpaperList = new ArrayList<>();
            ArrayList<Object> homeList = new ArrayList<>();
            TrendingJson trendingPhotosJson = (TrendingJson) data;
            HomeObject homeObject = null;

            for (int i = 0; i < trendingPhotosJson.getTrending().size(); i++) {

                Trending trending = trendingPhotosJson.getTrending().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(trending.getId())
                        .setStreamName(trending.getTitle())
                        .setArtistName(trending.getArticleName())
                        .setWebUrl(trending.getWebUrl())
                        .setFbUrl(trending.getFbUrl())
                        .setTwitterUrl(trending.getTwitterUrl())
                        .setBookUrl(trending.getBookUrl())
                        .setCatId(trending.getCatId())
                        .setTitle(trending.getTitle())
                        .setTags(trending.getTags())
                        .setDescription(trending.getDescription())
                        .setPostType(trending.getPostType())
                        .setCoverUrl(trending.getCoverUrl())
                        .setOriginalUrl(trending.getOriginalUrl())
                        .setLikes(trending.getLikes())
                        .setDislikes(trending.getDislikes())
                        .setDownloads(trending.getDownloads())
                        .setDataType(Constant.DATA_TYPE.POPULAR)
                        .setRating(trending.getRating())
                        .setComments(String.valueOf(trending.getComments())));

            }

            if (trendingWallpaperList.size() > 0) {
                homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.top))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.related_books))
                        .setData_type(Constant.DATA_TYPE.POPULAR)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));
                homeList.add(homeObject);
            }
            trendingWallpaperList.clear();


            for (int i = 0; i < trendingPhotosJson.getComments().size(); i++) {

                Comment comment = trendingPhotosJson.getComments().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(comment.getId())
                        .setPostId(comment.getPostId())
                        .setUserId(comment.getUserId())
                        .setReviewPersonName(comment.getFName() + " " + comment.getLName())
                        .setEmail(comment.getEmail())
                        .setLoginType(comment.getUserType())
                        .setReviewPersonRating(comment.getRating())
                        .setReviewPersonPicture(comment.getAvatar())
                        .setDataType(Constant.DATA_TYPE.REVIEW)
                        .setReviewPersonReview(comment.getComments()));

            }


            homeObject = new HomeObject()
                    .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.newly))
                    .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.review))
                    .setData_type(Constant.DATA_TYPE.COMMON)
                    .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));
            homeList.add(homeObject);


                /*homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(MyApplication.getInstance(), R.string.newly))
                        .setTitle(Utility.getStringFromRes(MyApplication.getInstance(), R.string.streams))
                        .setData_type(Constant.DATA_TYPE.FEED)
                        .setDataObjectArrayList(new ArrayList<DataObject>(trendingWallpaperList));*/

            if (trendingWallpaperList.size() > 0) {

                homeList.addAll(new ArrayList<>(trendingWallpaperList));

            }

            //endregion

            dataObject = new DataObject().setCode(trendingPhotosJson.getCode())
                    .setMessage(trendingPhotosJson.getMessage())
                    .setHomeList(homeList);

        } else if (requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_AUTHOR_DETAIL) {

            ArrayList<DataObject> trendingWallpaperList = new ArrayList<>();
            TrendingJson trendingPhotosJson = (TrendingJson) data;

            for (int i = 0; i < trendingPhotosJson.getArtist().size(); i++) {

                Artist trending = trendingPhotosJson.getArtist().get(i);

                trendingWallpaperList.add(new DataObject()
                        .setId(trending.getId())
                        .setTitle(trending.getCategory())
                        .setAuthorWork(trending.getBookCount())
                        .setAuthorDescription(trending.getDescription())
                        .setBookCount(trending.getBookCount())
                        .setDownloadCount(trending.getDownloadcount())
                        .setReviewCount(trending.getReviewCount())
                        .setDataType(Constant.DATA_TYPE.ARTIST)
                        .setOriginalUrl(trending.getPictureUrl()));

            }

            dataObject = new DataObject()
                    .setCode(trendingPhotosJson.getCode())
                    .setMessage(trendingPhotosJson.getMessage())
                    .setWallpaperList(trendingWallpaperList);

        }


        return dataObject;

    }


    public DataObject() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nextPage);
        dest.writeString(this.portraitUrl);
        dest.writeString(this.medium);
        dest.writeString(this.small);
        dest.writeString(this.tiny);
        dest.writeString(this.large);
        dest.writeString(this.tags);
        dest.writeString(this.type);
        dest.writeString(this.pictureid);
        dest.writeByte(this.isLocked ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.wallpaperList);
        dest.writeList(this.homeList);
        dest.writeString(this.historyId);
        dest.writeString(this.historyTags);
        dest.writeString(this.historyUrl);
        dest.writeString(this.postId);
        dest.writeString(this.catId);
        dest.writeString(this.artistName);
        dest.writeString(this.streamName);
        dest.writeString(this.artistId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.bookUrl);
        dest.writeString(this.postType);
        dest.writeString(this.coverUrl);
        dest.writeString(this.originalUrl);
        dest.writeString(this.webUrl);
        dest.writeString(this.fbUrl);
        dest.writeString(this.twitterUrl);
        dest.writeString(this.likes);
        dest.writeString(this.dislikes);
        dest.writeString(this.downloads);
        dest.writeString(this.comments);
        dest.writeString(this.rating);
        dest.writeString(this.categoryTitle);
        dest.writeString(this.categoryPicture);
        dest.writeString(this.userId);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.picture);
        dest.writeString(this.password);
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeString(this.comment);
        dest.writeString(this.privacyPolicy);
        dest.writeString(this.admobAppId);
        dest.writeString(this.admobBannerId);
        dest.writeString(this.admobInterstitialId);
        dest.writeString(this.playlistId);
        dest.writeString(this.serverId);
        dest.writeParcelable(this.downloadUri, flags);
        dest.writeStringList(this.fileList);
        dest.writeInt(this.dataType == null ? -1 : this.dataType.ordinal());
        dest.writeString(this.authorWork);
        dest.writeString(this.authorDescription);
        dest.writeString(this.bookCount);
        dest.writeString(this.downloadCount);
        dest.writeString(this.reviewCount);
        dest.writeString(this.reviewPersonName);
        dest.writeString(this.reviewPersonRating);
        dest.writeString(this.reviewPersonReview);
        dest.writeString(this.reviewPersonPicture);
        dest.writeString(this.fileType);
        dest.writeString(this.bookPage);
        dest.writeString(this.currentPage);
        dest.writeByte(this.isList ? (byte) 1 : (byte) 0);
    }

    protected DataObject(Parcel in) {
        this.id = in.readString();
        this.nextPage = in.readString();
        this.portraitUrl = in.readString();
        this.medium = in.readString();
        this.small = in.readString();
        this.tiny = in.readString();
        this.large = in.readString();
        this.tags = in.readString();
        this.type = in.readString();
        this.pictureid = in.readString();
        this.isLocked = in.readByte() != 0;
        this.wallpaperList = in.createTypedArrayList(DataObject.CREATOR);
        this.homeList = new ArrayList<Object>();
        in.readList(this.homeList, Object.class.getClassLoader());
        this.historyId = in.readString();
        this.historyTags = in.readString();
        this.historyUrl = in.readString();
        this.postId = in.readString();
        this.catId = in.readString();
        this.artistName = in.readString();
        this.streamName = in.readString();
        this.artistId = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.bookUrl = in.readString();
        this.postType = in.readString();
        this.coverUrl = in.readString();
        this.originalUrl = in.readString();
        this.webUrl = in.readString();
        this.fbUrl = in.readString();
        this.twitterUrl = in.readString();
        this.likes = in.readString();
        this.dislikes = in.readString();
        this.downloads = in.readString();
        this.comments = in.readString();
        this.rating = in.readString();
        this.categoryTitle = in.readString();
        this.categoryPicture = in.readString();
        this.userId = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.picture = in.readString();
        this.password = in.readString();
        this.code = in.readString();
        this.message = in.readString();
        this.comment = in.readString();
        this.privacyPolicy = in.readString();
        this.admobAppId = in.readString();
        this.admobBannerId = in.readString();
        this.admobInterstitialId = in.readString();
        this.playlistId = in.readString();
        this.serverId = in.readString();
        this.downloadUri = in.readParcelable(Uri.class.getClassLoader());
        this.fileList = in.createStringArrayList();
        int tmpDataType = in.readInt();
        this.dataType = tmpDataType == -1 ? null : Constant.DATA_TYPE.values()[tmpDataType];
        this.authorWork = in.readString();
        this.authorDescription = in.readString();
        this.bookCount = in.readString();
        this.downloadCount = in.readString();
        this.reviewCount = in.readString();
        this.reviewPersonName = in.readString();
        this.reviewPersonRating = in.readString();
        this.reviewPersonReview = in.readString();
        this.reviewPersonPicture = in.readString();
        this.fileType = in.readString();
        this.bookPage = in.readString();
        this.currentPage = in.readString();
        this.isList = in.readByte() != 0;
    }

    public static final Creator<DataObject> CREATOR = new Creator<DataObject>() {
        @Override
        public DataObject createFromParcel(Parcel source) {
            return new DataObject(source);
        }

        @Override
        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };
}
