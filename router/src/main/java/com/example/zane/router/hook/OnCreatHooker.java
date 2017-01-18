package com.example.zane.router.hook;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 专职回调onCreat()的回调，屏蔽一些不需要的监听
 * Created by zane on 2017/1/18.
 */

public class OnCreatHooker {

    public static void hookOnCreat(Application context, final OnCreatListener listener) {
        context.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                listener.beforeOnCreat(activity, savedInstanceState);
            }
            @Override
            public void onActivityStarted(Activity activity) {
            }
            @Override
            public void onActivityResumed(Activity activity) {
            }
            @Override
            public void onActivityPaused(Activity activity) {
            }
            @Override
            public void onActivityStopped(Activity activity) {
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }
}
