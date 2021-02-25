
package com.fixnowitdeveloper.bookflix.JsonUtil.ListOfPhotosUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfPhotosJson implements Parcelable
{

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
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
    public final static Parcelable.Creator<ListOfPhotosJson> CREATOR = new Creator<ListOfPhotosJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ListOfPhotosJson createFromParcel(Parcel in) {
            return new ListOfPhotosJson(in);
        }

        public ListOfPhotosJson[] newArray(int size) {
            return (new ListOfPhotosJson[size]);
        }

    }
    ;

    protected ListOfPhotosJson(Parcel in) {
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.perPage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.photos, (com.fixnowitdeveloper.bookflix.JsonUtil.ListOfPhotosUtil.Photo.class.getClassLoader()));
        this.nextPage = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ListOfPhotosJson() {
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
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
        dest.writeValue(totalResults);
        dest.writeValue(page);
        dest.writeValue(perPage);
        dest.writeList(photos);
        dest.writeValue(nextPage);
    }

    public int describeContents() {
        return  0;
    }

}
