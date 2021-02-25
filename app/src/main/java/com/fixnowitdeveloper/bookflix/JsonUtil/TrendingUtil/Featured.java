
package com.fixnowitdeveloper.bookflix.JsonUtil.TrendingUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Featured implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("artist_id")
    @Expose
    private String artistId;
    @SerializedName("article_name")
    @Expose
    private String articleName;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("postType")
    @Expose
    private String postType;
    @SerializedName("coverUrl")
    @Expose
    private String coverUrl;
    @SerializedName("originalUrl")
    @Expose
    private String originalUrl;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("dislikes")
    @Expose
    private String dislikes;
    @SerializedName("downloads")
    @Expose
    private String downloads;

    @SerializedName("streamUrl")
    @Expose
    private String streamUrl;
    @SerializedName("fbUrl")
    @Expose
    private String fbUrl;
    @SerializedName("twitterUrl")
    @Expose
    private String twitterUrl;
    @SerializedName("webUrl")
    @Expose
    private String webUrl;

    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("rating")
    @Expose
    private String rating;

    public Featured() {
    }

    public String getRating() {
        return rating;
    }

    public Featured setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getBookUrl() {
        return streamUrl;
    }

    public Featured setBookUrl(String streamUrl) {
        this.streamUrl = streamUrl;
        return this;
    }

    public String getFbUrl() {
        return fbUrl;
    }

    public Featured setFbUrl(String fbUrl) {
        this.fbUrl = fbUrl;
        return this;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public Featured setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
        return this;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public Featured setWebUrl(String webUrl) {
        this.webUrl = webUrl;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.artistId);
        dest.writeString(this.articleName);
        dest.writeString(this.catId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.tags);
        dest.writeString(this.postType);
        dest.writeString(this.coverUrl);
        dest.writeString(this.originalUrl);
        dest.writeString(this.likes);
        dest.writeString(this.dislikes);
        dest.writeString(this.downloads);
        dest.writeString(this.streamUrl);
        dest.writeString(this.fbUrl);
        dest.writeString(this.twitterUrl);
        dest.writeString(this.webUrl);
        dest.writeValue(this.comments);
    }

    protected Featured(Parcel in) {
        this.id = in.readString();
        this.artistId = in.readString();
        this.articleName = in.readString();
        this.catId = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.tags = in.readString();
        this.postType = in.readString();
        this.coverUrl = in.readString();
        this.originalUrl = in.readString();
        this.likes = in.readString();
        this.dislikes = in.readString();
        this.downloads = in.readString();
        this.streamUrl = in.readString();
        this.fbUrl = in.readString();
        this.twitterUrl = in.readString();
        this.webUrl = in.readString();
        this.comments = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Featured> CREATOR = new Creator<Featured>() {
        @Override
        public Featured createFromParcel(Parcel source) {
            return new Featured(source);
        }

        @Override
        public Featured[] newArray(int size) {
            return new Featured[size];
        }
    };
}
