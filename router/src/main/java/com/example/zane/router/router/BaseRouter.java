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

    // TODO: 2016/12/25  这里用模版，方便后期扩展
    public static final String ROUTER_URL = "router_url";

    public void route (Context context, String url){
        Intent intent = new Intent();
        intent.putExtra(ROUTER_URL, url);
        isAppContext(context, intent);
        startRoute(context, url, intent);
    }

    public void route (Context context, String url, Intent rawIntent){
        Intent intent = new Intent(rawIntent);
        intent.putExtra(ROUTER_URL, url);
        isAppContext(context, intent);
        startRoute(context, url, intent);
    }

    private void isAppContext(Context context, Intent intent){
        if (context instanceof Application){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    abstract void startRoute(Context context, String url, Intent rawIntent);
}
