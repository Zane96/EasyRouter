package com.zane.router.inject;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Zane on 2016/12/29.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public interface Inject {
    /**
     * 注入正向传递的数据
     * @param activity
     * @param intent
     */
    void injectParam(Activity activity, Intent intent);

    /**
     * 注入方向传递的数据
     * @param activity
     * @param intent
     */
    void injectResult(Activity activity, Intent intent);
}
