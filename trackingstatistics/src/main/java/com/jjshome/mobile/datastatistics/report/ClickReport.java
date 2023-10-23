package com.jjshome.mobile.datastatistics.report;

import com.jjshome.mobile.datastatistics.entity.EventInfo;
import com.jjshome.mobile.datastatistics.utils.Common;

import java.util.HashMap;
import java.util.Map;

/**
 * 点击事件
 * WQ on 2019/1/19
 * wq@jjshome.com
 */
public class ClickReport implements IReport {
    private EventInfo mEventInfo;
    private String pid;
    private String dtoken;

    public ClickReport(EventInfo errorInfo, String pid, String dtoken) {
        mEventInfo = errorInfo;
        this.pid = pid;
        this.dtoken = dtoken;
    }

    @Override
    public String getUrInfo() {
        String url = Common.getBaseUrl() + "5.gif";
        return Common.createUrlFromParams(url, getParams());
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.putAll(Common.getClickInfo(dtoken).toRequestMap());
        paramsMap.putAll(mEventInfo.toRequestMap());
        paramsMap.put("pId", pid);
        return paramsMap;
    }
}
