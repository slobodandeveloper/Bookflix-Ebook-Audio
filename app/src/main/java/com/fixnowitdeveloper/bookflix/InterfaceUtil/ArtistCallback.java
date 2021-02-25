package com.fixnowitdeveloper.bookflix.InterfaceUtil;

import com.fixnowitdeveloper.bookflix.ObjectUtil.AuthorObject;

public interface ArtistCallback {

    void onSelect(int parentPosition,int childPosition);

    void onShare(AuthorObject authorObject);

}
