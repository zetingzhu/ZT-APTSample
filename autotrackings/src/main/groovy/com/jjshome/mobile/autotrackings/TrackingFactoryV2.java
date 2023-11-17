package com.jjshome.mobile.autotrackings;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassContext;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationParameters;

import org.objectweb.asm.ClassVisitor;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;


/**
 * @author: zeting
 * @date: 2023/10/23
 */
public abstract class TrackingFactoryV2 implements AsmClassVisitorFactory<InstrumentationParameters.None> {
    private static final String TAG = "ASM-Factory";

    @Override
    public ClassVisitor createClassVisitor(ClassContext classContext, ClassVisitor classVisitor) {

        System.out.println(TAG + " classContext:" + classContext.getCurrentClassData().toString());
        System.out.println(TAG + " classVisitor:" + classVisitor.toString());

        // 方法中间插入埋点
        return new TrackingClassNodeV3(classVisitor, classContext.getCurrentClassData().getClassName());
//        return new TrackingClassNodeV2(classVisitor);
//        return new TimeCostClassVisitor(classVisitor, classContext.getCurrentClassData().getClassName());
//        return new TrackingClassNodeV4(classVisitor);
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