package com.fixnowitdeveloper.bookflix.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class BookHeaderObject implements Parcelable {
    private String bookId;
    private String bookName;
    private String bookDescription;
    private String bookAuthorName;
    private String bookDownloadCount;
    private String bookReviewCount;
    private String bookRating;
    private String bookImage;
    private String bookTag;
    private String bookUrl;


    public String getBookId() {
        return bookId;
    }

    public BookHeaderObject setBookId(String bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public BookHeaderObject setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public BookHeaderObject setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
        return this;
    }

    public String getBookAuthorName() {
        return bookAuthorName;
    }

    public BookHeaderObject setBookAuthorName(String bookAuthorName) {
        this.bookAuthorName = bookAuthorName;
        return this;
    }

    public String getBookDownloadCount() {
        return bookDownloadCount;
    }

    public BookHeaderObject setBookDownloadCount(String bookDownloadCount) {
        this.bookDownloadCount = bookDownloadCount;
        return this;
    }

    public String getBookReviewCount() {
        return bookReviewCount;
    }

    public BookHeaderObject setBookReviewCount(String bookReviewCount) {
        this.bookReviewCount = bookReviewCount;
        return this;
    }

    public String getBookRating() {
        return bookRating;
    }

    public BookHeaderObject setBookRating(String bookRating) {
        this.bookRating = bookRating;
        return this;
    }

    public String getBookImage() {
        return bookImage;
    }

    public BookHeaderObject setBookImage(String bookImage) {
        this.bookImage = bookImage;
        return this;
    }

    public String getBookTag() {
        return bookTag;
    }

    public BookHeaderObject setBookTag(String bookTag) {
        this.bookTag = bookTag;
        return this;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public BookHeaderObject setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
        return this;
    }

    public BookHeaderObject() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bookId);
        dest.writeString(this.bookName);
        dest.writeString(this.bookDescription);
        dest.writeString(this.bookAuthorName);
        dest.writeString(this.bookDownloadCount);
        dest.writeString(this.bookReviewCount);
        dest.writeString(this.bookRating);
        dest.writeString(this.bookImage);
        dest.writeString(this.bookTag);
        dest.writeString(this.bookUrl);
    }

    protected BookHeaderObject(Parcel in) {
        this.bookId = in.readString();
        this.bookName = in.readString();
        this.bookDescription = in.readString();
        this.bookAuthorName = in.readString();
        this.bookDownloadCount = in.readString();
        this.bookReviewCount = in.readString();
        this.bookRating = in.readString();
        this.bookImage = in.readString();
        this.bookTag = in.readString();
        this.bookUrl = in.readString();
    }

    public static final Creator<BookHeaderObject> CREATOR = new Creator<BookHeaderObject>() {
        @Override
        public BookHeaderObject createFromParcel(Parcel source) {
            return new BookHeaderObject(source);
        }

        @Override
        public BookHeaderObject[] newArray(int size) {
            return new BookHeaderObject[size];
        }
    };
}
