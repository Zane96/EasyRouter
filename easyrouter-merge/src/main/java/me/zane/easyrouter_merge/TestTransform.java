package me.zane.easyrouter_merge;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;

import java.io.IOException;
import java.util.Set;

import me.zane.easyrouter_merge.log.Log;


/**
 * Created by Zane on 2018/4/19.
 * Email: zanebot96@gmail.com
 */
public class TestTransform extends Transform{
    @Override
    public String getName() {
        return "test_transform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        System.out.println("------------------开始----------------------");
        System.out.println("这是我们的自定义插件!");
        System.out.println("------------------结束----------------------->");
        System.out.println("length: " + transformInvocation.getInputs());
    }
}
