
package com.jjshome.mobile.datastatistics.marquee;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class WindowHelper {

    private static Class sPhoneWindowClazz;

    private static Class sPopupWindowClazz;

    private static final String PREFIX_MAIN_WINDOW = "/MainWindow";

    private static final String PREFIX_DIALOG_WINDOW = "/DialogWindow";

    private static final String PREFIX_POPUP_WINDOW = "/PopupWindow";

    private static final String PREFIX_CUSTOM_WINDOW = "/CustomWindow";

    private static boolean sIsInitialized = false;


    private WindowHelper() {
    }

   public static void init() {
        if (!sIsInitialized) {
            String windowManagerClassName;
            if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
                windowManagerClassName = "android.view.WindowManagerGlobal";
            } else {
                windowManagerClassName = "android.view.WindowManagerImpl";
            }

            Class windowManager;

            try {
                windowManager = Class.forName(windowManagerClassName);
                String windowManagerString;
                if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
                    windowManagerString = "sDefaultWindowManager";
                } else if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR2) {
                    windowManagerString = "sWindowManager";
                } else {
                    windowManagerString = "mWindowManager";
                }

                Field viewsField = windowManager.getDeclaredField("mViews");
                Field instanceField = windowManager.getDeclaredField(windowManagerString);
                viewsField.setAccessible(true);

                instanceField.setAccessible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (VERSION.SDK_INT >= VERSION_CODES.M) {
                    try {
                        sPhoneWindowClazz = Class.forName("com.android.internal.policy.PhoneWindow$DecorView");
                    } catch (ClassNotFoundException var5) {
                        sPhoneWindowClazz = Class.forName("com.android.internal.policy.DecorView");
                    }
                } else {
                    sPhoneWindowClazz = Class.forName("com.android.internal.policy.impl.PhoneWindow$DecorView");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                if (VERSION.SDK_INT >= VERSION_CODES.M) {
                    sPopupWindowClazz = Class.forName("android.widget.PopupWindow$PopupDecorView");
                } else {
                    sPopupWindowClazz = Class.forName("android.widget.PopupWindow$PopupViewContainer");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            sIsInitialized = true;
        }
    }


   public static String getSubWindowPrefix(View root) {
        LayoutParams params = root.getLayoutParams();
        if (params instanceof WindowManager.LayoutParams) {
            android.view.WindowManager.LayoutParams windowParams = (android.view.WindowManager.LayoutParams) params;
            int type = windowParams.type;
            if (type == WindowManager.LayoutParams.TYPE_BASE_APPLICATION) {
                return PREFIX_MAIN_WINDOW;
            } else if (type < WindowManager.LayoutParams.LAST_APPLICATION_WINDOW
                    && root.getClass() == sPhoneWindowClazz) {
                return PREFIX_DIALOG_WINDOW;
            } else if (type < WindowManager.LayoutParams.LAST_SUB_WINDOW && root.getClass() == sPopupWindowClazz) {
                return PREFIX_POPUP_WINDOW;
            } else if (type < WindowManager.LayoutParams.LAST_SYSTEM_WINDOW) {
                return PREFIX_CUSTOM_WINDOW;
            }
        }

        //被杀恢复时，view会恢复点击状态，此时root还没有添加到窗口上，默认是Activity吧。
        Class rootClazz = root.getClass();
        if (rootClazz == sPhoneWindowClazz) {
            return PREFIX_MAIN_WINDOW;
        } else {
            return rootClazz == sPopupWindowClazz ? PREFIX_POPUP_WINDOW : PREFIX_CUSTOM_WINDOW;
        }
    }

   public static boolean isDecorView(View rootView) {
        if (!sIsInitialized) {
            init();
        }

        Class rootClass = rootView.getClass();
        return rootClass == sPhoneWindowClazz || rootClass == sPopupWindowClazz;
    }
}
