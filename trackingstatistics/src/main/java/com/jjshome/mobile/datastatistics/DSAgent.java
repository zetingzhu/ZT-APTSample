package com.jjshome.mobile.datastatistics;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

//import com.bun.miitmdid.core.JLibrary;
//import com.bun.miitmdid.core.MdidSdkHelper;
import com.google.android.material.tabs.TabLayout;
import com.jjshome.mobile.datastatistics.entity.EventID;
import com.jjshome.mobile.datastatistics.entity.extra.DJ0001;
import com.jjshome.mobile.datastatistics.marquee.FrameInfo;
import com.jjshome.mobile.datastatistics.marquee.FrameInfoHelper;
//import com.jjshome.mobile.datastatistics.utils.MiitHelper;
import com.jjshome.mobile.datastatistics.utils.ViewUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 统计代理
 * 此项目的功能文档参考https://172.16.2.230/svn/BIG_DATA/trunk/Web/Trunk/bigdata/bigdata-client/doc/com/
 * jjshome/common/analytics/modle/bigdata/AppClientLog.html
 * WQ on 2017/3/3
 * wq@jjshome.com
 */

public class DSAgent {

    /**
     * 请在Application中调用此方法，只是初始化配置文件。
     * sdk初始化延后到onResume等方法触发的是才执行，减少Application的启动时间
     */
    public static void initConfig(final AppConfig appConfig) {
        if (appConfig == null) {
            throw new IllegalArgumentException("请给AppConfig赋值");
        }
        if (appConfig.mContext == null) {
            throw new IllegalArgumentException("请给context赋值");
        }
        if (appConfig.mAppID == null) {
            throw new IllegalArgumentException("请输入AppConfig.appId参数");
        }
        if (appConfig.mServerType == 0) {
            throw new IllegalArgumentException("请输入AppConfig.serverType参数");
        }
        /*if (appConfig.dtoken == null || "".equals(appConfig.dtoken)) {
            throw new IllegalArgumentException("请输入AppConfig.dtoken参数");
        }*/
        try {
//            JLibrary.InitEntry(appConfig.mContext);
//            MdidSdkHelper.InitSdk(appConfig.mContext, true, new MiitHelper(new MiitHelper.AppIdsUpdater() {
//                @Override
//                public void OnIdsAvalid(@NonNull String ids) {
//                    //根方法中,我们如果只需要oaid,则只获取oaid即可
//                    // String oaid=_supplier.getOAID();
//                    appConfig.oaid = ids;
//                }
//            }));

        } catch (Exception e) {
            e.printStackTrace();
        }
        DSManager.getInstance().initAppConfig(appConfig);
    }


    /**
     * 手机所安装应用程序
     *
     * @param context
     */
    public static void recordAppInfo(Context context) {
        if (hasUpLoad()) {
            DSManager.getInstance().recordAppInfo(context);
        }

    }

    private static boolean hasUpLoad() {
        long lastUp = InfoSharedPref.getLastUpLoadAppTime();
        long thisTime = System.currentTimeMillis();
        if (lastUp == 0) {
            return true;
        }
        long jiange = 7 * 24 * 60 * 60 * 1000;
        if (thisTime - lastUp > jiange) {
            return true;
        }
        return false;
    }

