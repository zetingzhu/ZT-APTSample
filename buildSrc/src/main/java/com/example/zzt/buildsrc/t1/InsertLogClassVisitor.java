package com.example.zzt.buildsrc.t1;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.Arrays;

/**
 * @author: zeting
 * @date: 2023/11/17
 * 插入测试代码
 */
public class InsertLogClassVisitor extends ClassVisitor {
    private static final String TAG = "ASM-buildSrc-Log";
    String className;

    public InsertLogClassVisitor(ClassVisitor classVisitor, String className) {
        super(Opcodes.ASM5, classVisitor);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        AdviceAdapter newMethodVisitor = new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, descriptor) {
            @Override
            protected void onMethodEnter() {
                super.onMethodEnter();
                System.out.println(TAG + ">>>>>> onMethodEnter > access:" + access + " name:" + name + " signature:" + signature + " exceptions:" + Arrays.toString(exceptions));
                // 方法开始
                mv.visitLdcInsn("TAG");
                mv.visitLdcInsn(className + " -> " + name);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                mv.visitInsn(Opcodes.POP);
            }
        };
        return newMethodVisitor;
    }
}