package com.jjshome.mobile.datastatistics.report;

import com.jjshome.mobile.datastatistics.entity.ErrorInfo;
import com.jjshome.mobile.datastatistics.utils.Common;
import java.util.HashMap;
import java.util.Map;

/**
 * 错误
 * WQ on 2018/6/6
 * wq@jjshome.com
 */
public class ErrorReport implements IReport {
    private ErrorInfo mErrorInfo;

    public ErrorReport(ErrorInfo errorInfo) {
        mErrorInfo = errorInfo;
    }

    @Override
    public String getUrInfo() {
        String url = Common.getBaseUrl() + "3.gif";
        return Common.createUrlFromParams(url, getParams());
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.putAll(Common.getBaseInfo().toRequestMap());
        paramsMap.putAll(mErrorInfo.toRequestMap());
        return paramsMap;
    }
}
