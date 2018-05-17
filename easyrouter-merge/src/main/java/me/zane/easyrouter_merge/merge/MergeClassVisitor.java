package me.zane.easyrouter_merge.merge;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

import me.zane.easyrouter_merge.bean.MergeInfo;

/**
 * Created by Zane on 2018/5/17.
 * Email: zanebot96@gmail.com
 */
public class MergeClassVisitor extends ClassVisitor{
    private MergeInfo mergeInfo;

    public MergeClassVisitor(ClassVisitor cv, MergeInfo mergeInfo) {
        super(Opcodes.ASM5, cv);
        this.mergeInfo = mergeInfo;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor next = super.visitMethod(access, name, desc, signature, exceptions);
        if ("initTable".equals(name)) {
            for (MethodNode node : mergeInfo.getMapMethods()) {
                insertMethod(node, next);
            }
        }

        return next;
    }

    private void insertMethod(MethodNode methodNode, MethodVisitor mv){
        AbstractInsnNode insnNode = methodNode.instructions.getFirst();
        while (insnNode!=null && insnNode.getOpcode() != Opcodes.RETURN){
            insnNode.accept(mv);
            insnNode = insnNode.getNext();
        }
    }
}
