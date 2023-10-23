package com.jjshome.mobile.datastatistics;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.jjshome.mobile.datastatistics.AppConfig.SERVER_OFFLINE_TEST;
import static com.jjshome.mobile.datastatistics.AppConfig.SERVER_ONLINE;
import static com.jjshome.mobile.datastatistics.AppConfig.SERVER_ONLINE_TEST;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.jjshome.mobile.datastatistics.entity.ErrorInfo;
import com.jjshome.mobile.datastatistics.entity.EventID;
import com.jjshome.mobile.datastatistics.entity.EventInfo;
import com.jjshome.mobile.datastatistics.entity.MobileAppInfo;
import com.jjshome.mobile.datastatistics.entity.PageInfo;
import com.jjshome.mobile.datastatistics.entity.extra.JH0001;
import com.jjshome.mobile.datastatistics.report.AppInfoReport;
import com.jjshome.mobile.datastatistics.report.ClickReport;
import com.jjshome.mobile.datastatistics.report.ErrorReport;
import com.jjshome.mobile.datastatistics.report.EventReport;
import com.jjshome.mobile.datastatistics.report.PageReport;
import com.jjshome.mobile.datastatistics.report.UploadData;
import com.jjshome.mobile.datastatistics.utils.Common;
import com.jjshome.mobile.datastatistics.utils.DataLog;
import com.jjshome.mobile.datastatistics.utils.DeviceInfo;
import com.jjshome.mobile.datastatistics.utils.LocationRequest;
import com.jjshome.mobile.datastatistics.utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据统计管理类
 * WQ on 2017/3/3
 * wq@jjshome.com
 */

public class DSManager {
    private boolean init = false;
    public static Context mContext;
    private static Handler workerHandler;
    private AppConfig mAppConfig;
    private long lastConfirmTime = 0;

    private static class SingletonHolder {
        static final DSManager instance = new DSManager();
    }

    public static DSManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 是否初始化
     */
    protected boolean isInit() {
        return init;
    }

    /**
     * 初始化sdk
     */
    protected void init() {
        if (mAppConfig == null) {
            throw new IllegalArgumentException("请在Application中配置DSAgent.initAppConfig");
        }
        HandlerThread workerThread = new HandlerThread("statisticsThread", Process.THREAD_PRIORITY_BACKGROUND);
        workerThread.start();
        workerHandler = new Handler(workerThread.getLooper());
        //异常处理
        CrashHandler.getInstance().init();
        init = true;

        //首次运行，上传激活事件埋点
        checkJiHuoEvent();
    }

    protected void initAppConfig(AppConfig appConfig) {
        mAppConfig = appConfig;
        mContext = mAppConfig.mContext;
        //第一次init的时候生成uuid
        if (!InfoSharedPref.isFirstInit()) {
            InfoSharedPref.setUUID();

            InfoSharedPref.setFirstInit();
        }
    }

    public AppConfig getAppConfig() {
        return mAppConfig;
    }

