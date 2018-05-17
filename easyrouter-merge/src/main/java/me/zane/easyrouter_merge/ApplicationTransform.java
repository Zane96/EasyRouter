package me.zane.easyrouter_merge;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;

import java.io.IOException;
import java.util.Set;

import me.zane.easyrouter_merge.bean.MergeInfo;
import me.zane.easyrouter_merge.collect.CollectEngine;
import me.zane.easyrouter_merge.framework.TransformContext;
import me.zane.easyrouter_merge.log.Log;
import me.zane.easyrouter_merge.merge.MergeEngine;

/**
 * Created by Zane on 2018/5/17.
 * Email: zanebot96@gmail.com
 */
public class ApplicationTransform extends Transform{
    private static final String TAG = ApplicationTransform.class.getSimpleName();

    @Override
    public String getName() {
        return TAG;
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
        TransformContext context = new TransformContext(transformInvocation);

        CollectEngine collectEngine = new CollectEngine(context);
        MergeInfo mergeInfo = collectEngine.startCollect();

        Log.i("merinfo size: " + mergeInfo.toString());
        MergeEngine mergeEngine = new MergeEngine(context);
        mergeEngine.startMerge(mergeInfo);
    }
}
