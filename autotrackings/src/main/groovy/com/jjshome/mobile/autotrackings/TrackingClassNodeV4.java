package com.jjshome.mobile.autotrackings;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author: zeting
 * @date: 2023/10/23
 */
public class TrackingClassNodeV4 extends ClassNode {
    private static final String TAG = "ASM-ClassNode4 ";
    //类名
    private ClassVisitor classVisitor;

    public TrackingClassNodeV4(ClassVisitor classVisitor) {
        super(Opcodes.ASM7);
        this.classVisitor = classVisitor;
    }

    @Override
    public void visitEnd() {
        methods.forEach(new Consumer<MethodNode>() {
            @Override
            public void accept(MethodNode methodNode) {
                handleMethodInsn(methodNode);
            }
        });

        if (classVisitor != null) {
            accept(classVisitor);
        }
    }

    private void handleMethodInsn(MethodNode methodNode) {
        InsnList insnList = methodNode.instructions;
        ListIterator<AbstractInsnNode> iterator = insnList.iterator();
        while (iterator.hasNext()) {
            AbstractInsnNode insnNode = iterator.next();
            if (insnNode instanceof MethodInsnNode) {
                System.out.println(TAG + "handleMethod class: " + name + ",method=" + methodNode.name + ", desc=" + methodNode.desc);
            }
        }
    }
}
