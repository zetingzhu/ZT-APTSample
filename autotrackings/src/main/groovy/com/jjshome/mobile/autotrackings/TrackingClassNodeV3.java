package com.jjshome.mobile.autotrackings;


import com.jjshome.mobile.autotrackings.entiy.AnalyticsHookConfig;
import com.jjshome.mobile.autotrackings.entiy.AnalyticsMethodObj;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zeting
 * @date: 2023/10/23
 * 给点击事件添加埋点
 */
public class TrackingClassNodeV3 extends ClassVisitor {
    private static final String TAG = "ASM-ClassNode";
    private static final String TAG_LAMBDA = "ASM-Lambda";
    public static final Long Version = 20231117L;

    /**
     * 存储 Lambda 和方法对应字节码关系
     */
    private Map<String, AnalyticsMethodObj> mLambdaMethodCells = new HashMap<>();

    public TrackingClassNodeV3(ClassVisitor visitor, String className) {
        super(Opcodes.ASM7, visitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        /**
         * version:52 access:32
         * name:com/zzt/zztapt/act/ActTestJava3$1
         * signature:null
         * superName:java/lang/Object
         * interfaces:[android/view/View$OnClickListener]
         */
        super.visit(version, access, name, signature, superName, interfaces);
    }


    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
    }

