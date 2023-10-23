package com.jjshome.mobile.datastatistics.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 额外的接口传输字段
 * WQ on 2019/1/19
 * wq@jjshome.com
 */
public class ClickExtInfo extends BasisInfo {
    public String scs;//屏幕分辨率
    public String isc;//是否可以打电话
    public String osl;//操作系统语言
    public String dpi;//屏幕密度
    public String bat;//电池电量
    public String ip;//IP地址
    public String ded;//设备方向
    public String det;//设备类型
    //public String pid;//当前页的页面标识类
    //public String ref;//来源页
    public String dtoken;//设备令牌

    @Override
    public Map<String, String> toRequestMap() {
        Map<String, String> pageMap = new HashMap<>();
        pageMap.put("np", np);
        pageMap.put("bd", bd);
        pageMap.put("osv", osv);
        pageMap.put("pe", pe);
        pageMap.put("uid", uid);
        pageMap.put("bua", bua);
        pageMap.put("scs", scs);
        pageMap.put("osl", osl);
        pageMap.put("dpi", dpi);
        pageMap.put("bat", bat);
        pageMap.put("isc", isc);
        pageMap.put("ip", ip);
        pageMap.put("ded", ded);
        pageMap.put("det", det);
        pageMap.put("dtoken", dtoken);
        //pageMap.put("ref", ref);
        return pageMap;
    }
}
