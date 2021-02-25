
package com.fixnowitdeveloper.bookflix.JsonUtil.TrendingUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("pictureUrl")
    @Expose
    private String pictureUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("bookCount")
    @Expose
    private String bookCount;
    @SerializedName("reviewCount")
    @Expose
    private String reviewCount;
    @SerializedName("downloadcount")
    @Expose
    private String downloadcount;

    public Artist() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescription() {
        return description;
    }

    public Artist setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getBookCount() {
        return bookCount;
    }

    public Artist setBookCount(String bookCount) {
        this.bookCount = bookCount;
        return this;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public Artist setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
        return this;
    }

    public String getDownloadcount() {
        return downloadcount;
    }

    public Artist setDownloadcount(String downloadcount) {
        this.downloadcount = downloadcount;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.category);
        dest.writeString(this.pictureUrl);
        dest.writeString(this.description);
        dest.writeString(this.bookCount);
        dest.writeString(this.reviewCount);
        dest.writeString(this.downloadcount);
    }

    protected Artist(Parcel in) {
        this.id = in.readString();
        this.category = in.readString();
        this.pictureUrl = in.readString();
        this.description = in.readString();
        this.bookCount = in.readString();
        this.reviewCount = in.readString();
        this.downloadcount = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
