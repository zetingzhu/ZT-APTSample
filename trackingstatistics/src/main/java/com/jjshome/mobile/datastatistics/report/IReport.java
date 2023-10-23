package com.jjshome.mobile.datastatistics.report;

import java.util.Map;

/**
 * 上传url
 * WQ on 2018/6/6
 * wq@jjshome.com
 */
public interface IReport {

    /**
     * 组装上报日志的url
     */
    String getUrInfo();

    /**
     * 获取url参数
     */
    Map<String, String> getParams();
}
