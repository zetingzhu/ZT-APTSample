package com.jjshome.mobile.datastatistics.utils;


import com.jjshome.mobile.datastatistics.BuildConfig;

/**
 * 作者:ocean
 * 时间:2020-04-02 11:44
 * 用途: 获取秘钥
 */
public class EncryptedSPUtil {

    public static String getSKey() {
        return BuildConfig.appSKey;
    }

    public static String getIvParameter() {
        return BuildConfig.appIvParameter;
    }

}
