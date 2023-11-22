package com.common.lib.language;

import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;

/**
 * Created by dufangzhu on 2017/7/17.
 * 切花语言不重启activity的办法，重新设置textview的资源文件即可
 * 1、xml中textView使用LanguageView
 * 2、设置text资源
 * 3、activity 中，切换语言之后调用reLoadLanguage
 */

public interface LanguageView {
    /*xml的schema*/
    public static final String ANDROIDXML = "http://schemas.android.com/apk/res/android";

    //由于setText无法被重写
    void setTextById(@StringRes int id);

    /*手动去掉textId,不然重新加载语言的时候会被重置掉*/
    void setTextWithString(String text);

    /*手动通过TextArray设置语言*/
    void setTextByArrayAndIndex(@ArrayRes int arrId, @StringRes int arrIndex);

    /*修改语言时主要调用的方法*/
    void reLoadLanguage();



}
