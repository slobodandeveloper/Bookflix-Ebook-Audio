package com.fixnowitdeveloper.bookflix.InterfaceUtil;

public interface BookDetailCallback {

    void onSelect(int parentPosition, int childPosition);

    void addReview();

    void readBook();

    void downloadBook();

}
