package com.jjshome.mobile.autotrackings

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TrackingClassVisitor extends ClassVisitor {
    private ClassVisitor classVisitor;
    //类名
    private String className
    // 父类名
    private String superName
    //该类实现的接口
    private String[] interfaces

    public TrackingClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor)
        this.classVisitor = classVisitor
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
        this.superName = superName
        this.interfaces = interfaces
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions)
//        methodVisitor = new AdviceAdapter(Opcodes.ASM6, methodVisitor, access, name, desc) {
//
//            @Override
//            void visitCode() {
//                super.visitCode()
//                //系统的放行
//                if (className.startsWith('android')||className.startsWith("androidx")) {
//                    return
//                }
////                if(!className.contains("com.trade.eight")){
////                    return
////                }
//
//                String methodNameDesc = name + desc
//                //点击事件
//                if (isMatchingInterfaces(interfaces, 'android/view/View$OnClickListener') ) {
//                    //通常模式
//                    if('onClick(Landroid/view/View;)V' == (methodNameDesc)){
//                        handleViewEventClick(name, mv)
//                        return
//                    }else{
//                        //判断lambda 的情况,由于lambda是匿名方法，name和desc分开判断
//                        if(name.trim().startsWith('lambda$')&& isPrivate(access) && isSynthetic(access) && '(Landroid/view/View;)V'==desc){
//                            handleViewEventClick(name, mv)
//                            return
//                        }
//                    }
//
//                    return
//                }
//
//                //列表点击
//                if (isMatchingInterfaces(interfaces, 'android/widget/AdapterView$OnItemClickListener')) {
//                    if('onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V' == (methodNameDesc)){
//                        handleViewAdapterViewClick(name, mv)
//                    }else{
//                        // larson 如果使用lambda写的代码必须是私有访问且isSynthetic，否则直接跳过了（这是为何）
//                        if(name.trim().startsWith('lambda$')&& isPrivate(access) && isSynthetic(access) && '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V'==desc){
//                            handleViewAdapterViewClick(name, mv)
//                            return
//                        }
//                    }
//
//                    return
//                }
//
//                //SwitchCompat
//                if (isMatchingInterfaces(interfaces, 'android/widget/CompoundButton$OnCheckedChangeListener')) {
//                    if('onCheckedChanged(Landroid/widget/CompoundButton;Z)V' == (methodNameDesc)){
//                        handleViewEventClick(name, mv)
//                    }else{
//                        if(name.trim().startsWith('lambda$')&& isPrivate(access) && isSynthetic(access) && '(Landroid/widget/CompoundButton;Z)V'==desc){
//                            handleViewEventClick(name, mv)
//                            return
//                        }
//                    }
//                    return
//                }
//
//                //Radio Group
//                if (isMatchingInterfaces(interfaces, 'android/widget/RadioGroup$OnCheckedChangeListener')) {
//                    if('onCheckedChanged(Landroid/widget/RadioGroup;Z)V' == (methodNameDesc)){
//                        handleViewEventClick(name, mv)
//                    }else{
//                        if(name.trim().startsWith('lambda$')&& isPrivate(access) && isSynthetic(access) && '(Landroid/widget/RadioGroup;Z)V'==desc){
//                            handleViewEventClick(name, mv)
//                            return
//                        }
//                    }
//                    return
//                }
//
//            }
//
//        }
        return methodVisitor
    }

    /**
     * view点击事件处理
     * @param name
     */
    void handleViewEventClick(String name, MethodVisitor mv) {
        mv.visitVarInsn(Opcodes.ALOAD, 1)
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/jjshome/mobile/datastatistics/DSAgent", "onClickView", "(Landroid/view/View;)V", false);
    }

    /**
     * listview ,gridview 列表点击事件
     * @param name
     * @param mv
     */
    void handleViewAdapterViewClick(String name, MethodVisitor mv) {
        mv.visitVarInsn(Opcodes.ALOAD, 1)
        mv.visitVarInsn(Opcodes.ALOAD, 2)
        mv.visitVarInsn(Opcodes.ILOAD, 3)
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/jjshome/mobile/datastatistics/DSAgent", "onAdapterClickView", "(Landroid/widget/AdapterView;Landroid/view/View;I)V", false);
    }

    boolean isMatchingInterfaces(String[] interfaces, String interfaceName) {
        boolean isMatch = false
        if(interfaces != null && interfaces.length > 0){
            // 是否满足实现的接口
            interfaces.each { String inteface ->
                if (inteface == interfaceName) {
                    isMatch = true
                }
            }
        }
        return isMatch
    }

    boolean isPrivate(int access) {
        return (access & Opcodes.ACC_PRIVATE) != 0
    }
    boolean isSynthetic(int access) {
        return (access & Opcodes.ACC_SYNTHETIC) != 0
    }

}