    /**
     * activity 的 onResume时候调用
     *
     * @param context 上下文
     */
    public static void onResume(@NonNull Context context) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        DSManager.getInstance().startRecordPage(context);
    }


    /**
     * activity 的 onPause时候调用
     *
     * @param context 上下文
     */
    public static void onPause(@NonNull Context context) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        DSManager.getInstance().endRecordPage(context, DSManager.getInstance().getAppConfig().dtoken);
    }

    /**
     * 通用型事件记录
     *
     * @param eventId 内置的通用型id
     * @param event   此event为orgjson格式数据(Ojbect类型会导致toString方法生成的格式不正确)，不同eventID对应的Object不同.
     *                文档 https://172.16.2.230/svn/BIG_DATA/trunk/Web/Trunk/bigdata/bigdata-client
     *                /doc/com/jjshome/common/analytics/modle/bigdata/em/EventID.html
     */
    public static void onEvent(@NonNull EventID eventId, @NonNull JSONObject event) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        DSManager.getInstance().recordEvent(eventId, event);
    }

    /**
     * 事件记录
     *
     * @param eventId 自己定义的id，以后此id值开发人员可在 "埋点管理平台"查到
     * @param event   事件描述信息，此event为orgjson格式数据(Ojbect类型会导致toString方法生成的格式不正确)
     */
    public static void onEvent(@NonNull String eventId, @NonNull JSONObject event) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        DSManager.getInstance().recordEvent(eventId, event);
    }

    /**
     * 事件记录
     *
     * @param eventId 自己定义的id，以后此id值开发人员可在 "埋点管理平台"查到
     * @param event   事件描述信息，此evnt必须是json 的字符串格式，注意最外层为JSONObject
     *                （使用如fastjson的 JSON.toJSONString ,orgJson的JSONObject.toString()生成）
     */
    public static void onEvent(@NonNull String eventId, @NonNull String event) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        DSManager.getInstance().recordEvent(eventId, event);
    }

    /**
     * 事件记录 推荐简单数据使用此方法统计事件信息，复杂格式推荐使用{@link #onEvent(String, JSONObject)}或{@link #onEvent(String, String)}
     *
     * @param eventId 自己定义的id，以后此id值开发人员可在 "埋点管理平台"查到
     * @param event   事件描述信息，map键值对
     */
    public static void onEvent(@NonNull String eventId, @NonNull final HashMap<String, String> event) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        DSManager.getInstance().recordEvent(eventId, event);
    }

    /**
     * 点击事件
     *
     * @param className 可以用于精确定位view的类，手动埋点的时候使用
     * @param id        view 的id值
     * @param desc      此控件的描述信息
     * @deprecated 新版大数据埋点只有特殊埋点，建议尽快迁移，请使用{@link #onEvent(String, HashMap)} 或其同名方法
     */

    public static void onClickEvent(@NonNull Context context, @NonNull String className, @NonNull int id,
                                    @NonNull String desc) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        String idName = "此控件没有设置id";
        try {
            idName = context.getResources().getResourceEntryName(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DJ0001 dj0001 = new DJ0001();
        dj0001.sign = String.format("%s-%s", className, idName);
        dj0001.name = desc;
        JSONObject event = new JSONObject();
        try {
            event.put("name", dj0001.name);
            event.put("sign", dj0001.sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onEvent(EventID.DJ0001, event);
    }

    /**
     * 点击事件
     *
     * @param context 上下文
     * @param id      view 的id值
     * @param desc    此控件的描述信息
     * @deprecated 新版大数据埋点只有特殊埋点，建议尽快迁移，请使用{@link #onEvent(String, HashMap)} 或其同名方法
     */
    public static void onClickEvent(@NonNull Context context, @NonNull int id, @NonNull String desc) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        String idName = "此控件没有设置id";
        try {
            idName = context.getResources().getResourceEntryName(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DJ0001 dj0001 = new DJ0001();
        dj0001.sign = String.format("%s-%s", context.getClass().getName(), idName);
        dj0001.name = desc;
        JSONObject event = new JSONObject();
        try {
            event.put("name", dj0001.name);
            event.put("sign", dj0001.sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onEvent(EventID.DJ0001, event);
    }

    /**
     * @param id   自定义的id名称
     * @param desc id的描述
     * @deprecated 新版大数据埋点只有特殊埋点，建议尽快迁移，请使用{@link #onEvent(String, HashMap)} 或其同名方法
     */
    public static void onClickEvent(@NonNull String id, @NonNull String desc) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        DJ0001 dj0001 = new DJ0001();
        dj0001.sign = String.format("%s", id);
        dj0001.name = desc;
        JSONObject event = new JSONObject();
        try {
            event.put("name", dj0001.name);
            event.put("sign", dj0001.sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onEvent(EventID.DJ0001, event);
    }

    /**
     * view的点击事件记录，配合自动统计使用
     *
     * @param view 点击的view
     */
    public static void onClickView(android.view.View view) {
        System.out.println("ASM- 埋点：onClickView view：" + view);
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }

        if (view == null) {
            return;
        }
        if (!InfoSharedPref.isAutoTracking()) {
            return;
        }

        viewRecordClick(view);

    }

    public static void viewRecordClick(android.view.View view) {

        try {
            FrameInfo frameInfo = FrameInfoHelper.getFrameInfoFromView(view);
            if (frameInfo == null) {
                return;
            }
            String className = frameInfo.getPage();

            String eid = className + "-" + frameInfo.getPath();
            String obj = ViewUtil.getViewText(view);
            String index = "";
            if (frameInfo.getPosition() != null && frameInfo.getPosition().size() != 0) {
                index = frameInfo.getPosition().get((frameInfo.getPosition().size() - 1));
            }
            DSManager.getInstance().recordClick(eid, obj, className, DSManager.getInstance().getAppConfig().dtoken, className, frameInfo.getPath(), "", index);
        } catch (Exception e) {

        }

    }

    public static void onClickTabLayout(TabLayout.Tab tab) {
        System.out.println("ASM- 埋点：onClickTabLayout view：" + tab);
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        if (tab == null) {
            return;
        }
        View view = tab.getCustomView();
        if (view == null) {
            view = tab.view;
        }
        if (view == null) {
            return;
        }
        if (!InfoSharedPref.isAutoTracking()) {
            return;
        }

        viewRecordClick(view);
    }

    public static void onAdapterClickView(android.widget.AdapterView parent, android.view.View view, int position) {
        System.out.println("ASM- 埋点：onAdapterClickView  parent:" + parent + " > view:" + view);
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        if (parent == null || view == null) {
            return;
        }
        if (!InfoSharedPref.isAutoTracking()) {
            return;
        }

        viewRecordClick(view);
//        String className = ViewUtil.getActivityName(view);
//        String path = getViewInfo(view);
//        String eid = String.format(Locale.CHINA, "%s-%s-%s-position%d", className, getViewInfo(parent), path, position);
//        String obj = ViewUtil.getViewText(view);
//        DSManager.getInstance().recordClick(eid, obj, className, DSManager.getInstance().getAppConfig().dtoken, className, path, "", String.valueOf(position));
    }

    /**
     * 额外的配置信息 当前的渠道信息，用户的手机号 是否输出日志。
     * 注意乐办公及其他内部app需要登录的app设置uid为员工的workerId，房源网uid设置为登录用户的userId
     *
     * @param config 额外的配置信息
     */
    public static void setExtraConfig(@NonNull ExtraConfig config) {
        DSManager.getInstance().setExtraConfig(config);
    }

    /**
     * 设置是否开启无痕埋点
     *
     * @param isAutoTracking 是否开启无痕埋点，默认开启
     */
    public static void setAutoTracking(boolean isAutoTracking) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        InfoSharedPref.setAutoTracking(isAutoTracking);
    }

    /**
     * 记录服务端错误信息
     *
     * @param url 接口地址
     * @param e   网络框架抛出的异常
     */
    public static void onNetError(@NonNull String url, @NonNull Exception e) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        DSManager.getInstance().recordNetError(url, e);
    }

    /**
     * 记录云信错误信息
     *
     * @param error 错误信息
     */
    public static void onIMError(@NonNull String error) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        DSManager.getInstance().recordImError(error);
    }


    /**
     * 记录服务端错误日志
     *
     * @param url 接口地址
     * @param err 网络框架的错误信息
     */
    public static void onNetError(@NonNull String url, @NonNull String err) {
        if (!DSManager.getInstance().isInit()) {//初始化成功
            DSManager.getInstance().init();
        }
        DSManager.getInstance().recordNetError(url, err);
    }

    /**
     * 得到通用的添加到header 中的数据，用于大数据服务端埋点，请在项目使用的http框架中添加此header信息
     * https://172.16.2.230/svn/BIG_DATA/trunk/Web/Trunk/bigdata/bigdata-client/doc/com/jjshome/common/analytics/modle/bigdata/AppClientLog.html
     */
    public static Map<String, String> getCommonHeaders() {
        if (DSManager.getInstance().getAppConfig() == null) {
            return new HashMap<>();
        }
        return DSManager.getInstance().getCommonHeaders();
    }

    public static String getViewInfo(View view) {
        String viewName = view.getClass().getName();
        int viewId = view.getId();
        String viewIdName = "";
        try {
            viewIdName = view.getContext().getResources().getResourceEntryName(viewId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(viewIdName)) {
            return viewName + "-" + viewIdName;
        } else {
            return viewName;
        }
    }
}
