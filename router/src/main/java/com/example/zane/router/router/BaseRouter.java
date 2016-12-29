package com.example.zane.router.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public abstract class BaseRouter {

    public static final String ROUTER_URL = "router_url";
    public static final String INJECT_DATA = "inject_data";

    public void route (Context context, String url){
        Intent intent = new Intent();
        intent.putExtra(ROUTER_URL, url);
        intent.putExtra(INJECT_DATA, getTargetClassName(url));
        isAppContext(context, intent);
        startRoute(context, url, intent);
    }

    public void route (Context context, String url, Intent rawIntent){
        Intent intent = new Intent(rawIntent);
        intent.putExtra(ROUTER_URL, url);
        intent.putExtra(INJECT_DATA, getTargetClassName(url));
        isAppContext(context, intent);
        startRoute(context, url, intent);
    }

    /**
     * 从url中获取被启动组件的名字
     * @param url
     * @return
     */
    String getTargetClassName(String url){
        return url.substring(11, url.length());
    }

    private void isAppContext(Context context, Intent intent){
        if (context instanceof Application){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    abstract void startRoute(Context context, String url, Intent rawIntent);
}
