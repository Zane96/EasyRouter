package com.zane.router;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.zane.router.router.ActivityRouter;
import com.zane.router.router.BaseRouter;
import com.zane.router.router.HttpRouter;
import com.zane.router.converter.Converter;
import com.zane.router.converter.GsonConventerFactory;
import com.zane.router.hook.Hooker;
import com.zane.router.message.Message;
import com.zane.router.result.OnActivityResultListener;
import com.zane.router.utils.ZLog;

/**
 *
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class EasyRouter {

    public static void init(Application context){
        init(context, GsonConventerFactory.creat());
        ZLog.setDebug(false);
    }

    /**
     * 全局hook
     */
    public static void init(Application context, Converter.Factory factory){
        Hooker.hookRouter(context);
        EasyRouterSet.setConverterFactory(factory);
    }

    /**
     *
     * @param context
     * @param message
     */
    public static void route(Context context, Message message){
        BaseRouter router = findRouterFromScheme(message);
        router.route(context, message);
    }

    /**
     * 不带参数的startActivityForResult
     * @param context
     * @param message
     */
    public static void routeForResult(Activity context, Message message, OnActivityResultListener listener){
        ActivityRouter router = new ActivityRouter();
        router.startActivityForResult(context, message, listener);
    }

    /**
     * 返回
     * @param activity
     * @param message
     */
    public static void setResult(Activity activity, int resultCode, Message message) {
        ActivityRouter router = new ActivityRouter();
        router.setResult(activity, resultCode, message);
    }

    private static BaseRouter findRouterFromScheme(Message message){
        if (Constant.ACTIVITY.equals(message.getUrl().getScheme())){
            return new ActivityRouter();
        }
        return new HttpRouter();
    }
}
