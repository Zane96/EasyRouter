package me.zane.easyrouter_merge.rename;

import org.gradle.api.Project;

import java.io.IOException;

import me.zane.easyrouter_merge.framework.ContextReader;
import me.zane.easyrouter_merge.framework.DirectoryContentProvider;
import me.zane.easyrouter_merge.framework.TransformContext;
import me.zane.easyrouter_merge.log.Log;

/**
 * Created by Zane on 2018/5/17.
 * Email: zanebot96@gmail.com
 */
public class RenameEngine {
    private TransformContext context;

    public RenameEngine(TransformContext context) {
        this.context = context;
    }

    public void stratRename(Project project) {
        RenameKey.init(project);
        ContextReader reader = new ContextReader(context, new DirectoryContentProvider());
        RenameHandler handler = new RenameHandler(context);

        try {
            reader.accept(handler);
        } catch (Exception e) {
            Log.e("exception in rename modular file: " + e.getLocalizedMessage());
        }
    }
}
