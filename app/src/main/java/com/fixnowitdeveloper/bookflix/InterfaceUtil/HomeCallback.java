package com.fixnowitdeveloper.bookflix.InterfaceUtil;

import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;

public interface HomeCallback {

    void onSelect(int parentPosition,int childPosition);

    void onSelectSearch();

    void onAddBook();

    void onMore(Constant.DATA_TYPE dataType);

}
