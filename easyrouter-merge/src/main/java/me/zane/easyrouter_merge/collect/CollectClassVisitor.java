package me.zane.easyrouter_merge.collect;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

import me.zane.easyrouter_merge.bean.MergeInfo;
import me.zane.easyrouter_merge.log.Log;

/**
 * Created by Zane on 2018/5/17.
 *
 * 在这里通过分析jar中modular的EasyRouterTable方法来获取函数的方法Node
 * Email: zanebot96@gmail.com
 */
public class CollectClassVisitor extends ClassVisitor{
    private MergeInfo mergeInfo;
    private boolean isFindTarget;

    public CollectClassVisitor() {
        super(Opcodes.ASM5);
        mergeInfo = new MergeInfo();
    }

    public boolean isFindTarget() {
        return isFindTarget;
    }

    public MergeInfo getMergeInfo() {
        return mergeInfo;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (name.matches("com/zane/easyrouter.*/EasyRouterTable")) {
            isFindTarget = true;
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodNode methodNode = new MethodNode(access, name, desc, signature, exceptions);
        if ("initTable".equals(name)) {
            mergeInfo.addMapMethods(methodNode);
            return methodNode;
        }

        return null;
    }
}
