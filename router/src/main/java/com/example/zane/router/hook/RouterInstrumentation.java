package com.example.zane.router.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.zane.router.router.Router;
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

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity t,
                                            Intent rawIntent, int requestCode, Bundle options){
        try {
            Method execStart = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class, IBinder.class,
                    IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            execStart.setAccessible(true);
            String url = rawIntent.getStringExtra(Router.ROUTER_URL);
            Class target = routerTable.queryTable(url);
            Intent intent = new Intent(who, target);
            intent.putExtras(rawIntent.getExtras());

            return (ActivityResult) execStart.invoke(mBase, who, contextThread, token, t, intent, requestCode, options);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
