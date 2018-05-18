package me.zane.easyrouter_merge.collect;

import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Status;
import com.android.utils.FileUtils;
import com.google.common.io.Files;

import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.zane.easyrouter_merge.bean.MergeInfo;
import me.zane.easyrouter_merge.framework.ClassHandler;
import me.zane.easyrouter_merge.framework.JarWriter;
import me.zane.easyrouter_merge.framework.TransformContext;
import me.zane.easyrouter_merge.log.Log;

/**
 * Created by Zane on 2018/5/17.
 * Email: zanebot96@gmail.com
 */
public class CollectHandler implements ClassHandler{
    private Map<QualifiedContent, JarWriter> map = new ConcurrentHashMap<>();
    private TransformContext context;
    private MergeInfo mergeInfo;

    public CollectHandler(TransformContext context) {
        this.context = context;
        mergeInfo = new MergeInfo();
    }

    public MergeInfo getMergeInfo() {
        return mergeInfo;
    }

    @Override
    public boolean onStart(QualifiedContent content) throws IOException {
        if (content instanceof JarInput) {
            JarInput jarInput = (JarInput) content;
            File targetFile = context.getRelativeFile(content);
            switch (jarInput.getStatus()) {
                case REMOVED:
                    FileUtils.deleteIfExists(targetFile);
                    return false;
                case CHANGED:
                    FileUtils.deleteIfExists(targetFile);
                default:
                    Files.createParentDirs(targetFile);
                    map.put(content, new JarWriter(targetFile));
            }
            return true;
        }
        return false;
    }

    @Override
    public void onClassFetch(QualifiedContent content, Status status, String relativePath, byte[] bytes) throws IOException {
        //收集完毕之后不用写入output
        if (relativePath.endsWith(".class") && relativePath.contains("com/zane/easyrouter")) {
            ClassReader classReader = new ClassReader(bytes);
            CollectClassVisitor collectClassVisitor = new CollectClassVisitor();
            classReader.accept(collectClassVisitor, 0);

            if (collectClassVisitor.isFindTarget()) {
                mergeInfo.combine(collectClassVisitor.getMergeInfo());
            } else {
                JarWriter jarWriter = map.get(content);
                jarWriter.write(relativePath,bytes);
            }
        } else {
            JarWriter jarWriter = map.get(content);
            jarWriter.write(relativePath,bytes);
        }
    }

    @Override
    public void onComplete(QualifiedContent content) throws IOException {
        if (content instanceof JarInput && ((JarInput) content).getStatus() != Status.REMOVED) {
            map.get(content).close();
        }
    }
}
