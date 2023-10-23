package com.jjshome.mobile.datastatistics.entity;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 基本的统计信息
 * WQ on 2017/4/6
 * wq@jjshome.com
 */

public class BasisInfo {
    //    /**
    //     * 网络IP
    //     * example: 125.125.236.12
    //     */
    //    public String ip;

    /**
     * 网络供应商
     * example: 1-移动 2-联通 3-电信 4-其他
     */
    public String np = "";

    /**
     * 手机品牌
     */
    public String bd = "";

    /**
     * 操作系统版本
     */
    public String osv = "";

    /**
     * 手机号码
     */
    public String pe = "";

    /**
     * 应用登录id，例如房源网就是用户的userId，经纪人+就是workerId
     */
    public String uid = "";
    /**
     * 浏览器签名
     */
    public String bua = "";
    /**
     * 0代表测试环境 1代表正式环境
     */
    public String d = "";
    /**
     * 谷歌的adid
     */
    public String did = "";

    public Map<String, String> toRequestMap() {
        Map<String, String> pageMap = new HashMap<>();
        pageMap.put("np", np);
        pageMap.put("bd", bd);
        pageMap.put("osv", osv);
        pageMap.put("pe", pe);
        pageMap.put("uid", uid);
        pageMap.put("bua", bua);
        pageMap.put("d ", d );
        pageMap.put("did ", did );
        return pageMap;
    }
}
