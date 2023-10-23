package com.jjshome.mobile.datastatistics.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.jjshome.mobile.datastatistics.AppConfig;
import com.jjshome.mobile.datastatistics.BuildConfig;
import com.jjshome.mobile.datastatistics.DSManager;
import com.jjshome.mobile.datastatistics.InfoSharedPref;
import com.jjshome.mobile.datastatistics.MyBase64;
import com.jjshome.mobile.datastatistics.entity.BasisInfo;
import com.jjshome.mobile.datastatistics.entity.ClickExtInfo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 工具类
 * WQ on 2017/3/6
 * wq@jjshome.com
 */
public class Common {

    /**
     * 获取app包名
     */
    public static String getActivityName(Context context) {
        if (context == null) {
            return "";
        }
        if (context instanceof Activity) {
            return ((Activity) context).getClass().getName();
        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (checkPermissions(context, "android.permission.GET_TASKS")) {
                try {
                    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                    return cn.getClassName();
                } catch (Exception e) {
                    return "";
                }
            } else {
                DataLog.d("android.permission.GET_TASKS 权限获取不到");
                return "";
            }
        }
    }

    /**
     * 检查指定的权限是否获取
     */
    public static boolean checkPermissions(Context context, String permission) {
        PackageManager pm = context.getPackageManager();
        return pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 格式化时间
     */
    public static String formatTime(long evaluationTime) {
        if (evaluationTime == 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(evaluationTime);
        return sdf.format(date);
    }

    /**
     * 生成字符串的md5值
     */
    public static String MD5Operation(String info) {
        try {
            byte strTemp[] = info.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(strTemp);
            byte b[] = md.digest();

            BigInteger bigInt = new BigInteger(1, b);
            return bigInt.toString(16);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 实体转map
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 生成上报url
     */
    public static String createUrlFromParams(String url, Map<String, String> params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            for (Map.Entry<String, String> urlParam : params.entrySet()) {
                //对参数进行 utf-8 编码,防止头信息传中文
                String urlValue = URLEncoder.encode(urlParam.getValue(), "UTF-8");
                sb.append(urlParam.getKey()).append("=").append(urlValue).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
        }
        return url;
    }

    /**
     * 对内容进行加密
     * "111111111111111111111"
     * A4twXBzSyUj+ujPQPpAhaEz2nXGFoyWSgtmeK4QV8Nw=
     * A4twXBzSyUj%2BujPQPpAhaEz2nXGFoyWSgtmeK4QV8Nw%3D
     * String encryptValueStr = encrypt("111111111111111111111", EncryptedSPUtil.getSKey(), EncryptedSPUtil.getIvParameter());
     * Log.d("7n.gif", "加密字符串 ：" + encryptValueStr);
     * String urlEncodeStr = URLEncoder.encode(encryptValueStr, "UTF-8");
     * Log.d("7n.gif", "加密字符串 url ：" + urlEncodeStr);
     *
     * @param url
     * @param params
     * @return
     */
    public static String createUrlFromParamsAes(String url, Map<String, String> params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            StringBuilder sbParams = new StringBuilder();
            for (Map.Entry<String, String> urlParam : params.entrySet()) {
                String urlValue = urlParam.getValue();
                sbParams.append(urlParam.getKey()).append("=").append(urlValue).append("&");
            }
            sbParams.deleteCharAt(sbParams.length() - 1);
            try {
                String encryptValue = encrypt(sbParams.toString(), EncryptedSPUtil.getSKey(), EncryptedSPUtil.getIvParameter());
                String urlEncode = URLEncoder.encode(encryptValue, "UTF-8");
                sb.append(urlEncode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }


    /**
     * 上报加密
     *
     * @param encData
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String encData, String secretKey, String iv) throws Exception {
        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 16) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec ivps = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
        return MyBase64.encode(encrypted);// 此处使用BASE64做转码。
    }

    /**
     * 获取基本的上传url
     */
    public static String getBaseUrl() {
        AppConfig appConfig = DSManager.getInstance().getAppConfig();
        switch (appConfig.mServerType) {
            case AppConfig.SERVER_OFFLINE_TEST:
                return "https://d.xtspd.com/";
            case AppConfig.SERVER_ONLINE_TEST:
                return "https://d.xtspd.com/";
            case AppConfig.SERVER_ONLINE_TEST1:
                return "https://d.xtspd.com/";
            case AppConfig.SERVER_ONLINE_TEST2:
                return "https://d.xtspd.com/";
            case AppConfig.SERVER_ONLINE:
            default:
                return "https://d.xtspd.com/";
        }
    }

    public static String getBaseUrlA7Do() {
        AppConfig appConfig = DSManager.getInstance().getAppConfig();
        switch (appConfig.mServerType) {
            case AppConfig.SERVER_OFFLINE_TEST:
                return "https://api.rummy-hi.in/";
            case AppConfig.SERVER_ONLINE_TEST:
                return "https://api.rummy-hi.in/";
            case AppConfig.SERVER_ONLINE_TEST1:
                return "https://api.rummy-hi.in/";
            case AppConfig.SERVER_ONLINE_TEST2:
                return "https://api.rummy-hi.in/";
            case AppConfig.SERVER_ONLINE:
            default:
                return "https://api.rummy-hi.in/";
        }
    }

    /**
     * 获取上传和请求数据url
     */
    public static String getBaseApi() {
        AppConfig appConfig = DSManager.getInstance().getAppConfig();
        switch (appConfig.mServerType) {

            case AppConfig.SERVER_OFFLINE_TEST:
                return "https://d.xtspd.com/";
            case AppConfig.SERVER_ONLINE_TEST:
                return "https://d.xtspd.com/";
            case AppConfig.SERVER_ONLINE_TEST1:
                return "https://d.xtspd.com/";
            case AppConfig.SERVER_ONLINE_TEST2:
                return "https://d.xtspd.com/";
            case AppConfig.SERVER_ONLINE:
            default:
                return "https://d.xtspd.com/";
        }
    }

    /**
     * 获取每个页面请求基本信息
     */
    public static BasisInfo getBaseInfo() {
        BasisInfo basisInfo = new BasisInfo();
        //运营商信息
        basisInfo.np = DeviceInfo.getSimOperatorInfo(DSManager.mContext);
        //品牌信息
        basisInfo.bd = DeviceInfo.getMobileBrand();
        // 获取当前操作系统版本号
        basisInfo.osv = DeviceInfo.getOSVersion();
        basisInfo.pe = InfoSharedPref.getPhone();
        basisInfo.did = InfoSharedPref.getADID();
        basisInfo.uid = InfoSharedPref.getUid();
        basisInfo.bua = DeviceInfo.getWebUserAgent(DSManager.mContext);
        basisInfo.d = BuildConfig.DEBUG_LOG ? "0" : "1";
        return basisInfo;
    }

    public static ClickExtInfo getClickInfo(String dtoken) {
        ClickExtInfo clickExtInfo = new ClickExtInfo();
        clickExtInfo.np = DeviceInfo.getSimOperatorInfo(DSManager.mContext);
        clickExtInfo.bd = DeviceInfo.getMobileBrand();
        clickExtInfo.osv = DeviceInfo.getOSVersion();
        clickExtInfo.pe = InfoSharedPref.getPhone();
        clickExtInfo.did = InfoSharedPref.getADID();
        clickExtInfo.uid = InfoSharedPref.getUid();
        clickExtInfo.bua = DeviceInfo.getWebUserAgent(DSManager.mContext);
        clickExtInfo.d = BuildConfig.DEBUG_LOG ? "0" : "1";
        clickExtInfo.scs = DeviceInfo.getWidhAndHigh(DSManager.mContext);
        clickExtInfo.osl = Locale.getDefault().getLanguage();
        clickExtInfo.dpi = DeviceInfo.getDensity(DSManager.mContext);
        clickExtInfo.bat = DeviceInfo.getBattery(DSManager.mContext) + "%";
        clickExtInfo.isc = DeviceInfo.canCallPhone(DSManager.mContext);
        clickExtInfo.ip = DeviceInfo.getIp(DSManager.mContext);
        clickExtInfo.ded = DeviceInfo.getPhoneOrientation(DSManager.mContext);
        clickExtInfo.det = DeviceInfo.getIsPad(DSManager.mContext);
        //clickExtInfo.pid = DSManager.mContext.getClass().getName();
        //clickExtInfo.ref =;
        clickExtInfo.dtoken = dtoken;//InfoSharedPref.getUid();
        return clickExtInfo;
    }

    /**
     * 解决okhttp 不支持特殊字符的情况(包含中文)
     *
     * @param headInfo
     * @return
     */
    public static String encodeHeadInfo(String headInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

    /**
     * @param str
     * @return
     * @deprecated map的toString 转化为Map
     */
    public static Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, String> map = new HashMap<>();
        for (String item : strs) {
            if (item.endsWith("=")) {//传过来的value为空字符串,为了不抛出异常，添加一个空格的字符串
                item = item + " ";
            }
            String key = item.split("=")[0].trim();
            String value = item.split("=")[1];
            map.put(key, value);
        }
        return map;
    }

    private static final String SCREENSHOT_DIRECTORY = "/datastatic";

    private static File filesDir;

    @Nullable
    @WorkerThread
    public static String getScreenshotDirectoryRoot(@NonNull Activity context) {
        if (filesDir == null) {
            filesDir = context.getFilesDir();
        }
        if (filesDir == null) {
            return null;
        }
        return filesDir.getAbsolutePath() + SCREENSHOT_DIRECTORY;
    }
}
