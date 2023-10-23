package com.jjshome.mobile.datastatistics.http;


import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/11.
 */

public class NetworkTaskMap {

    private static NetworkTaskMap networkTaskMap;

    /**
     * @param @return
     * @return NetworkTask
     * @throws
     * @Title: getInstance
     * @Description: 获取对象
     */
    public static synchronized NetworkTaskMap getInstance() {
        if (networkTaskMap == null) {
            networkTaskMap = new NetworkTaskMap();
        }
        return networkTaskMap;
    }

//    public static void OkHttpNoteApi(Context context, String tag, String methodCode, HashMap<String, Object> msgBody, Request event) {
//        Map params = new HashMap();
//        params.put("methodCode", methodCode);
//        params.put("msgBody", JSON.toJSONString(msgBody));
//        JLog.d(Constant.TAG, Constant.getUrlV2(context) + "&methodCode=" + methodCode + "&msgBody=" + JSON.toJSONString(msgBody));
//        HttpUtils.post(Constant.getUrlV2(context), tag, params, event);
//    }

    /**
     * 参数凭借
     * @param methodCode
     * @param msgBody
     * @return
     */
    public static Map<String, String> getMap(String methodCode, HashMap<String, Object> msgBody) {
        Map params = new HashMap();
//        params.put("serviceCode",Constant.SERVICECODE);
//        params.put("methodCode", methodCode);
        params.put("msgBody", JSON.toJSONString(msgBody));
        return params;
    }

}
