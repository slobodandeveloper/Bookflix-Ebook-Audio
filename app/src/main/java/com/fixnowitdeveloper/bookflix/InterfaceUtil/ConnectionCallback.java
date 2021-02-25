package com.fixnowitdeveloper.bookflix.InterfaceUtil;

import com.fixnowitdeveloper.bookflix.ObjectUtil.RequestObject;

public interface ConnectionCallback {

    void onSuccess(Object data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);


}
