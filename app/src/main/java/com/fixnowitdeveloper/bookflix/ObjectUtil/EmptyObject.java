package com.fixnowitdeveloper.bookflix.ObjectUtil;

public class EmptyObject {
    private String title;
    private String description;
    private int placeHolderIcon;


    public String getTitle() {
        return title;
    }

    public EmptyObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EmptyObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPlaceHolderIcon() {
        return placeHolderIcon;
    }

    public EmptyObject setPlaceHolderIcon(int placeHolderIcon) {
        this.placeHolderIcon = placeHolderIcon;
        return this;
    }
}
