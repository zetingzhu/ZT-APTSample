package com.jjshome.mobile.datastatistics;

import android.content.Context;


import androidx.annotation.IntDef;
import com.jjshome.mobile.datastatistics.entity.AppID;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * app初始化的必要配置
 * WQ on 2017/4/22
 * wq@jjshome.com
 */

public class AppConfig {
    /**
     * 上报日志的服务器类别
     */
    @IntDef({SERVER_OFFLINE_TEST, SERVER_ONLINE_TEST, SERVER_ONLINE, SERVER_ONLINE_TEST1, SERVER_ONLINE_TEST2})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ServerType {

    }

    /**
     * 线下测试环境
     */
    public static final int SERVER_OFFLINE_TEST = 1;
    /**
     * 线上测试环境
     */
    public static final int SERVER_ONLINE_TEST = 2;
    /**
     * 生产环境
     */
    public static final int SERVER_ONLINE = 3;
    /**
     * 配合大数据测试的ip
     */
    public static final int SERVER_ONLINE_TEST1 = 4;
    /**
     * 配合大数据测试的ip
     */
    public static final int SERVER_ONLINE_TEST2 = 5;

    public final AppID mAppID;
    public final int mServerType;
    public final Context mContext;
    public String dtoken;
    public String oaid;

    private AppConfig(final Builder builder) {
        mAppID = builder.mAppID;
        mServerType = builder.mServerType;
        mContext = builder.context;
        dtoken = builder.dtoken;
    }

    public static class Builder {
        private AppID mAppID;
        private int mServerType;
        private Context context;
        private String dtoken;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        public AppConfig build() {
            return new AppConfig(this);
        }

        /**
         * 设置应用的appID
         */
        public Builder appId(AppID appID) {
            this.mAppID = appID;
            return this;
        }

        /**
         * 设置接口的类别（线下测试，线上测试，生产环境）
         */
        public Builder serverType(@ServerType int serverType) {
            this.mServerType = serverType;
            return this;
        }

        /**
         * 设备令牌
         */
        public Builder dtoken(String dtoken) {
            this.dtoken = dtoken;
            return this;
        }
    }
}
