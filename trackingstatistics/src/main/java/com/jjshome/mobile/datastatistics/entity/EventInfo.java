package com.jjshome.mobile.datastatistics.entity;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 事件信息
 * WQ on 2017/4/6
 * wq@jjshome.com
 */

public class EventInfo {
    /**
     * 自定义事件id
     */
    public String eId;
    /**
     * 自定义事件对象
     */
    public String obj;

    public String page;//页面pid
    public String title;//页面标题
    public String path;//元素路径：以控件名称+控件ID，存在多级控件的，均以控件名称-控件ID-上级控件名称-上级控件ID的顺序规则放至path中
    public String index;//位置顺序
    public int type;//1无埋点

    @Override
    public String toString() {
        return "EventInfo{" + "eId='" + eId + '\'' + ", obj='" + obj + '\'' + ", page='" + page + '\'' + ", title='" + title + '\'' + ", path='" + path + '\'' + ", index='" + index + '\'' + '}';
    }


    public Map<String, String> toRequestMap() {
        Map<String, String> pageMap = new HashMap<>();
        if (type != 1) {
            pageMap.put("eId", eId);
        }
//        pageMap.put("page", TextUtils.isEmpty(page) ? "" : page);
        pageMap.put("title", TextUtils.isEmpty(title) ? "" : title);
        pageMap.put("path", TextUtils.isEmpty(path) ? "" : path);
        pageMap.put("index", TextUtils.isEmpty(index) ? "" : index);
        if (TextUtils.isEmpty(obj)) {
            pageMap.put("obj", "");
        } else {
            pageMap.put("obj", obj.replaceAll("(\r\n|\r|\n|\n\r)", ""));
        }
        return pageMap;
    }
}
