package me.zane.easyrouter_merge.merge;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Status;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.IOException;

import me.zane.easyrouter_merge.bean.MergeInfo;
import me.zane.easyrouter_merge.framework.ClassHandler;
import me.zane.easyrouter_merge.framework.DirectoryContentProvider;
import me.zane.easyrouter_merge.framework.DirectoryWriter;
import me.zane.easyrouter_merge.framework.TransformContext;
import me.zane.easyrouter_merge.log.Log;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;

/**
 * Created by Zane on 2018/5/17.
 *
 * 作用于主modular的dir input
 * Email: zanebot96@gmail.com
 */
public class MergeHandler implements ClassHandler{
    private TransformContext context;
    private MergeInfo mergeInfo;
    private DirectoryWriter directoryWriter;

    public MergeHandler(TransformContext context, MergeInfo mergeInfo) {
        this.context = context;
        this.mergeInfo = mergeInfo;
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
            ClassReader classReader = new ClassReader(finalBytes);
            ClassWriter classWriter = new ClassWriter(0);
            MergeClassVisitor mergeClassVisitor = new MergeClassVisitor(classWriter, mergeInfo);
            classReader.accept(mergeClassVisitor, 0);
            finalBytes = classWriter.toByteArray();
        }

        directoryWriter.write(outputFile, relativePath, finalBytes);
    }

    @Override
    public void onComplete(QualifiedContent content) throws IOException {
    }
}
