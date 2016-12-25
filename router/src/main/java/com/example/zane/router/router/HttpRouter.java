package com.example.zane.router.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class HttpRouter extends BaseRouter{

    @Override
    void startRoute(Context context, String url, Intent rawIntent) {
        rawIntent.setAction(Intent.ACTION_VIEW);
        rawIntent.setData(Uri.parse(url));
        rawIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(rawIntent);
    }
}
