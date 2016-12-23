package com.example.zane.router.router;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Zane on 2016/12/9.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public final class Router {

    public static final String ROUTER_URL = "router_url";

    public static void startActivity(Context context, String url, Intent rawIntent){
        Intent intent = new Intent(rawIntent);
        if (context instanceof Application){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(ROUTER_URL, url);
        context.startActivity(intent);
    }
}
