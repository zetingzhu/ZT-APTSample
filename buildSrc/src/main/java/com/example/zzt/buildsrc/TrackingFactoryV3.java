package com.example.zzt.buildsrc;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassContext;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationParameters;
import com.example.zzt.buildsrc.t1.InsertLogClassVisitor;
import com.example.zzt.buildsrc.t2.TimeCostClassVisitor;

import org.objectweb.asm.ClassVisitor;

import java.util.Arrays;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;


/**
 * @author: zeting
 * @date: 2023/10/23
 */
public abstract class TrackingFactoryV3 implements AsmClassVisitorFactory<InstrumentationParameters.None> {
    private static final String TAG = "ASM-buildSrc-Factory";

    @Override
    public ClassVisitor createClassVisitor(ClassContext classContext, ClassVisitor classVisitor) {
        System.out.println(TAG + ">>>>>> createClassVisitor 开始  classContext：" + classContext.getCurrentClassData().getClassName() + "  > classVisitor:" + classVisitor);

        // 插入日志
//        return new InsertLogClassVisitor(classVisitor, classContext.getCurrentClassData().getClassName());
        // 插入开始结束时间
        return new TimeCostClassVisitor(classVisitor, classContext.getCurrentClassData().getClassName());
        // 各种点击事件
//        return new TrackingClassNodeV3(classVisitor, classContext.getCurrentClassData().getClassName());
    }


    @Override
    public boolean isInstrumentable(@NotNull ClassData classData) {
        String className = classData.getClassName();
        if (className.startsWith("com.zzt.zztapt.")) {
            return true;
        }
        return false;
    }
}