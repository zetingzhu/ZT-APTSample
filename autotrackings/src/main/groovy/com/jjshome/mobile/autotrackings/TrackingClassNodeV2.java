package com.jjshome.mobile.autotrackings;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: zeting
 * @date: 2023/10/23
 */
public class TrackingClassNodeV2 extends ClassNode {
    private static final String TAG = "ASM-ClassNode";
    private ClassVisitor classVisitor;
    //类名
    private String className;
    // 父类名
    private String superName;
    //该类实现的接口
    private String[] interfaces;

    public TrackingClassNodeV2(ClassVisitor visitor) {
        super(Opcodes.ASM7);
        classVisitor = visitor;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.superName = superName;
        this.interfaces = interfaces;
    }

    /**
     * 访问标志，方法名，方法描述符，泛型签名和抛出异常列表，返回一个MethodVisitor
     *
     * @param access     the method's access flags (see {@link Opcodes}). This parameter also indicates if the method is synthetic and/or deprecated.
     * @param name       the method's name.
     * @param desc       the method's descriptor (see {Type}).
     * @param signature  the method's signature. May be {@literal null} if the method parameters,  return type and exceptions do not use generic types.
     * @param exceptions the internal names of the method's exception classes (see Type#getInternalName()}). May be {@literal null}.
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//        System.out.println(TAG + ">>>>>> visitMethod > access:" + access + " name:" + name + " desc:" + desc + " signature:" + signature + " exceptions:" + Arrays.toString(exceptions));
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        mv = new MethodVisitor(Opcodes.ASM6, mv) {
            /**
             * 调用visitCode()方法，调用一次。
             */
            @Override
            public void visitCode() {
                super.visitCode();
            }

            /**
             * 调用visitMaxs()方法，调用一次。
             * @param maxStack maximum stack size of the method.
             * @param maxLocals maximum number of local variables for the method.
             */
            @Override
            public void visitMaxs(int maxStack, int maxLocals) {
                super.visitMaxs(maxStack, maxLocals);
//                System.out.println(TAG + ">>>>>> visitMaxs name:" + name + " access:" + access);
                //系统的放行
                if (className.startsWith("android") || className.startsWith("androidx")) {
                    return;
                }

                String methodNameDesc = name + desc;
//                System.out.println(TAG + ">>>>>> visitMaxs methodNameDesc:" + methodNameDesc + " > interfaces:" + Arrays.toString(interfaces));

                //点击事件
                if (isMatchingInterfaces(interfaces, "android/view/View$OnClickListener")) {
                    //通常模式
                    if ("onClick(Landroid/view/View;)V".equals(methodNameDesc)) {
                        System.out.println(TAG + ">>>>>> name:" + name + " desc:" + desc);
                        handleViewEventClick(name, mv);
                        return;
                    } else {
                        //判断lambda 的情况,由于lambda是匿名方法，name和desc分开判断
                        if (name.trim().startsWith("lambda$") && isPrivate(access) && isSynthetic(access) && "(Landroid/view/View;)V".equals(desc)) {
                            handleViewEventClick(name, mv);
                            return;
                        }
                    }

                    return;
                }

                //列表点击
                if (isMatchingInterfaces(interfaces, "android/widget/AdapterView$OnItemClickListener")) {
                    if ("onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V".equals(methodNameDesc)) {
                        handleViewAdapterViewClick(name, mv);
                    } else {
                        // larson 如果使用lambda写的代码必须是私有访问且isSynthetic，否则直接跳过了（这是为何）
                        if (name.trim().startsWith("lambda$") && isPrivate(access) && isSynthetic(access) && "(Landroid/widget/AdapterView;Landroid/view/View;IJ)V".equals(desc)) {
                            handleViewAdapterViewClick(name, mv);
                            return;
                        }
                    }

                    return;
                }

                //SwitchCompat
                if (isMatchingInterfaces(interfaces, "android/widget/CompoundButton$OnCheckedChangeListener")) {
                    if ("onCheckedChanged(Landroid/widget/CompoundButton;Z)V".equals(methodNameDesc)) {
                        handleViewEventClick(name, mv);
                    } else {
                        if (name.trim().startsWith("lambda$") && isPrivate(access) && isSynthetic(access) && "(Landroid/widget/CompoundButton;Z)V".equals(desc)) {
                            handleViewEventClick(name, mv);
                            return;
                        }
                    }
                    return;
                }

                //Radio Group
                if (isMatchingInterfaces(interfaces, "android/widget/RadioGroup$OnCheckedChangeListener")) {
                    if ("onCheckedChanged(Landroid/widget/RadioGroup;Z)V".equals(methodNameDesc)) {
                        handleViewEventClick(name, mv);
                    } else {
                        if (name.trim().startsWith("lambda$") && isPrivate(access) && isSynthetic(access) && "(Landroid/widget/RadioGroup;Z)V".equals(desc)) {
                            handleViewEventClick(name, mv);
                            return;
                        }
                    }
                    return;
                }
            }


            /**
             * 调用visitEnd()方法，调用一次。
             */
            @Override
            public void visitEnd() {
                super.visitEnd();
            }

            @Override
            public void visitInsn(int opcode) {
                super.visitInsn(opcode);
//                System.out.println(TAG + ">>>>>> visitInsn opcode:" + opcode);
            }
        };
        return mv;
    }

    /**
     * view点击事件处理
     *
     * @param name
     */
    void handleViewEventClick(String name, MethodVisitor mv) {
        System.out.println(TAG + ">>>>>> handleViewEventClick name:" + name);

        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/jjshome/mobile/datastatistics/DSAgent", "onClickView", "(Landroid/view/View;)V", false);
    }

    /**
     * listview ,gridview 列表点击事件
     *
     * @param name
     * @param mv
     */
    void handleViewAdapterViewClick(String name, MethodVisitor mv) {
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitVarInsn(Opcodes.ALOAD, 2);
        mv.visitVarInsn(Opcodes.ILOAD, 3);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/jjshome/mobile/datastatistics/DSAgent", "onAdapterClickView", "(Landroid/widget/AdapterView;Landroid/view/View;I)V", false);
    }

    boolean isMatchingInterfaces(String[] interfaces, String interfaceName) {
        boolean isMatch = false;
        if (interfaces != null && interfaces.length > 0) {
            // 是否满足实现的接口
            for (String inteface : interfaces) {
                if (Objects.equals(inteface, interfaceName)) {
                    isMatch = true;
                }
            }
        }
        return isMatch;
    }

    boolean isPrivate(int access) {
        return (access & Opcodes.ACC_PRIVATE) != 0;
    }

    boolean isSynthetic(int access) {
        return (access & Opcodes.ACC_SYNTHETIC) != 0;
    }
}
