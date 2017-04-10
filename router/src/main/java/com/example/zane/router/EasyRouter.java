package com.example.zane.router;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.zane.easyrouter_generated.EasyRouterTable;
import com.example.zane.router.hook.Hooker;
import com.example.zane.router.result.ActivityResultEngine;
import com.example.zane.router.result.OnActivityResultListener;
import com.example.zane.router.router.ActivityRouter;
import com.example.zane.router.router.BaseRouter;
import com.example.zane.router.router.HttpRouter;
import com.example.zane.router.router.Table;

/**
 *
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class EasyRouter {

    //activity路由
    private static final String ACTIVITY = "activity://";
    //网页路由
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";

    /**
     * 全局hook
     */
    public static void hook(Application context){
        Hooker.hookRouter(context);
    }

    private EasyRouter(){
    }

    /**
     * 不传递参数的路由
     * @param context
     * @param url
     */
    public static void route(Context context, String url){
        BaseRouter router = getRouterFromScheme(url);
        router.route(context, url);
    }

    /**
     * 带参数的路由跳转
     * @param context
     * @param url
     * @param rawIntent
     */
    public static void route(Context context, String url, Intent rawIntent){
        BaseRouter router = getRouterFromScheme(url);
        router.route(context, url, rawIntent);
    }

    /**
     * 不带参数的startActivityForResult
     * @param context
     * @param url
     * @param requestCode
     */
    public static void routeForResult(Activity context, String url, int requestCode, OnActivityResultListener listener){
        ActivityRouter router = new ActivityRouter();
        router.startActivityForResult(context, url, requestCode, listener);
    }

    /**
     * 带参数的startActivityForResult
     * @param context
     * @param url
     * @param rawIntent
     * @param requestCode
     */
    public static void routeForResult(Activity context, String url, Intent rawIntent, int requestCode, OnActivityResultListener listener){
        ActivityRouter router = new ActivityRouter();
        router.startActivityForResult(context, url, rawIntent, requestCode, listener);
    }

    private static BaseRouter getRouterFromScheme(String url){
        if (ACTIVITY.equals(url.substring(0, 11))){
            return new ActivityRouter();
        }
        return new HttpRouter();
    }
}
