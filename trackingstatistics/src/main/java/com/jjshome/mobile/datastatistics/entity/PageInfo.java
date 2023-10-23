package com.jjshome.mobile.datastatistics.entity;

import android.text.TextUtils;
import com.jjshome.mobile.datastatistics.utils.Common;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 页面浏览记录会话统计，一次onResume和一次onPause为一次会话
 * WQ on 2017/3/3
 * wq@jjshome.com
 */

public class PageInfo {
    /**
     * 当前访问页的类全路径
     * example：com.jjshome.HomeActivity
     */
    public String pId = "";
    /**
     * 浏览页的开始时间
     * example：2017-04-01 16:05:36
     */
    public String st = "";
    /**
     * 浏览页的结束时间
     * example：2017-04-01 16:08:30
     */
    public String et = "";

    @Override
    public String toString() {
        return "PageInfo{" + "pId='" + pId + '\'' + ", st='" + st + '\'' + ", et='" + et + '\'' + '}';
    }

    public Map<String, String> toRequestMap() {
        Map<String, String> pageMap = new HashMap<>();
        pageMap.put("pId", pId);
        pageMap.put("st", st);
        pageMap.put("et", et);
        return pageMap;
    }

}
