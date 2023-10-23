package com.jjshome.mobile.datastatistics.entity;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 错误信息
 * WQ on 2017/4/6
 * wq@jjshome.com
 */

public class ErrorInfo {
    /**
     * 错误类型app的错误信息
     */
    public static final String ERR_APP = "ERR01";
    /**
     * 错误类型 服务端错误
     */
    public static final String ERR_SERVER = "ERR02";
    /**
     * 当前错误信息的md5值
     */
    public String erId;
    /**
     * 错误类型 IM错误
     */
    public static final String ERR_IM = "ERR03";

    /**
     * 错误类型 ERR01-请求错误，ERR02-应用错误
     */
    public String type;

    /**
     * 错误信息 按大数据要求取第一行
     * example: syntax error, expect {, actual EOF, pos 0
     */
    public String err;

    @Override
    public String toString() {
        return "ErrorInfo{" + "erId='" + erId + '\'' + ", type='" + type + '\'' + ", err='" + err + '\'' + '}';
    }


    public Map<String, String> toRequestMap() {
        Map<String, String> pageMap = new HashMap<>();
        pageMap.put("erId", erId);
        pageMap.put("type", type);
        pageMap.put("err", err);
        return pageMap;
    }
}
