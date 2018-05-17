package me.zane.easyrouter_merge.merge;

import java.io.IOException;

import me.zane.easyrouter_merge.bean.MergeInfo;
import me.zane.easyrouter_merge.framework.ContextReader;
import me.zane.easyrouter_merge.framework.DirectoryContentProvider;
import me.zane.easyrouter_merge.framework.TransformContext;
import me.zane.easyrouter_merge.log.Log;

/**
 * Created by Zane on 2018/5/17.
 * Email: zanebot96@gmail.com
 */
public class MergeEngine {
    private TransformContext context;

    public MergeEngine(TransformContext context) {
        this.context = context;
    }

    public void startMerge(MergeInfo mergeInfo) {
        ContextReader cr = new ContextReader(context, new DirectoryContentProvider());
        MergeHandler handler = new MergeHandler(context, mergeInfo);

        try {
            cr.accept(handler);
        } catch (Exception e) {
            Log.e("exception in merge modular file: " + e.getLocalizedMessage());
        }
    }
}
