package com.jjshome.mobile.datastatistics.utils;

import com.google.android.material.tabs.TabLayout;
import android.view.View;

import java.lang.reflect.Field;

/**
 * TabLayout 点击定制化
 */
public class TabLayoutOnClickerUtils {
    public static void initTabListener(TabLayout mTabLayout) {
        //获取Tab的数量
        int tabCount = mTabLayout.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab == null) {
                continue;
            }
//这里使用到反射，拿到Tab对象后获取Class
            Class c = tab.getClass();
            try {
                //c.getDeclaredField 获取私有属性。
//“mView”是Tab的私有属性名称，类型是 TabView ，TabLayout私有内部类。
                Field field = c.getDeclaredField("mView");
                if (field == null) {
                    continue;
                }
                field.setAccessible(true);
                final View view = (View) field.get(tab);
                if (view == null) {
                    continue;
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

            } catch (NoSuchFieldException e) {

                e.printStackTrace();

            } catch (IllegalAccessException e) {

                e.printStackTrace();

            }
        }
    }
}
