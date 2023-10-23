package com.jjshome.mobile.datastatistics.entity;

/**
 * 手机安装应用信息
 */
public class MobileAppInfo {

    /**
     * 应用名称
     */
    public String name;
    /**
     * 包名
     */
    public String pageName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public String toString() {
        return "MobileAppInfo{" +
                "name='" + name + '\'' +
                ", pageName='" + pageName + '\'' +
                '}';
    }
}
