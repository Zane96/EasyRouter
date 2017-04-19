package com.zane.router.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zane.router.message.Message;
import com.zane.router.result.OnActivityResultListener;
import com.zane.router.result.ActivityResultEngine;

/**
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class ActivityRouter extends BaseRouter{

    @Override
    void startRoute(Context context, Intent rawIntent) {
        context.startActivity(rawIntent);
    }

    /**
     * startActivityForResult()
     * 这里不应该再是activity去启动组件而是由fragment去启动并hook
     * @param activity 可以确定是Activity的context
     * @param message
     */
    public void startActivityForResult(Activity activity, Message message, OnActivityResultListener listener){
        Intent intent = new Intent();
        intent.putExtra(ROUTER_MESSAGE, message);
        ActivityResultEngine.startHookFragment(activity, 0, intent, listener);
    }

    /**
     * setResult()
     * @param activity
     * @param message
     */
    public void setResult(Activity activity, int resultCode, Message message) {
        Intent intent = new Intent();
        intent.putExtra(ROUTER_MESSAGE, message);
        activity.setResult(resultCode, intent);
    }
}
