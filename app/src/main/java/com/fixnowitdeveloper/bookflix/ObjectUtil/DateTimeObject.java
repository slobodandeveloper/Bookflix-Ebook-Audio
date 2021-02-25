package com.fixnowitdeveloper.bookflix.ObjectUtil;


import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;

public class DateTimeObject {
    private Constant.DATETIME datetimeType;
    private String datetime;
    private String date;
    private String time;
    private boolean isDateInLong;
    private long dateTimeInLong;
    private boolean isCurrentDate;


    public Constant.DATETIME getDatetimeType() {
        return datetimeType;
    }

    public DateTimeObject setDatetimeType(Constant.DATETIME datetimeType) {
        this.datetimeType = datetimeType;
        return this;
    }

    public String getDatetime() {
        return datetime;
    }

    public DateTimeObject setDatetime(String datetime) {
        this.datetime = datetime;
        return this;
    }

    public String getDate() {
        return date;
    }

    public DateTimeObject setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public DateTimeObject setTime(String time) {
        this.time = time;
        return this;
    }

    public long getDateTimeInLong() {
        return dateTimeInLong;
    }

    public DateTimeObject setDateTimeInLong(long dateTimeInLong) {
        this.dateTimeInLong = dateTimeInLong;
        return this;
    }

    public boolean isDateInLong() {
        return isDateInLong;
    }

    public DateTimeObject setDateInLong(boolean dateInLong) {
        isDateInLong = dateInLong;
        return this;
    }

    public boolean isCurrentDate() {
        return isCurrentDate;
    }

    public DateTimeObject setCurrentDate(boolean currentDate) {
        isCurrentDate = currentDate;
        return this;
    }
}
