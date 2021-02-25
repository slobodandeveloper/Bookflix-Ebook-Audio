package com.fixnowitdeveloper.bookflix.InterfaceUtil;

import android.net.Uri;

import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;

public interface DatabaseCallback {

    void onSuccess(Uri data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);

}
