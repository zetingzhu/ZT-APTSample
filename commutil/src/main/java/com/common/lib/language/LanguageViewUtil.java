package com.common.lib.language;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dufangzhu on 2017/7/17.
 * 语言切换之后更新资源
 */

public class LanguageViewUtil {
    /**
     * 语言切换之后更新资源
     * @param view
     */
    public static void updateViewLanguage(View view) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int count = vg.getChildCount();
                for (int i = 0; i < count; i++) {
                    updateViewLanguage(vg.getChildAt(i));
                }
            } else if (view instanceof LanguageView) {
                LanguageView tv = (LanguageView) view;
                tv.reLoadLanguage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
