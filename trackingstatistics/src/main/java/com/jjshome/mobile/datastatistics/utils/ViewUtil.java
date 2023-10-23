package com.jjshome.mobile.datastatistics.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.lang.reflect.Method;
import java.util.List;

/**
 * WQ on 2019/1/18
 * wq@jjshome.com
 */
public class ViewUtil {
    public static String getActivityName(View view) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            //context本身是Activity的实例
            return context.getClass().getCanonicalName();
        } else if (context instanceof ContextWrapper) {
            //Activity有可能被系统＂装饰＂，看看context.base是不是Activity
            Activity activity = getActivityFromContextWrapper(context);
            if (activity != null) {
                return activity.getClass().getCanonicalName();
            } else {
                //如果从view.getContext()拿不到Activity的信息（比如view的context是Application）,则返回当前栈顶Activity的名字
                return getTopActivity(context);
            }
        }
        return "";
    }


    public static String getViewText(View view){
        CharSequence viewText = null;
        try {
            Class<?> switchCompatClass = null;
            try {
                switchCompatClass = Class.forName("android.support.v7.widget.SwitchCompat");
            } catch (Exception e) {
                //ignored
            }

            if (view instanceof CheckBox) { // CheckBox
                CheckBox checkBox = (CheckBox) view;
                viewText = checkBox.getText();
            } else if (switchCompatClass != null && switchCompatClass.isInstance(view)) {
                CompoundButton switchCompat = (CompoundButton) view;
                Method m;
                if (switchCompat.isChecked()) {
                    m = view.getClass().getMethod("getTextOn");
                } else {
                    m = view.getClass().getMethod("getTextOff");
                }
                viewText = (String)m.invoke(view);
            } else if (view instanceof RadioButton) { // RadioButton
                RadioButton radioButton = (RadioButton) view;
                viewText = radioButton.getText();
            } else if (view instanceof ToggleButton) { // ToggleButton
                ToggleButton toggleButton = (ToggleButton) view;
                boolean isChecked = toggleButton.isChecked();
                if (isChecked) {
                    viewText = toggleButton.getTextOn();
                } else {
                    viewText = toggleButton.getTextOff();
                }
            } else if (view instanceof Button) { // Button
                Button button = (Button) view;
                viewText = button.getText();
            } else if (view instanceof CheckedTextView) { // CheckedTextView
                CheckedTextView textView = (CheckedTextView) view;
                viewText = textView.getText();
            } else if (view instanceof TextView) { // TextView
                TextView textView = (TextView) view;
                viewText = textView.getText();
                if (TextUtils.isEmpty(viewText)){
                    viewText=textView.getHint();
                }
            }else if (view instanceof ViewGroup) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    viewText = getViewGroupText(stringBuilder, (ViewGroup) view);
                    if (!TextUtils.isEmpty(viewText)) {
                        viewText = viewText.toString().substring(0, viewText.length() - 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){

        }
        if(TextUtils.isEmpty(viewText)){
            return "";
        }else{
            if(viewText.length()>=20){
                return viewText.subSequence(0,20).toString();
            }else {
                return viewText.toString();
            }
        }

    }

    public static String getViewGroupText(StringBuilder stringBuilder, ViewGroup root) {
            try {
                if (root == null) {
                    return stringBuilder.toString();
                }

                final int childCount = root.getChildCount();
                for (int i = 0; i < childCount; ++i) {
                    final View child = root.getChildAt(i);

                    if (child.getVisibility() != View.VISIBLE) {
                        continue;
                    }

                    if (child instanceof ViewGroup) {
                        getViewGroupText(stringBuilder, (ViewGroup) child);
                    } else {
                        Class<?> switchCompatClass = null;
                        try {
                            switchCompatClass = Class.forName("android.support.v7.widget.SwitchCompat");
                        } catch (Exception e) {
                            //ignored
                        }
                        CharSequence viewText = null;
                        if (child instanceof CheckBox) {
                            CheckBox checkBox = (CheckBox) child;
                            viewText = checkBox.getText();
                        } else if (switchCompatClass != null && switchCompatClass.isInstance(child)) {
                            CompoundButton switchCompat = (CompoundButton) child;
                            Method method;
                            if (switchCompat.isChecked()) {
                                method = child.getClass().getMethod("getTextOn");
                            } else {
                                method = child.getClass().getMethod("getTextOff");
                            }
                            viewText = (String)method.invoke(child);
                        } else if (child instanceof RadioButton) {
                            RadioButton radioButton = (RadioButton) child;
                            viewText = radioButton.getText();
                        } else if (child instanceof ToggleButton) {
                            ToggleButton toggleButton = (ToggleButton) child;
                            boolean isChecked = toggleButton.isChecked();
                            if (isChecked) {
                                viewText = toggleButton.getTextOn();
                            } else {
                                viewText = toggleButton.getTextOff();
                            }
                        } else if (child instanceof Button) {
                            Button button = (Button) child;
                            viewText = button.getText();
                        } else if (child instanceof CheckedTextView) {
                            CheckedTextView textView = (CheckedTextView) child;
                            viewText = textView.getText();
                        } else if (child instanceof TextView) {
                            TextView textView = (TextView) child;
                            viewText = textView.getText();
                            if (TextUtils.isEmpty(viewText)){
                                viewText=textView.getHint();
                            }
                        }
                        if (!TextUtils.isEmpty(viewText)) {
                            stringBuilder.append(viewText.toString());
                            stringBuilder.append("-");
                        }
                        if(stringBuilder.length()>=20){//产品要求只显示前20个字符
                            return stringBuilder.substring(0,20);
                        }
                    }
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return stringBuilder.toString();
            }
        }



    private static Activity getActivityFromContextWrapper(Context context) {
        context = ((ContextWrapper) context).getBaseContext();
        if (context instanceof Activity) {
            //context本身是Activity的实例
            return (Activity) context;
        } else {
            return null;
        }
    }

    /**
     * 获得栈中最顶层的Activity
     */
    private static String getTopActivity(Context context) {
        android.app.ActivityManager manager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            return (runningTaskInfos.get(0).topActivity).getShortClassName();
        } else {
            return null;
        }
    }
}
