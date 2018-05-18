package me.zane.easyrouter_merge;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.google.common.collect.Sets;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Set;

import me.zane.easyrouter_merge.framework.TransformContext;
import me.zane.easyrouter_merge.rename.RenameEngine;

/**
 * Created by Zane on 2018/5/17.
 * Email: zanebot96@gmail.com
 */
public class LibraryTransform extends Transform{
    private static final String TAG = LibraryTransform.class.getSimpleName();
    private Project project;

    public LibraryTransform(Project project) {
        this.project = project;
    }

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
        return Sets.immutableEnumSet(QualifiedContent.Scope.PROJECT);
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        TransformContext context = new TransformContext(transformInvocation);
        RenameEngine engine = new RenameEngine(context);
        engine.stratRename(project);
    }
}
