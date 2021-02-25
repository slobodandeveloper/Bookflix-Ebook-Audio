package com.fixnowitdeveloper.bookflix.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistHeaderObject implements Parcelable {
    private String artistId;
    private String authorName;
    private String authorPicture;
    private String authorWork;
    private String authorDescription;
    private String bookCount;
    private String downloadCount;
    private String reviewCount;


    public String getArtistId() {
        return artistId;
    }

    public ArtistHeaderObject setArtistId(String artistId) {
        this.artistId = artistId;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public ArtistHeaderObject setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getAuthorPicture() {
        return authorPicture;
    }

    public ArtistHeaderObject setAuthorPicture(String authorPicture) {
        this.authorPicture = authorPicture;
        return this;
    }

    public String getAuthorWork() {
        return authorWork;
    }

    public ArtistHeaderObject setAuthorWork(String authorWork) {
        this.authorWork = authorWork;
        return this;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public ArtistHeaderObject setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
        return this;
    }

    public String getBookCount() {
        return bookCount;
    }

    public ArtistHeaderObject setBookCount(String bookCount) {
        this.bookCount = bookCount;
        return this;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public ArtistHeaderObject setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
        return this;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public ArtistHeaderObject setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
        return this;
    }


    public ArtistHeaderObject() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.artistId);
        dest.writeString(this.authorName);
        dest.writeString(this.authorPicture);
        dest.writeString(this.authorWork);
        dest.writeString(this.authorDescription);
        dest.writeString(this.bookCount);
        dest.writeString(this.downloadCount);
        dest.writeString(this.reviewCount);
    }

    protected ArtistHeaderObject(Parcel in) {
        this.artistId = in.readString();
        this.authorName = in.readString();
        this.authorPicture = in.readString();
        this.authorWork = in.readString();
        this.authorDescription = in.readString();
        this.bookCount = in.readString();
        this.downloadCount = in.readString();
        this.reviewCount = in.readString();
    }

    public static final Creator<ArtistHeaderObject> CREATOR = new Creator<ArtistHeaderObject>() {
        @Override
        public ArtistHeaderObject createFromParcel(Parcel source) {
            return new ArtistHeaderObject(source);
        }

        @Override
        public ArtistHeaderObject[] newArray(int size) {
            return new ArtistHeaderObject[size];
        }
    };
}
