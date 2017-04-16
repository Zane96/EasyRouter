package com.example.zane.router;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.zane.router.converter.Converter;
import com.example.zane.router.converter.GsonConventerFactory;
import com.example.zane.router.hook.Hooker;
import com.example.zane.router.message.Message;
import com.example.zane.router.result.OnActivityResultListener;
import com.example.zane.router.router.ActivityRouter;
import com.example.zane.router.router.BaseRouter;
import com.example.zane.router.router.HttpRouter;

/**
 *
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class EasyRouter {

    public static void init(Application context){
        init(context, GsonConventerFactory.creat());
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
     * @param requestCode
     */
    public static void routeForResult(Activity context, Message message, int requestCode, OnActivityResultListener listener){
        ActivityRouter router = new ActivityRouter();
        router.startActivityForResult(context, message, requestCode, listener);
    }


    private static BaseRouter findRouterFromScheme(Message message){
        if (Constant.ACTIVITY.equals(message.getUrl().getScheme())){
            return new ActivityRouter();
        }
        return new HttpRouter();
    }
}
