package me.zane.easyrouter_merge.framework;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.TransformInvocation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by gengwanpeng on 17/4/26.
 *
 * A data sets collect all jar info and pre-analysis result.
 *
 */
public class TransformContext {

    private TransformInvocation invocation;

    private Collection<JarInput> allJars;
    private Collection<JarInput> addedJars;
    private Collection<JarInput> removedJars;
    private Collection<JarInput> changedJars;
    private Collection<DirectoryInput> allDirs;


    public TransformContext(TransformInvocation invocation) {
        this.invocation = invocation;
        init();
    }

    /**
     * start collect.
     */
    private void init() {
        allJars = new ArrayList<>(invocation.getInputs().size());
        addedJars = new ArrayList<>(invocation.getInputs().size());
        changedJars = new ArrayList<>(invocation.getInputs().size());
        removedJars = new ArrayList<>(invocation.getInputs().size());
        allDirs = new ArrayList<>(invocation.getInputs().size());
        invocation.getInputs().forEach(it -> {
            it.getJarInputs().forEach(j -> {
                allJars.add(j);
                if (invocation.isIncremental()) {
                    switch (j.getStatus()) {
                        case ADDED:
                            addedJars.add(j);
                            break;
                        case REMOVED:
                            removedJars.add(j);
                            break;
                        case CHANGED:
                            changedJars.add(j);
                    }
                }
            });
            allDirs.addAll(it.getDirectoryInputs());
        });
    }


    public boolean isIncremental() {
        return invocation.isIncremental();
    }

    public Collection<JarInput> getAllJars() {
        return Collections.unmodifiableCollection(allJars);
    }

    public Collection<DirectoryInput> getAllDirs() {
        return Collections.unmodifiableCollection(allDirs);
    }

    public Collection<JarInput> getAddedJars() {
        return Collections.unmodifiableCollection(addedJars);
    }

    public Collection<JarInput> getChangedJars() {
        return Collections.unmodifiableCollection(changedJars);
    }

    public Collection<JarInput> getRemovedJars() {
        return Collections.unmodifiableCollection(removedJars);
    }

    public File getRelativeFile(QualifiedContent content) {
        return invocation.getOutputProvider().getContentLocation(content.getName(), content.getContentTypes(), content.getScopes(),
                (content instanceof JarInput ? Format.JAR : Format.DIRECTORY));
    }

    public void clear() throws IOException {
        invocation.getOutputProvider().deleteAll();
    }

    @Override
    public String toString() {
        return "TransformContext{" +
                       "allJars=" + allJars +
                       ", addedJars=" + addedJars +
                       ", removedJars=" + removedJars +
                       ", changedJars=" + changedJars +
                       ", allDirs=" + allDirs +
                       '}';
    }
}

