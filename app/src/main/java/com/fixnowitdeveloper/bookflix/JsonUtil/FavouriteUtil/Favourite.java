
package com.fixnowitdeveloper.bookflix.JsonUtil.FavouriteUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favourite {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("post_id")
    @Expose
    private String postId;
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


    public String getId() {
        return id;
    }

    public Favourite setId(String id) {
        this.id = id;
        return this;
    }

    public String getPostId() {
        return postId;
    }

    public Favourite setPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public String getArtistId() {
        return artistId;
    }

    public Favourite setArtistId(String artistId) {
        this.artistId = artistId;
        return this;
    }

    public String getArticleName() {
        return articleName;
    }

    public Favourite setArticleName(String articleName) {
        this.articleName = articleName;
        return this;
    }

    public String getCatId() {
        return catId;
    }

    public Favourite setCatId(String catId) {
        this.catId = catId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Favourite setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Favourite setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public Favourite setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public String getPostType() {
        return postType;
    }

    public Favourite setPostType(String postType) {
        this.postType = postType;
        return this;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public Favourite setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Favourite setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
        return this;
    }

    public String getLikes() {
        return likes;
    }

    public Favourite setLikes(String likes) {
        this.likes = likes;
        return this;
    }

    public String getDislikes() {
        return dislikes;
    }

    public Favourite setDislikes(String dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    public String getDownloads() {
        return downloads;
    }

    public Favourite setDownloads(String downloads) {
        this.downloads = downloads;
        return this;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public Favourite setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
        return this;
    }

    public String getFbUrl() {
        return fbUrl;
    }

    public Favourite setFbUrl(String fbUrl) {
        this.fbUrl = fbUrl;
        return this;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public Favourite setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
        return this;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public Favourite setWebUrl(String webUrl) {
        this.webUrl = webUrl;
        return this;
    }

    public Integer getComments() {
        return comments;
    }

    public Favourite setComments(Integer comments) {
        this.comments = comments;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public Favourite setRating(String rating) {
        this.rating = rating;
        return this;
    }
}
