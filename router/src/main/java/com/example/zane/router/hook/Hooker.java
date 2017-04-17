package com.example.zane.router.hook;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.os.Bundle;

import com.example.zane.router.inject.InjectMan;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class Hooker {

    private static final InjectMan injectMan = new InjectMan();

    /**
     * Hook startActivity/startActivityForResult两个方法
     * Hook activity的onCreat()方法
     */
    public static void hookRouter(Application context){
        hookStart();
        OnCreatHooker.hookOnCreat(context, new OnCreatListener() {
            @Override
            public void beforeOnCreat(Activity activity, Bundle savedInstanceState) {
                injectMan.inject(activity, savedInstanceState);
            }
        });
    }

    private static void hookStart() {
        //拿到app的单例ActivityThread实例
        Class<?> activityThread = null;
        try {
            activityThread = Class.forName("android.app.ActivityThread");
            Method method = activityThread.getDeclaredMethod("currentActivityThread");
            method.setAccessible(true);
            Object mCurrentThread = method.invoke(null);

            Field mInstrumentation = activityThread.getDeclaredField("mInstrumentation");
            mInstrumentation.setAccessible(true);
            Instrumentation mBase = (Instrumentation) mInstrumentation.get(mCurrentThread);
            RouterInstrumentation instrumenttation = new RouterInstrumentation(mBase);
            mInstrumentation.set(mCurrentThread, instrumenttation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

