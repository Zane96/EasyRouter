package me.zane.easyrouter_merge.rename;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Zane on 2018/5/17.
 *
 * 这个ASM visitor运作于修改modular中EasyRouterTable类的名字
 * 我们需要判断当前这个class是否是EasyRouterTable类
 * Email: zanebot96@gmail.com
 */
public class RenameClassVisistor extends ClassVisitor {
    private boolean isFindTarget;
    private String finalName;

    public RenameClassVisistor(ClassVisitor cw) {
        super(Opcodes.ASM5, cw);
    }

    public boolean isFindTarget() {
        return isFindTarget;
    }

    public String getFinalName() {
        return finalName;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        finalName = name;
        if ("com/zane/easyrouter_generated/EasyRouterTable".equals(name)) {
            isFindTarget = true;
            finalName = finalName.replace("easyrouter", "easyrouter_" + RenameKey.getKEY());
        }
        super.visit(version, access, finalName, signature, superName, interfaces);
    }
}
