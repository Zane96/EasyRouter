package me.zane.easyrouter_merge.rename;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Status;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.IOException;

import me.zane.easyrouter_merge.framework.ClassHandler;
import me.zane.easyrouter_merge.framework.DirectoryWriter;
import me.zane.easyrouter_merge.framework.TransformContext;
import me.zane.easyrouter_merge.log.Log;

/**
 * Created by Zane on 2018/5/17.
 *
 * 接受transform异步的input输入，这里接受dir类型的input
 * 如果是我们需要的类，那么丢进ASM进行下一步操作
 * 在ASM跑完了之后，写入到output
 *
 * Email: zanebot96@gmail.com
 */
public class RenameHandler implements ClassHandler{
    private TransformContext context;
    private DirectoryWriter directoryWriter;

    public RenameHandler(TransformContext context) {
        this.context = context;
        directoryWriter = new DirectoryWriter();
    }

    @Override
    public boolean onStart(QualifiedContent content) throws IOException {
        return true;
    }

    @Override
    public void onClassFetch(QualifiedContent content, Status status, String relativePath, byte[] bytes) throws IOException {
        File outputFile = context.getRelativeFile(content);
        byte[] finalBytes = bytes;
        if (relativePath.endsWith(".class") && relativePath.contains("com/zane/easyrouter_generated")) {
            Log.i("1 path: " + relativePath);
            ClassReader classReader = new ClassReader(finalBytes);
            ClassWriter classWriter = new ClassWriter(0);
            RenameClassVisistor classVisistor = new RenameClassVisistor(classWriter);
            classReader.accept(classVisistor, 0);

            if (classVisistor.isFindTarget()) {
                relativePath = classVisistor.getFinalName() + ".class";
                finalBytes = classWriter.toByteArray();
            }
        }

        directoryWriter.write(outputFile, relativePath, finalBytes);
    }

    @Override
    public void onComplete(QualifiedContent content) throws IOException {

    }
}
