package com.zzt.zztapt;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.jjshome.mobile.datastatistics.AppConfig;
import com.jjshome.mobile.datastatistics.DSAgent;
import com.jjshome.mobile.datastatistics.ExtraConfig;
import com.jjshome.mobile.datastatistics.entity.AppID;

/**
 * @author: zeting
 * @date: 2023/11/9
 */
public class MyApp extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAutoTracking();
    }

    /**
     * 初始化自动埋点
     */
    private void initAutoTracking() {
        DSAgent.initConfig(new AppConfig.Builder(this)
                .appId(AppID.APP001)
                .serverType(AppConfig.SERVER_ONLINE)
                .dtoken("dtoken")
                .build());
        DSAgent.setExtraConfig(new ExtraConfig().isDebug(true).setChannel("debug"));
    }
}