    /**
     * 页面统计开始
     */
    protected void startRecordPage(final Context context) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                PageInfo pageSession = new PageInfo();
                InfoSharedPref.setRef(InfoSharedPref.getPId());
                pageSession.pId = Common.getActivityName(context);//InfoSharedPref.getPId();
                pageSession.st = Common.formatTime(System.currentTimeMillis());
                InfoSharedPref.setSessionInfo(pageSession);
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }

    /**
     * 页面统计结束
     */
    protected void endRecordPage(final Context context, final String dtoken) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ref = InfoSharedPref.getRef();
                final PageInfo pageInfo = new PageInfo();
                pageInfo.pId = Common.getActivityName(context);
                pageInfo.et = Common.formatTime(System.currentTimeMillis());
                final PageInfo wholePageInfo = InfoSharedPref.updateSessionInfo(pageInfo);
                //InfoSharedPref.setSessionInfo(wholePageInfo);
                DataLog.d(wholePageInfo);
                UploadData.upload(new PageReport(wholePageInfo, ref, dtoken));
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }

    /**
     * 记录事件信息
     */
    protected void recordEvent(@NonNull final EventID eventID, @NonNull final JSONObject event) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EventInfo eventInfo = new EventInfo();
                eventInfo.eId = eventID.name();
                eventInfo.obj = event.toString();
                DataLog.d(eventInfo);
                UploadData.upload(new EventReport(eventInfo));
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }

    /**
     * 记录事件信息
     */
    protected void recordEvent(@NonNull final EventID eventID, @NonNull final String event) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EventInfo eventInfo = new EventInfo();
                eventInfo.eId = eventID.name();
                eventInfo.obj = event;
                DataLog.d(eventInfo);
                UploadData.upload(new EventReport(eventInfo));
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }

    /**
     * 记录事件信息
     *
     * @param eventID 非通用的eventID
     */
    protected void recordEvent(@NonNull final String eventID, @NonNull final JSONObject event) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EventInfo eventInfo = new EventInfo();
                eventInfo.eId = eventID;
                eventInfo.obj = event.toString();
                DataLog.d(eventInfo);
                UploadData.upload(new EventReport(eventInfo));
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }

    /**
     * 记录事件信息
     */
    protected void recordEvent(final String eventID, final String event) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EventInfo eventInfo = new EventInfo();
                eventInfo.eId = eventID;
                eventInfo.obj = event;
                DataLog.d(eventInfo);
                UploadData.upload(new EventReport(eventInfo));
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }

    /**
     * 记录事件信息
     */
    protected void recordEvent(final String eventID, final HashMap<String, String> event) {
        if (event.size() < 1) {
            return;
        }
        HashMap<String, String> cloneEvent = (HashMap<String, String>) event.clone();
        final EventInfo eventInfo = new EventInfo();
        eventInfo.eId = eventID;
        JSONObject jsonEvent = new JSONObject();
        Iterator<Map.Entry<String, String>> it = cloneEvent.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            try {
                jsonEvent.put(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        eventInfo.obj = jsonEvent.toString();
        DataLog.d(eventInfo);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                UploadData.upload(new EventReport(eventInfo));
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }

    protected void recordClick(@NonNull final String eventID, final String obj, final String pid, final String dtoken, final String page, final String path, final String title, final String index) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EventInfo eventInfo = new EventInfo();
                eventInfo.eId = eventID;
                eventInfo.page = page;
                eventInfo.title = title;
                eventInfo.path = path;
                eventInfo.index = index;
                eventInfo.type = 1;
                //去除"\n"方便服务端解析
                String objStr = obj.replace("\n", "");
                eventInfo.obj = objStr;
                DataLog.d(eventInfo);
                UploadData.upload(new ClickReport(eventInfo, pid, dtoken));
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }


    /**
     * 应用信息
     */
    protected void recordAppInfo(final Context context) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (context != null) {
                    List<MobileAppInfo> list = getAppInfo(context);
                    if (list == null || list.size() == 0) {
                        return;
                    }
                    UploadData.upload(new AppInfoReport(list), new StatisticsRequest.NetworkRequestListener() {
                        @Override
                        public void deliverResponse(Integer response) {
                            Log.d("DSManager", "MobileAppInfo upload response:" + response);
                            if (response == 200) {
                                InfoSharedPref.setLastUpLoadAppTime(System.currentTimeMillis());
                            }
                        }
                    });
                }
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }


    private List<MobileAppInfo> getAppInfo(Context context) {
        if (context == null) {
            return null;
        }
        List<MobileAppInfo> list = new ArrayList<>();
        PackageManager pckMan = context.getPackageManager();
        List<PackageInfo> packs = pckMan.getInstalledPackages(0);
        int count = packs.size();
        String name;
        String pageName;
        int installedNum = 0;
        for (int i = 0; i < count; i++) {
            PackageInfo p = packs.get(i);
            ApplicationInfo appInfo = p.applicationInfo;
            /**
             * Value for {@link #flags}: if set, this application is installed in the
             * device's system image.
             */

            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                //系统程序
            } else {

                //不是系统程序
                name = appInfo.loadLabel(pckMan).toString();
                pageName = p.packageName;
                if (!TextUtils.isEmpty(pageName) && !TextUtils.isEmpty(name)) {
                    MobileAppInfo mobileAppInfo = new MobileAppInfo();
                    mobileAppInfo.name = name;
                    mobileAppInfo.pageName = pageName;
                    list.add(mobileAppInfo);
                }
            }
        }
        return list;
    }


    /**
     * 记录网络错误
     */
    protected void recordNetError(final String url, final Exception e) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ErrorInfo errorInfo = new ErrorInfo();
                Writer writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                e.printStackTrace(printWriter);
                Throwable cause = e.getCause();
                while (cause != null) {
                    cause.printStackTrace(printWriter);
                    cause = cause.getCause();
                }
                printWriter.close();
                String errorStack = writer.toString();
                errorInfo.erId = Common.MD5Operation(errorStack);

                errorInfo.type = ErrorInfo.ERR_SERVER;
                errorInfo.err = url + ":" + errorStack;
                DataLog.d(errorInfo);
                UploadData.upload(new ErrorReport(errorInfo));
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }

    /**
     * 记录网络错误
     */
    protected void recordNetError(final String url, final String err) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ErrorInfo errorInfo = new ErrorInfo();
                if (TextUtils.isEmpty(err)) {
                    return;
                }
                errorInfo.erId = Common.MD5Operation(err);
                errorInfo.type = ErrorInfo.ERR_SERVER;
                errorInfo.err = url + ":" + err;
                DataLog.d(errorInfo);
                UploadData.upload(new ErrorReport(errorInfo));
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }


    /**
     * 记录网络错误
     */
    protected void recordImError(final String err) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ErrorInfo errorInfo = new ErrorInfo();
                if (TextUtils.isEmpty(err)) {
                    return;
                }
                errorInfo.erId = Common.MD5Operation((System.currentTimeMillis() + ""));
                errorInfo.type = ErrorInfo.ERR_IM;
                errorInfo.err = err;
                DataLog.d(errorInfo);
                UploadData.upload(new ErrorReport(errorInfo));
            }
        });
        if (workerHandler != null) {
            workerHandler.post(thread);
        }
    }

    /**
     * 返回需要给服务端的header信息
     */
    protected Map<String, String> getCommonHeaders() {
        Map<String, String> headersMap = new HashMap<>(8);
        switch (mAppConfig.mServerType) {
            case SERVER_OFFLINE_TEST:
                //app 接口请求环境 1 (测试环境),  0(生产环境)
                headersMap.put("d", "1");
                break;
            case SERVER_ONLINE_TEST:
                headersMap.put("d", "1");
                break;
            case SERVER_ONLINE:
            default:
                headersMap.put("d", "0");
                break;
        }
        headersMap.put("uuid", InfoSharedPref.getUUID().trim());//唯一标识
        headersMap.put("aid", mAppConfig.mAppID.toString());//APP标识
        headersMap.put("ssid", DeviceInfo.getSSID(DSManager.mContext).trim());//唯一的硬件信息
        headersMap.put("version", DeviceInfo.getVersionName(DSManager.mContext));//房源网app的版本号
        headersMap.put("phoneOS", "android");
        headersMap.put("phoneModel", Common.encodeHeadInfo(DeviceInfo.getMobileModel()));
        headersMap.put("network", DeviceInfo.getCurrentNetType(DSManager.mContext));
        headersMap.put("carries", DeviceInfo.getSimOperatorInfo(DSManager.mContext));
        LocationRequest.LatLng latLng = LocationRequest.getLocation(DSManager.mContext);
        headersMap.put("longitude", latLng == null ? "" : String.valueOf(latLng.lng));
        headersMap.put("latitude", latLng == null ? "" : String.valueOf(latLng.lat));
        if (!TextUtils.isEmpty(InfoSharedPref.getChannel())) {
            headersMap.put("channel", InfoSharedPref.getChannel());
        }
        headersMap.put("cit", TextUtils.isEmpty(InfoSharedPref.getCityCode()) ? "000002" : InfoSharedPref.getCityCode());
        headersMap.put("mac", DeviceInfo.getMac(DSManager.mContext) == null ? "" : DeviceInfo.getMac(DSManager.mContext));
        headersMap.put("imei", DeviceInfo.getDeviceId(DSManager.mContext) == null ? "" : DeviceInfo.getDeviceId(DSManager.mContext));
        headersMap.put("imsi", DeviceInfo.getIMSI(DSManager.mContext) == null ? "" : DeviceInfo.getIMSI(DSManager.mContext));
        headersMap.put("androidid", DeviceInfo.getAndroidId(DSManager.mContext));
        long thisTime = Calendar.getInstance().getTimeInMillis();
        if (thisTime - lastConfirmTime > 90 * 1000) {
            String sid = MD5.encode32(thisTime + InfoSharedPref.getBackUp_SSID());
            headersMap.put("sid", sid);
            InfoSharedPref.setSid(sid);
        } else {
            headersMap.put("sid", InfoSharedPref.getSid());
        }
        String oaid = "";
        if (mAppConfig != null && mAppConfig.oaid != null && mAppConfig.oaid.length() != 0) {
            oaid = mAppConfig.oaid;
        }
        if (!TextUtils.isEmpty(oaid)) {
            if (oaid.contains("-")) {
                headersMap.put("oaid", oaid.replaceAll("-", ""));
            } else {
                headersMap.put("oaid", oaid);
            }
        } else {
            headersMap.put("oaid", "");
        }
//        if (mAppConfig.oaid != null && !mAppConfig.oaid.equals("null") && !mAppConfig.oaid.equals("")) {
//            try {
//                headersMap.put("oaid", mAppConfig.oaid.replaceAll("-", ""));
//            }catch (Exception e){
//                e.printStackTrace();
//                headersMap.put("oaid", "");
//            }
//        } else {
//            headersMap.put("oaid", "");
//        }
        lastConfirmTime = thisTime;
        return headersMap;
    }

    protected void setExtraConfig(@NonNull ExtraConfig config) {
        if (mAppConfig == null) {
            throw new IllegalArgumentException("请在Application中配置DSAgent.initAppConfig");
        }
        DataLog.isDebug = config.mIsDebug;
        if (!TextUtils.isEmpty(config.mChannel)) {
            InfoSharedPref.setChannel(config.mChannel);
        }
        if (!TextUtils.isEmpty(config.mPhone)) {
            InfoSharedPref.setPhone(config.mPhone);
        }
        if (!TextUtils.isEmpty(config.mUid)) {
            InfoSharedPref.setUid(config.mUid);
        }
        if (!TextUtils.isEmpty(config.mCityCode)) {
            InfoSharedPref.setCityCode(config.mCityCode);
        }
    }

    private void checkJiHuoEvent() {
        if (!InfoSharedPref.isFirstJiHuo()) {//没激活
            JH0001 jh0001 = new JH0001();
            jh0001.channel = InfoSharedPref.getChannel();
            JSONObject event = new JSONObject();
            try {
                event.put("channel", jh0001.channel);
                putInCopyParam(event);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            recordEvent(EventID.JH0001, event);
            InfoSharedPref.setFirstJiHuo();
        }
    }

    /**
     * 产品详情特殊事件埋点
     *
     * @param productCode 产品代码
     */
    public void productContractEVK(String productCode) {
        if (DSManager.getInstance().getAppConfig() == null) {
            return;
        }
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        JSONObject event = new JSONObject();
        try {
            event.put("code", TextUtils.isEmpty(productCode) ? "" : productCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recordEvent("A23010301", event);
    }

    private void putInCopyParam(JSONObject event) {
        try {
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
            ClipData data = cm.getPrimaryClip();
            ClipData.Item item = data.getItemAt(0);
            String content = item.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                //{"evk":"DW0001","loc":"http://172.16.3.100/appDownLoad/appDload.html?source=1","ref":"","cookieID":"test","IP":"113.87.200.163"}
                JSONObject jsonObject = new JSONObject(content);
                String evk = jsonObject.getString("evk");
                String loc = jsonObject.getString("loc");
                String ref = jsonObject.getString("ref");
                String cookieID = jsonObject.getString("cookieID");
                String IP = jsonObject.getString("IP");

                event.put("evk", evk);
                event.put("loc", loc);
                event.put("ref", ref);
                event.put("cookieID", cookieID);
                event.put("IP", IP);
            }
        } catch (Exception e) {
        }
    }
}
