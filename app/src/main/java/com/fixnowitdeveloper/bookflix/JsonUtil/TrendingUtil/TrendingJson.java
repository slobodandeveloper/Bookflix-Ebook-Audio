
package com.fixnowitdeveloper.bookflix.JsonUtil.TrendingUtil;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fixnowitdeveloper.bookflix.JsonUtil.CategoriesUtil.Category;
import com.fixnowitdeveloper.bookflix.JsonUtil.CommentUtil.Comment;

public class TrendingJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("featured")
    @Expose
    private List<Featured> featured = null;
    @SerializedName("trending")
    @Expose
    private List<Trending> trending = null;
    @SerializedName("newly")
    @Expose
    private List<Newly> newly = null;
    @SerializedName("artist")
    @Expose
    private List<Artist> artist = null;
    @SerializedName("category")
    @Expose
    private List<Category> categories = null;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

    public TrendingJson() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Featured> getFeatured() {
        return featured;
    }

    public void setFeatured(List<Featured> featured) {
        this.featured = featured;
    }

    public List<Trending> getTrending() {
        return trending;
    }

    public void setTrending(List<Trending> trending) {
        this.trending = trending;
    }

    public List<Newly> getNewly() {
        return newly;
    }

    public void setNewly(List<Newly> newly) {
        this.newly = newly;
    }

    public List<Artist> getArtist() {
        return artist;
    }

    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public TrendingJson setCategories(List<Category> categories) {
        this.categories = categories;
        return this;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public TrendingJson setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.featured);
        dest.writeTypedList(this.trending);
        dest.writeTypedList(this.newly);
        dest.writeTypedList(this.artist);
        dest.writeTypedList(this.categories);
        dest.writeList(this.comments);
    }

    protected TrendingJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.featured = in.createTypedArrayList(Featured.CREATOR);
        this.trending = in.createTypedArrayList(Trending.CREATOR);
        this.newly = in.createTypedArrayList(Newly.CREATOR);
        this.artist = in.createTypedArrayList(Artist.CREATOR);
        this.categories = in.createTypedArrayList(Category.CREATOR);
        this.comments = new ArrayList<Comment>();
        in.readList(this.comments, Comment.class.getClassLoader());
    }

    public static final Creator<TrendingJson> CREATOR = new Creator<TrendingJson>() {
        @Override
        public TrendingJson createFromParcel(Parcel source) {
            return new TrendingJson(source);
        }

        @Override
        public TrendingJson[] newArray(int size) {
            return new TrendingJson[size];
        }
    };
}
