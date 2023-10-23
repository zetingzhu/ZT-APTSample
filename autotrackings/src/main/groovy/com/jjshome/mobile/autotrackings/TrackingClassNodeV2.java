package com.jjshome.mobile.autotrackings;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
//import org.objectweb.asm.tree.AbstractInsnNode;
//import org.objectweb.asm.tree.FieldInsnNode;
//import org.objectweb.asm.tree.InsnList;
//import org.objectweb.asm.tree.LdcInsnNode;
//import org.objectweb.asm.tree.LocalVariableNode;
//import org.objectweb.asm.tree.MethodInsnNode;
//import org.objectweb.asm.tree.MethodNode;
//import org.objectweb.asm.tree.ParameterNode;
//import org.objectweb.asm.tree.TypeInsnNode;
//import org.objectweb.asm.tree.VarInsnNode;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.function.Consumer;

/**
 * @author: zeting
 * @date: 2023/10/23
 */
public class TrackingClassNodeV2 extends ClassNode {

    private ClassVisitor classVisitor;

    public TrackingClassNodeV2(ClassVisitor visitor) {
        super(Opcodes.ASM7);
        classVisitor = visitor;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();

    }
}
