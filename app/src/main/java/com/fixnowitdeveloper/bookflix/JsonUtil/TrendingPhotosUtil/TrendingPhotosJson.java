
package com.fixnowitdeveloper.bookflix.JsonUtil.TrendingPhotosUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrendingPhotosJson implements Parcelable
{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("next_page")
    @Expose
    private String nextPage;
    public final static Parcelable.Creator<TrendingPhotosJson> CREATOR = new Creator<TrendingPhotosJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TrendingPhotosJson createFromParcel(Parcel in) {
            return new TrendingPhotosJson(in);
        }

        public TrendingPhotosJson[] newArray(int size) {
            return (new TrendingPhotosJson[size]);
        }

    }
    ;

    protected TrendingPhotosJson(Parcel in) {
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.perPage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.photos, (com.fixnowitdeveloper.bookflix.JsonUtil.TrendingPhotosUtil.Photo.class.getClassLoader()));
        this.nextPage = ((String) in.readValue((String.class.getClassLoader())));
    }

    public TrendingPhotosJson() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(perPage);
        dest.writeList(photos);
        dest.writeValue(nextPage);
    }

    public int describeContents() {
        return  0;
    }

}