    /**
     * 访问标志，方法名，方法描述符，泛型签名和抛出异常列表，返回一个MethodVisitor
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//        System.out.println(TAG + ">>>>>> visitMethod  access:" + access + " name:" + name + " descriptor:" + descriptor + " signature:" + signature + " exceptions:" + Arrays.toString(exceptions));
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        AdviceAdapter newMethodVisitor = new AdviceAdapter(Opcodes.ASM7, methodVisitor, access, name, descriptor) {
            /**
             * lambda表达式和方法 使用
             * @param name the method's name.
             * @param descriptor the method's descriptor (see {@link Type}).
             * @param bsm the bootstrap method.
             * @param bsmArgs the bootstrap method constant arguments. Each argument must be
             *     an {@link Integer}, {@link Float}, {@link Long}, {@link Double}, {@link String}, {@link
             *     Type}, {@link Handle} or { ConstantDynamic} value. This method is allowed to modify
             *     the content of the array so a caller should expect that this array may change.
             */
            @Override
            public void visitInvokeDynamicInsn(String name, String descriptor, Handle bsm, Object... bsmArgs) {
                /**
                 * name:onClick
                 * descriptor:()Landroid/view/View$OnClickListener;
                 * bsm:java/lang/invoke/LambdaMetafactory.metafactory(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; (6)
                 * bsmArgs:[(Landroid/view/View;)V, com/zzt/zztapt/act/ActTestJava3.lambda$onCreate$0(Landroid/view/View;)V (6), (Landroid/view/View;)V]
                 */
                super.visitInvokeDynamicInsn(name, descriptor, bsm, bsmArgs);
//                System.err.println(TAG_LAMBDA + ">>>>>>> visitMethod visitInvokeDynamicInsn name:" + name + " descriptor:" + descriptor + " bsm:" + bsm.toString() + " bsmArgs:" + Arrays.toString(bsmArgs));
                try {
                    String owner = bsm.getOwner();
                    /**
                     * owner: java/lang/invoke/LambdaMetafactory
                     * 代表着 lambda
                     */
                    if (!"java/lang/invoke/LambdaMetafactory".equals(owner)) {
                        return;
                    }
                    /**
                     * desc2: (Landroid/view/View;)V
                     */
                    String desc2 = null;
                    if (bsmArgs[0] instanceof Type) {
                        desc2 = ((Type) bsmArgs[0]).getDescriptor();
                    }
                    /** Landroid/view/View$OnClickListener;onClick(Landroid/view/View;)V
                     *  Desc：Landroid/view/View$OnClickListener;
                     *  name：onClick
                     *  desc2：(Landroid/view/View;)V
                     */
                    String hookKey = Type.getReturnType(descriptor).getDescriptor() + name + desc2;

                    AnalyticsMethodObj sensorsAnalyticsMethodCell = AnalyticsHookConfig.LAMBDA_METHODS.get(hookKey);
                    if (sensorsAnalyticsMethodCell != null) {
                        if (bsmArgs[1] instanceof Handle) {
                            Handle it = (Handle) bsmArgs[1];
                            /**
                             * name:lambda$onCreate$0
                             * desc:(Landroid/view/View;)V
                             */
                            mLambdaMethodCells.put(it.getName() + it.getDesc(), sensorsAnalyticsMethodCell);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onMethodEnter() {
                addClickEvent(methodVisitor, access, name, descriptor, signature, exceptions);
                addItemClickEvent(methodVisitor, access, name, descriptor, signature, exceptions);
                addSwitchCompatClickEvent(methodVisitor, access, name, descriptor, signature, exceptions);
                addRadioGroupCompatClickEvent(methodVisitor, access, name, descriptor, signature, exceptions);

                addLambdaMethod(methodVisitor, access, name, descriptor, signature, exceptions);
                super.onMethodEnter();
            }

        };
        return newMethodVisitor;
    }

    /**
     * lambda 处理
     *
     * @param mv
     * @param access
     * @param name
     * @param desc
     * @param signature
     * @param exceptions
     */
    public void addLambdaMethod(MethodVisitor mv, int access, String name, String desc, String signature, String[] exceptions) {
        if (name.trim().contains("lambda$")) {
            AnalyticsMethodObj lambdaMethodCell = mLambdaMethodCells.get(name + desc);
            if (lambdaMethodCell != null) {
                Type[] types = Type.getArgumentTypes(lambdaMethodCell.getDesc());
                int length = types.length;
                Type[] lambdaTypes = Type.getArgumentTypes(desc);
                // paramStart 为访问的方法参数的下标，从 0 开始
                int paramStart = lambdaTypes.length - length;
                if (paramStart < 0) {
                    return;
                } else {
                    for (int i = 0; i < length; i++) {
                        if (!lambdaTypes[paramStart + i].getDescriptor().equals(types[i].getDescriptor())) {
                            return;
                        }
                    }
                }
                System.err.println(TAG_LAMBDA + ">>>>>>  add lambda  > name:" + (name + desc));

                Boolean isStaticMethod = isStatic(access);
                for (int i = paramStart; i < paramStart + lambdaMethodCell.getParamsCount(); i++) {
                    Integer opCodeA = lambdaMethodCell.getOpcodes().get(i - paramStart);
                    Integer varB = getVisitPosition(lambdaTypes, i, isStaticMethod);
                    mv.visitVarInsn(opCodeA, varB);
                }
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, AnalyticsHookConfig.EVENT_METHOD_CLAZZ, lambdaMethodCell.getAgentName(), lambdaMethodCell.getAgentDesc(), false);
            }
        }
    }

    /**
     * 给 View.OnClick 插入代码
     *
     * @param mv
     * @param name
     * @param descriptor
     */
    public void addClickEvent(MethodVisitor mv, int access, String name, String descriptor, String signature, String[] exceptions) {
        if ("onClick".equals(name) && "(Landroid/view/View;)V".equals(descriptor)) {
            handleViewEventClick(name, mv);
        }
    }

    /**
     * Listview item Click
     *
     * @param mv
     * @param access
     * @param name
     * @param descriptor
     * @param signature
     * @param exceptions
     */
    public void addItemClickEvent(MethodVisitor mv, int access, String name, String descriptor, String signature, String[] exceptions) {
        if ("onItemClick".equals(name) && "(Landroid/widget/AdapterView;Landroid/view/View;IJ)V".equals(descriptor)) {
            handleViewAdapterViewClick(name, mv);
        }
    }

    /**
     * Switch click
     *
     * @param mv
     * @param access
     * @param name
     * @param descriptor
     * @param signature
     * @param exceptions
     */
    public void addSwitchCompatClickEvent(MethodVisitor mv, int access, String name, String descriptor, String signature, String[] exceptions) {
        if ("onCheckedChanged".equals(name) && "(Landroid/widget/CompoundButton;Z)V".equals(descriptor)) {
            handleViewEventClick(name, mv);
        }
    }

    /**
     * Radio Group click
     *
     * @param mv
     * @param access
     * @param name
     * @param descriptor
     * @param signature
     * @param exceptions
     */
    public void addRadioGroupCompatClickEvent(MethodVisitor mv, int access, String name, String descriptor, String signature, String[] exceptions) {
        if ("onCheckedChanged".equals(name) && "(Landroid/widget/RadioGroup;I)V".equals(descriptor)) {
            handleViewEventClick(name, mv);
        }
    }

    /**
     * view点击事件处理
     *
     * @param name
     */
    void handleViewEventClick(String name, MethodVisitor mv) {
        System.err.println(TAG + ">>>>>> add 1 > name:" + name);

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
        System.err.println(TAG + ">>>>>>  add 3  > name:" + name);

        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitVarInsn(Opcodes.ALOAD, 2);
        mv.visitVarInsn(Opcodes.ILOAD, 3);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/jjshome/mobile/datastatistics/DSAgent", "onAdapterClickView", "(Landroid/widget/AdapterView;Landroid/view/View;I)V", false);
    }


    /**
     * 获取参数类型
     *
     * @param descriptor
     */
    public void getASMMethodType(String name, String descriptor) {
        System.out.println(TAG + "****************************************" + name + "**********************************************");

        Type methodType = Type.getMethodType(descriptor);
        int sizes = methodType.getArgumentsAndReturnSizes();
        System.out.println(TAG + ">>>>>> return type: " + methodType.getReturnType().getClassName() + " (" + (sizes & 3) + ')');

        Type[] argTypes = methodType.getArgumentTypes();
        System.out.println(TAG + ">>>>>> " + argTypes.length + " arguments (" + (sizes >> 2) + ')');
        for (int ix = 0; ix < argTypes.length; ix++) {
            System.out.println(TAG + ">>>>>> arg" + ix + ": " + argTypes[ix].getClassName());
        }

        System.out.println(TAG + "****************************************" + name + "**********************************************");
    }

    /**
     * 获取View 参数位置
     *
     * @param descriptor
     * @return
     */
    public int getASMMethodViewByIndex(String className, String descriptor) {
        try {
            if (isNotNull(className)) {
                Type methodType = Type.getMethodType(descriptor);
                Type[] argTypes = methodType.getArgumentTypes();
                for (int k = 0; k < argTypes.length; k++) {
                    if (className.equals(argTypes[k].getClassName())) {
                        return k;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取方法参数下标为 index 的对应 ASM index
     *
     * @param types          方法参数类型数组
     * @param index          方法中参数下标，从 0 开始
     * @param isStaticMethod 该方法是否为静态方法
     * @return 访问该方法的 index 位参数的 ASM index
     */
    Integer getVisitPosition(Type[] types, Integer index, boolean isStaticMethod) {
        if (types == null || index < 0 || index >= types.length) {
            throw new Error("getVisitPosition error");
        }
        if (index == 0) {
            return isStaticMethod ? 0 : 1;
        } else {
            return getVisitPosition(types, index - 1, isStaticMethod) + types[index - 1].getSize();
        }
    }

    boolean isPrivate(int access) {
        return (access & Opcodes.ACC_PRIVATE) != 0;
    }

    boolean isSynthetic(int access) {
        return (access & Opcodes.ACC_SYNTHETIC) != 0;
    }

    boolean isStatic(int access) {
        return (access & Opcodes.ACC_STATIC) != 0;
    }

    boolean isFinal(int access) {
        return (access & Opcodes.ACC_FINAL) != 0;
    }

    public static boolean isNotNull(String str) {
        return str != null && str.trim().length() > 0;
    }
}
