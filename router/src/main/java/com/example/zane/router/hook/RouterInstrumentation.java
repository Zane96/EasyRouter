package com.example.zane.router.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;

import com.example.zane.router.EasyRouter;
import com.example.zane.router.router.BaseRouter;
import com.example.zane.router.router.Table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Zane on 2016/11/28.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class RouterInstrumentation extends Instrumentation {

    private static final String TAG = RouterInstrumentation.class.getSimpleName();

    private Instrumentation mBase;
    private Table routerTable;

    public RouterInstrumentation(Instrumentation mBase, Table routerTable) {
        this.mBase = mBase;
        this.routerTable = routerTable;
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        try {
            Method callOnCreat = Instrumentation.class.getDeclaredMethod("callActivityOnCreate", Activity.class, Bundle.class);
            callOnCreat.setAccessible(true);
            Intent intent = activity.getIntent();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity t,
                                            Intent rawIntent, int requestCode, Bundle options){
        String url = rawIntent.getStringExtra(BaseRouter.ROUTER_URL);
        Method execStart = null;
        try {
            execStart = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class, IBinder.class,
                    IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            execStart.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if ("activity://".equals(url.substring(0, 11))){
            try {
                Class target = routerTable.queryTable(url);
                Intent intent = new Intent(who, target);
                intent.putExtras(rawIntent.getExtras());

                return (ActivityResult) execStart.invoke(mBase, who, contextThread, token, t,
                        intent, requestCode, options);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                return (ActivityResult) execStart.invoke(mBase, who, contextThread, token, t,
                        rawIntent, requestCode, options);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
