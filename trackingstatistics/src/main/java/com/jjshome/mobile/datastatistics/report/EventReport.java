package com.jjshome.mobile.datastatistics.report;

import com.jjshome.mobile.datastatistics.InfoSharedPref;
import com.jjshome.mobile.datastatistics.entity.EventInfo;
import com.jjshome.mobile.datastatistics.utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件记录
 * WQ on 2018/6/6
 * wq@jjshome.com
 */
public class EventReport implements IReport {
    private EventInfo mEventInfo;

    public EventReport(EventInfo eventInfo) {
        mEventInfo = eventInfo;
    }

    @Override
    public String getUrInfo() {
        String url = Common.getBaseUrl() + "2.gif";
        return Common.createUrlFromParams(url, getParams());
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.putAll(Common.getBaseInfo().toRequestMap());
        paramsMap.putAll(mEventInfo.toRequestMap());
        paramsMap.put("pId", InfoSharedPref.getPId());
        /*try {
            JSONObject jsonObject = new JSONObject(mEventInfo.obj);
            paramsMap.put("pId", jsonObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return paramsMap;
    }
}
