package com.jjshome.mobile.datastatistics.marquee;


import androidx.viewpager.widget.ViewPager;

public class ClassHelper {

    private static boolean sHasAndroidXViewPager;

    // todo recyclerView, support包其他列表

    private static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Throwable var2) {
            return false;
        }
    }

  public  static boolean instanceOfAndroidXViewPager(Object view) {
        return sHasAndroidXViewPager && view instanceof ViewPager;
    }


    private static final boolean sHasTransform = false;

    static {
        if (!sHasTransform) {
            sHasAndroidXViewPager = hasClass("android.support.v4.view.ViewPager");
        }
    }
}
