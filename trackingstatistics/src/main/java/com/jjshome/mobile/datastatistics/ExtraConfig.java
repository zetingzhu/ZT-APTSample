package com.jjshome.mobile.datastatistics;

/**
 * 配置sdk 参数
 * WQ on 2017/4/8
 * wq@jjshome.com
 */

public class ExtraConfig {

    protected boolean mIsDebug = false;//默认不打开日志
    protected String mChannel = null;//默认渠道为jjs
    protected String mPhone;
    protected String mUid;
    protected String mCityCode;

    private static class SingletonHolder {
        static final ExtraConfig instance = new ExtraConfig();
    }

    public static ExtraConfig getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 设置是否打开日志
     */
    public ExtraConfig isDebug(boolean isDebug) {
        mIsDebug = isDebug;
        return this;
    }

    /**
     * 设置当前的渠道
     */
    public ExtraConfig setChannel(String channel) {
        mChannel = channel;
        return this;
    }

    /**
     * 设置当前用户的手机号
     */
    public ExtraConfig setPhone(String phone) {
        mPhone = phone;
        return this;
    }

    /**
     * 设置切换的城市code
     * @param cityCode
     * @return
     */
    public ExtraConfig setCityCode(String cityCode) {
        mCityCode = cityCode;
        return this;
    }

    /**
     * 设置用户的登录id
     * 房产网是userId
     * 内部系统是workerId
     */
    public ExtraConfig setUid(String uid) {
        mUid = uid;
        return this;
    }
}
