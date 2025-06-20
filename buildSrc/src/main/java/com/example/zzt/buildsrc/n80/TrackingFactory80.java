package com.example.zzt.buildsrc.n80;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassContext;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationParameters;

import org.objectweb.asm.ClassVisitor;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;


/**
 * @author: zeting
 * @date: 2023/10/23
 * 埋点插件
 */
public abstract class TrackingFactory80 implements AsmClassVisitorFactory<InstrumentationParameters.None> {
    private static final String TAG = "ASM-Factory";

    @Override
    public ClassVisitor createClassVisitor(ClassContext classContext, ClassVisitor classVisitor) {

        System.out.println(TAG + " classContext:" + classContext.getCurrentClassData());
        System.out.println(TAG + " classVisitor:" + classVisitor.toString());

        // 方法中间插入埋点
        return new TrackingClassNode80(classVisitor, classContext.getCurrentClassData().getClassName());
    }


    @Override
    public boolean isInstrumentable(@NotNull ClassData classData) {
        String className = classData.getClassName();
        /**********************测试数据**********************/
        if (className.startsWith("com.zzt.zztapt.")) {
            return true;
        }
        /**********************测试数据**********************/
        return className.startsWith("com.trade.eight.");
    }
}