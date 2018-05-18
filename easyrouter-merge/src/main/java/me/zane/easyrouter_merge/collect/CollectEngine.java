package me.zane.easyrouter_merge.collect;

import java.io.IOException;

import me.zane.easyrouter_merge.bean.MergeInfo;
import me.zane.easyrouter_merge.framework.ContextReader;
import me.zane.easyrouter_merge.framework.JarContentProvider;
import me.zane.easyrouter_merge.framework.TransformContext;
import me.zane.easyrouter_merge.log.Log;

/**
 * Created by Zane on 2018/5/17.
 * Email: zanebot96@gmail.com
 */
public class CollectEngine {
    private TransformContext context;

    public CollectEngine(TransformContext context) {
        this.context = context;
    }

    public MergeInfo startCollect() {
        ContextReader contextReader = new ContextReader(context, new JarContentProvider());
        CollectHandler handler = new CollectHandler(context);

        try {
            contextReader.accept(handler);
        } catch (Exception e) {
            Log.e("exception in collect modular file: " + e.getLocalizedMessage());
        }

        return handler.getMergeInfo();
    }
}
