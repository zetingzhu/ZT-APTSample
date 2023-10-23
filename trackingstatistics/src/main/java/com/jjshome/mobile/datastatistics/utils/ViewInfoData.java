package com.jjshome.mobile.datastatistics.utils;

import com.jjshome.mobile.datastatistics.marquee.ViewInfoEntity;

public class ViewInfoData {

    public  static ViewInfoEntity viewInfoEntity;

    public static ViewInfoEntity getViewInfoEntity() {
        return viewInfoEntity;
    }

    public static void setViewInfoEntity(ViewInfoEntity viewInfoEntity) {
        ViewInfoData.viewInfoEntity = viewInfoEntity;
    }
}
