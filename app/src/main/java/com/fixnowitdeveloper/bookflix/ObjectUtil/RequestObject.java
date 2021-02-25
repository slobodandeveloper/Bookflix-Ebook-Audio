package com.fixnowitdeveloper.bookflix.ObjectUtil;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.ConnectionCallback;
import com.fixnowitdeveloper.bookflix.InterfaceUtil.InternetCallback;

public class RequestObject implements Parcelable {
    private Context context;
    private String serverUrl;
    private String requestType;
    private String photoKeyword;
    private String perPage = "60";
    private String page = "1";
    private String pictureId;
    private String json;
    private String postType;
    private String artistName;
    private String title;
    private String coverUrl;
    private String fileExtension;
    private String loadingText;
    private boolean share = false;
    private boolean read = false;
    private boolean firstRequest = true;
    private Constant.CONNECTION_TYPE connectionType;
    private Constant.CONNECTION connection;
    private ConnectionCallback connectionCallback;
    private InternetCallback internetCallback;


    public Context getContext() {
        return context;
    }

    public RequestObject setContext(Context context) {
        this.context = context;
        return this;
    }

    public Constant.CONNECTION getConnection() {
        return connection;
    }

    public RequestObject setConnection(Constant.CONNECTION connection) {
        this.connection = connection;
        return this;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public RequestObject setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    public String getLoadingText() {
        return loadingText;
    }

    public RequestObject setLoadingText(String loadingText) {
        this.loadingText = loadingText;
        return this;
    }

    public String getPhotoKeyword() {
        return photoKeyword;
    }

    public RequestObject setPhotoKeyword(String photoKeyword) {
        this.photoKeyword = photoKeyword;
        return this;
    }

    public String getRequestType() {
        return requestType;
    }

    public RequestObject setRequestType(String requestType) {
        this.requestType = requestType;
        return this;
    }

    public ConnectionCallback getConnectionCallback() {
        return connectionCallback;
    }

    public RequestObject setConnectionCallback(ConnectionCallback connectionCallback) {
        this.connectionCallback = connectionCallback;
        return this;
    }

    public String getPerPage() {
        return perPage;
    }

    public RequestObject setPerPage(String perPage) {
        this.perPage = perPage;
        return this;
    }

    public boolean isShare() {
        return share;
    }

    public RequestObject setShare(boolean share) {
        this.share = share;
        return this;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public RequestObject setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public RequestObject setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        return this;
    }

    public String getPage() {
        return page;
    }

    public RequestObject setPage(String page) {
        this.page = page;
        return this;
    }

    public boolean isFirstRequest() {
        return firstRequest;
    }

    public RequestObject setFirstRequest(boolean firstRequest) {
        this.firstRequest = firstRequest;
        return this;
    }

    public Constant.CONNECTION_TYPE getConnectionType() {
        return connectionType;
    }

    public RequestObject setConnectionType(Constant.CONNECTION_TYPE connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public String getPictureId() {
        return pictureId;
    }

    public RequestObject setPictureId(String pictureId) {
        this.pictureId = pictureId;
        return this;
    }

    public String getJson() {
        return json;
    }

    public RequestObject setJson(String json) {
        this.json = json;
        return this;
    }

    public RequestObject() {
    }

    public String getPostType() {
        return postType;
    }

    public RequestObject setPostType(String postType) {
        this.postType = postType;
        return this;
    }

    public String getArtistName() {
        return artistName;
    }

    public RequestObject setArtistName(String artistName) {
        this.artistName = artistName;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public RequestObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public InternetCallback getInternetCallback() {
        return internetCallback;
    }

    public RequestObject setInternetCallback(InternetCallback internetCallback) {
        this.internetCallback = internetCallback;
        return this;
    }

    public boolean isRead() {
        return read;
    }

    public RequestObject setRead(boolean read) {
        this.read = read;
        return this;
    }

    @Override
    public String toString() {
        return "RequestObject{" +
                "serverUrl='" + serverUrl + '\'' +
                ", requestType='" + requestType + '\'' +
                ", photoKeyword='" + photoKeyword + '\'' +
                ", perPage='" + perPage + '\'' +
                ", page='" + page + '\'' +
                ", pictureId='" + pictureId + '\'' +
                ", json='" + json + '\'' +
                ", firstRequest=" + firstRequest +
                ", connectionType=" + connectionType +
                ", connection=" + connection +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.serverUrl);
        dest.writeString(this.requestType);
        dest.writeString(this.photoKeyword);
        dest.writeString(this.perPage);
        dest.writeString(this.page);
        dest.writeString(this.pictureId);
        dest.writeString(this.json);
        dest.writeString(this.postType);
        dest.writeByte(this.firstRequest ? (byte) 1 : (byte) 0);
        dest.writeInt(this.connectionType == null ? -1 : this.connectionType.ordinal());
        dest.writeInt(this.connection == null ? -1 : this.connection.ordinal());

    }

    protected RequestObject(Parcel in) {

        this.serverUrl = in.readString();
        this.requestType = in.readString();
        this.photoKeyword = in.readString();
        this.perPage = in.readString();
        this.page = in.readString();
        this.pictureId = in.readString();
        this.json = in.readString();
        this.postType = in.readString();
        this.firstRequest = in.readByte() != 0;
        int tmpConnectionType = in.readInt();
        this.connectionType = tmpConnectionType == -1 ? null : Constant.CONNECTION_TYPE.values()[tmpConnectionType];
        int tmpConnection = in.readInt();
        this.connection = tmpConnection == -1 ? null : Constant.CONNECTION.values()[tmpConnection];

    }

    public static final Creator<RequestObject> CREATOR = new Creator<RequestObject>() {
        @Override
        public RequestObject createFromParcel(Parcel source) {
            return new RequestObject(source);
        }

        @Override
        public RequestObject[] newArray(int size) {
            return new RequestObject[size];
        }
    };
}
