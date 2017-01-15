package com.example.zane.router.hook;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;


import com.example.zane.router.router.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class Hooker {

//    public static void hookRouter(Activity activity, Table table){
//        Class<?> activityClass = null;
//        try {
//            Class appCompatActivityClass = Class.forName("android.support.v7.app.AppCompatActivity");
//            Method isSamePackage = appCompatActivityClass.getDeclaredMethod("inSamePackage");
//            isSamePackage.setAccessible(true);
//            if ((Boolean) isSamePackage.invoke(appCompatActivityClass, activity.getClass())){
//                activityClass = activity.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getSuperclass();
//            } else {
//                activityClass = activity.getClass().getSuperclass();
//            }
//            Field mInstrumentation = activityClass.getDeclaredField("mInstrumentation");
//            mInstrumentation.setAccessible(true);
//            Instrumentation mBase = (Instrumentation) mInstrumentation.get(activity);
//            RouterInstrumentation instrumenttation = new RouterInstrumentation(mBase, table);
//            mInstrumentation.set(activity, instrumenttation);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Hook startActivity/startActivityForResult两个方法
     * @param table
     */
    public static void hookRouter(Table table){
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
            RouterInstrumentation instrumenttation = new RouterInstrumentation(mBase, table);
            mInstrumentation.set(mCurrentThread, instrumenttation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

