
package com.fixnowitdeveloper.bookflix.JsonUtil.AdJsonUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("admob_app_id")
    @Expose
    private String admobAppId;
    @SerializedName("admob_banner_id")
    @Expose
    private String admobBannerId;
    @SerializedName("admob_interstitial_id")
    @Expose
    private String admobInterstitialId;
    @SerializedName("admob_publisher_id")
    @Expose
    private String admobPublisherId;
    @SerializedName("admob_privacy_url")
    @Expose
    private String admobPrivacyUrl;

    public String getAdmobAppId() {
        return admobAppId;
    }

    public void setAdmobAppId(String admobAppId) {
        this.admobAppId = admobAppId;
    }

    public String getAdmobBannerId() {
        return admobBannerId;
    }

    public void setAdmobBannerId(String admobBannerId) {
        this.admobBannerId = admobBannerId;
    }

    public String getAdmobInterstitialId() {
        return admobInterstitialId;
    }

    public void setAdmobInterstitialId(String admobInterstitialId) {
        this.admobInterstitialId = admobInterstitialId;
    }

    public String getAdmobPublisherId() {
        return admobPublisherId;
    }

    public Ad setAdmobPublisherId(String admobPublisherId) {
        this.admobPublisherId = admobPublisherId;
        return this;
    }

    public String getAdmobPrivacyUrl() {
        return admobPrivacyUrl;
    }

    public Ad setAdmobPrivacyUrl(String admobPrivacyUrl) {
        this.admobPrivacyUrl = admobPrivacyUrl;
        return this;
    }
}
