package com.jjshome.mobile.autotrackings.t2;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.Objects;

/**
 * @author: zeting
 * @date: 2023/10/23
 */
public class TimeCostClassVisitor extends ClassVisitor {
    private static final String TAG = TimeCostClassVisitor.class.getSimpleName();
    String className;

    public TimeCostClassVisitor(ClassVisitor classVisitor, String className) {
        super(Opcodes.ASM5, classVisitor);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        AdviceAdapter newMethodVisitor = new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, descriptor) {
            @Override
            protected void onMethodEnter() {
//                System.out.println(TAG + ">>>>>> onMethodEnter > access:" + access + " name:" + name + " signature:" + signature + " exceptions:" + Arrays.toString(exceptions));

                // 方法开始
                if (isNeedVisiMethod(name)) {
                    System.out.println(TAG + ">>>>>> onMethodEnter >  name:" + name);
                    mv.visitLdcInsn(name);
                    mv.visitLdcInsn(className);
                    mv.visitMethodInsn(
                            INVOKESTATIC,
                            "com.jjshome.mobile.autotrackings/TimeCache",
                            "putStartTime",
                            "(Ljava/lang/String;Ljava/lang/String;)V",
                            false
                    );
                }
                super.onMethodEnter();
            }

            @Override
            protected void onMethodExit(int opcode) {
                if (isNeedVisiMethod(name)) {
                    System.out.println(TAG + ">>>>>> onMethodExit > opcode:" + opcode);

                    mv.visitLdcInsn(name);
                    mv.visitLdcInsn(className);
                    mv.visitMethodInsn(
                            INVOKESTATIC,
                            "com.jjshome.mobile.autotrackings/TimeCache",
                            "putEndTime",
                            "(Ljava/lang/String;Ljava/lang/String;)V",
                            false
                    );
                }
                super.onMethodExit(opcode);
            }
        };

        return newMethodVisitor;
    }

    private boolean isNeedVisiMethod(String name) {
        return !Objects.equals(name, "putStartTime")
                && !Objects.equals(name, "putEndTime")
                && !Objects.equals(name, "<clinit>")
                && !Objects.equals(name, "printlnTime")
                && !Objects.equals(name, "<init>");
    }
}
