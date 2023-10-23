package com.jjshome.mobile.datastatistics;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import android.text.TextUtils;

import com.jjshome.mobile.datastatistics.entity.PageInfo;
import com.jjshome.mobile.datastatistics.utils.DeviceInfo;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 数据统计文件
 * WQ on 2017/3/3
 * wq@jjshome.com
 */

public class InfoSharedPref {
    private static final String file = "rynat_data_info";

    /**
     * 设置 pageInfo
     */
    public static void setSessionInfo(PageInfo pageInfo) {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pId", pageInfo.pId);
            jsonObject.put("st", pageInfo.st);
            jsonObject.put("et", pageInfo.et);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sp.edit().putString("pageInfo", jsonObject.toString()).commit();
    }

    /**
     * 更新pageInfo
     */
    public static PageInfo updateSessionInfo(PageInfo pageInfo) {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        String originalSessionInfo = sp.getString("pageInfo", "");
        if (!TextUtils.isEmpty(originalSessionInfo)) {
            try {
                JSONObject originalSessionInfoJSON = new JSONObject(originalSessionInfo);
                String pageId = (String) originalSessionInfoJSON.get("pId");
                String newPageId = pageInfo.pId;
                if (!TextUtils.isEmpty(pageId) && !TextUtils.isEmpty(newPageId) && pageId.equalsIgnoreCase(newPageId)) {
                    pageInfo.st = originalSessionInfoJSON.getString("st");
                }
            } catch (JSONException | ClassCastException e) {
                e.printStackTrace();
            }
        }
        return pageInfo;
    }

    public static String getPId() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        String originalSessionInfo = sp.getString("pageInfo", "");
        if (!TextUtils.isEmpty(originalSessionInfo)) {
            try {
                JSONObject originalSessionInfoJSON = new JSONObject(originalSessionInfo);
                String pageId = (String) originalSessionInfoJSON.get("pId");
                return pageId;
            } catch (JSONException | ClassCastException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 设置配置文件 当前渠道
     */
    public static void setChannel(@NonNull String channel) {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        sp.edit().putString("channel", channel).commit();
    }

    /**
     * 获取当前渠道
     */
    public static String getChannel() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        String channel = sp.getString("channel", "");
        if (TextUtils.isEmpty(channel)) {//如果没有配置，从 MetaData 中 UMENG_CHANNEL 的获取
            channel = DeviceInfo.getAppMetaData(DSManager.mContext, "UMENG_CHANNEL");
            if (TextUtils.isEmpty(channel)) {
                //默认没有值的渠道当作是googleplay的渠道
                channel = "googleplay";
            }
        }
        return channel;
    }

    /**
     * 设置配置文件 城市code
     */
    public static void setCityCode(@NonNull String channel) {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        sp.edit().putString("cityCode", channel).commit();
    }

    /**
     * 获取配置文件 城市code
     */
    public static String getCityCode() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        return sp.getString("cityCode", "");
    }

    /**
     * 设置记录第一次初始化
     */
    public static void setFirstInit() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        sp.edit().putBoolean("init", true).commit();
    }

    /**
     * 获取是否第一次初始化
     */
    public static boolean isFirstInit() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        return sp.getBoolean("init", false);
    }

    /**
     * 设置记录已经激活
     */
    public static void setFirstJiHuo() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        sp.edit().putBoolean("jiHuo", true).commit();
    }

    /**
     * 获取是否激活
     */
    public static boolean isFirstJiHuo() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        return sp.getBoolean("jiHuo", false);
    }

    /**
     * 获取uuid
     */
    public static String getUUID() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        return sp.getString("uuid", "");
    }

    /**
     * 设置uuid，只有第一次启动sdk的时候设置
     */
    public static void setUUID() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        sp.edit().putString("uuid", UUID.randomUUID().toString()).commit();
    }

    /**
     * 获取手机号
     */
    public static String getPhone() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        return sp.getString("phone", "");
    }

    /**
     * 设置当前用户的手机号
     */
    public static void setPhone(@NonNull String phone) {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        sp.edit().putString("phone", phone).commit();
    }

    /**
     * 获取adid
     */
    public static String getADID() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        return sp.getString("googleadid", "");
    }

    /**
     * 设置当前adid
     */
    public static void setADID(@NonNull String adid) {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        sp.edit().putString("googleadid", adid).commit();
    }

    /**
     * 获取当前用户id
     */
    public static String getUid() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        return sp.getString("uid", "");
    }

    /**
     * 设置当前用户的id
     */
    public static void setUid(@NonNull String uid) {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        sp.edit().putString("uid", uid).commit();
    }

    /**
     * 设置是否启动无埋点
     */
    public static void setAutoTracking(boolean isAutoTracking) {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        sp.edit().putBoolean("autoTracking", isAutoTracking).commit();
    }

    /**
     * 获取是否启动无埋点
     */
    public static boolean isAutoTracking() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        return sp.getBoolean("autoTracking", true);
    }

    /**
     * 获取ssid,优先调用DeviceInfo.getSSID()
     */
    public static String getBackUp_SSID() {
        SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
        return sp.getString("backUp_ssid", "");
    }

    /**
     * 设置ssid，android Q之后通过另外的方式生成的SSID
     */
    public static void setBackUp_SSID(String backUp_ssid) {
        if (!TextUtils.isEmpty(backUp_ssid)) {
            SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
            sp.edit().putString("backUp_ssid", backUp_ssid).commit();
        }
    }

    /**
     * 设置sid会话ID
     */
    public static void setSid(String sid) {
        if (DSManager.mContext != null) {
            SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
            sp.edit().putString("session_sid", sid).apply();
        }
    }

    public static String getSid() {
        if (DSManager.mContext != null) {
            SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
            return sp.getString("session_sid", "");
        }
        return "";
    }

    public static void setRef(String ref) {
        if (DSManager.mContext != null) {
            SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
            sp.edit().putString("ref", ref).apply();
        }
    }

    public static String getRef() {
        if (DSManager.mContext != null) {
            SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
            return sp.getString("ref", "");
        }
        return "";
    }

    public static void setLastUpLoadAppTime(long ref) {
        if (DSManager.mContext != null) {
            SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
            sp.edit().putLong("appInfoLastUp", ref).apply();
        }
    }

    public static long getLastUpLoadAppTime() {
        if (DSManager.mContext != null) {
            SharedPreferences sp = DSManager.mContext.getSharedPreferences(file, Activity.MODE_PRIVATE);
            return sp.getLong("appInfoLastUp", 0);
        }
        return 0;
    }
}
