package com.jjshome.mobile.datastatistics.report;

import android.content.Context;

import com.jjshome.mobile.datastatistics.entity.PageInfo;
import com.jjshome.mobile.datastatistics.utils.Common;

import java.util.HashMap;
import java.util.Map;

/**
 * 浏览页面记录
 * WQ on 2018/6/6
 * wq@jjshome.com
 */
public class PageReport implements IReport {
    private PageInfo mPageInfo;
    private String ref;
    private String dtoken;

    public PageReport(PageInfo pageInfo, String ref, String dtoken) {
        mPageInfo = pageInfo;
        this.ref = ref;
        this.dtoken = dtoken;
    }

    @Override
    public String getUrInfo() {
        String url = Common.getBaseUrl() + "1.gif";
        return Common.createUrlFromParams(url, getParams());
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.putAll(Common.getBaseInfo().toRequestMap());
        paramsMap.putAll(Common.getClickInfo(dtoken).toRequestMap());
        paramsMap.putAll(mPageInfo.toRequestMap());
        paramsMap.put("ref", ref);
        return paramsMap;
    }
}
