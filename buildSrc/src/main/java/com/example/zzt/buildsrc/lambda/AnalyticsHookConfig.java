package com.example.zzt.buildsrc.lambda;

import org.objectweb.asm.Opcodes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zeting
 * @date: 2023/11/13
 * 配置的分析信息
 */
public class AnalyticsHookConfig {
    /**
     * 分析埋点插入类
     */
    public static String EVENT_METHOD_CLAZZ = "com/jjshome/mobile/datastatistics/DSAgent";

    public static Map<String, AnalyticsMethodObj> LAMBDA_METHODS = new HashMap<>();

    static {
        addLambdaMethod(
                new AnalyticsMethodObj(
                        "onClick",
                        "(Landroid/view/View;)V",
                        "Landroid/view/View$OnClickListener;",
                        "onClickView",
                        "(Landroid/view/View;)V",
                        1, 1,
                        List.of(Opcodes.ALOAD))
        );
        addLambdaMethod(
                new AnalyticsMethodObj(
                        "onCheckedChanged",
                        "(Landroid/widget/CompoundButton;Z)V",
                        "Landroid/widget/CompoundButton$OnCheckedChangeListener;",
                        "onClickView",
                        "(Landroid/view/View;)V",
                        1, 1,
                        List.of(Opcodes.ALOAD)
                )
        );
        addLambdaMethod(
                new AnalyticsMethodObj(
                        "onCheckedChanged",
                        "(Landroid/widget/RadioGroup;I)V",
                        "Landroid/widget/RadioGroup$OnCheckedChangeListener;",
                        "onClickView",
                        "(Landroid/view/View;)V",
                        1, 1,
                        List.of(Opcodes.ALOAD)
                )
        );
        addLambdaMethod(
                new AnalyticsMethodObj(
                        "onItemClick",
                        "(Landroid/widget/AdapterView;Landroid/view/View;IJ)V",
                        "Landroid/widget/AdapterView$OnItemClickListener;",
                        "onAdapterClickView",
                        "(Landroid/widget/AdapterView;Landroid/view/View;I)V",
                        1, 3,
                        Arrays.asList(Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD)
                )
        );
    }

    private static void addLambdaMethod(AnalyticsMethodObj sensorsAnalyticsMethodCell) {
        if (sensorsAnalyticsMethodCell != null) {
            LAMBDA_METHODS.put(
                    sensorsAnalyticsMethodCell.getParent() + sensorsAnalyticsMethodCell.getName() + sensorsAnalyticsMethodCell.getDesc(),
                    sensorsAnalyticsMethodCell
            );
        }
    }
}
