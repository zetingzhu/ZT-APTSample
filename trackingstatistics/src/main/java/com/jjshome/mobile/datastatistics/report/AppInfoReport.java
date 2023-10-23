package com.jjshome.mobile.datastatistics.report;

import com.alibaba.fastjson.JSON;
import com.jjshome.mobile.datastatistics.entity.MobileAppInfo;
import com.jjshome.mobile.datastatistics.utils.Common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppInfoReport implements IReport {
    private List<MobileAppInfo> list;

    public AppInfoReport(List<MobileAppInfo> list) {
        this.list = list;
    }

    @Override
    public String getUrInfo() {
        String url = Common.getBaseUrlA7Do() + "a7.do";
        return Common.createUrlFromParamsAes(url, getParams());
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.putAll(Common.getBaseInfo().toRequestMap());
        paramsMap.put("appInfo", JSON.toJSONString(list));
        return paramsMap;
    }
}
