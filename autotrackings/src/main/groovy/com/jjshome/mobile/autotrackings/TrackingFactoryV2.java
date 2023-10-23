package com.jjshome.mobile.autotrackings;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassContext;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationContext;
import com.android.build.api.instrumentation.InstrumentationParameters;

import org.gradle.api.provider.Property;
import org.objectweb.asm.ClassVisitor;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;


/**
 * @author: zeting
 * @date: 2023/10/23
 */
public abstract class TrackingFactoryV2 implements AsmClassVisitorFactory<InstrumentationParameters.None> {

    private static final String TAG = "ASM-Factory";

    @NotNull
    @Override
    public org.objectweb.asm.ClassVisitor createClassVisitor(@NotNull ClassContext classContext, @NotNull ClassVisitor classVisitor) {
        return new TrackingClassNodeV2(classVisitor);
    }

    @Override
    public boolean isInstrumentable(@NotNull ClassData classData) {
        return true;
    }
}