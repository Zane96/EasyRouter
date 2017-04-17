package com.example.zane.router.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.example.zane.easyrouter_generated.EasyRouterTable;
import com.example.zane.router.message.Message;
import com.example.zane.router.router.BaseRouter;
import com.example.zane.router.utils.ZLog;

import java.lang.reflect.Method;

/**
 * Created by Zane on 2016/11/28.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class RouterInstrumentation extends Instrumentation {

    private static final String TAG = RouterInstrumentation.class.getSimpleName();

    private Instrumentation mBase;
    private EasyRouterTable routerTable;

    public RouterInstrumentation(Instrumentation mBase) {
        this.mBase = mBase;
        this.routerTable = new EasyRouterTable();
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity t,
                                            Intent rawIntent, int requestCode, Bundle options){

        Message message = rawIntent.getParcelableExtra(BaseRouter.ROUTER_MESSAGE);
        Message.Url url = message.getUrl();
        Method execStart = null;

        try {
            execStart = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class, IBinder.class,
                    IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            execStart.setAccessible(true);
        } catch (NoSuchMethodException e) {
            ZLog.e("RouterInstrumentation", e.getMessage());
        }

        try {
            if ("activity".equals(url.getScheme())){
                Class target = routerTable.queryTable(url.toString());
                Intent intent = new Intent(who, target);
                intent.putExtras(rawIntent.getExtras());
                ZLog.i("RouterInstrumentation", target+"");

                return (ActivityResult) execStart.invoke(mBase, who, contextThread, token, t,
                        intent, requestCode, options);
            } else {
                return (ActivityResult) execStart.invoke(mBase, who, contextThread, token, t,
                        rawIntent, requestCode, options);
            }
        }catch (Exception e) {
            ZLog.e("RouterInstrumentation", e.getMessage());
        }

        return null;
    }
}
