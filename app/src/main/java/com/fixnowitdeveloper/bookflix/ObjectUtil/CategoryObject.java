package com.fixnowitdeveloper.bookflix.ObjectUtil;

public class CategoryObject {
    private String id;
    private String title;
    private String picture;
    private int drawable;
    private boolean isRound = true;
    private boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public CategoryObject setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public String getId() {
        return id;
    }

    public CategoryObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CategoryObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getDrawable() {
        return drawable;
    }

    public CategoryObject setDrawable(int drawable) {
        this.drawable = drawable;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public CategoryObject setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public boolean isRound() {
        return isRound;
    }

    public CategoryObject setRound(boolean round) {
        isRound = round;
        return this;
    }
}
