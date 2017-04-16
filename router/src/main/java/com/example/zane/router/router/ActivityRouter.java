package com.example.zane.router.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.zane.router.message.Message;
import com.example.zane.router.result.ActivityResultEngine;
import com.example.zane.router.result.OnActivityResultListener;

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
     * @param requestCode
     */
    public void startActivityForResult(Activity activity, Message message, int requestCode, OnActivityResultListener listener){
        Intent intent = new Intent();
        intent.putExtra(ROUTER_MESSAGE, message);
        ActivityResultEngine.startHookFragment(activity, requestCode, intent, listener);
        activity.startActivityForResult(intent, requestCode);
    }
}
