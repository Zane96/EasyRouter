package com.zane.easyrouter;

import android.app.Application;

import com.zane.router.EasyRouter;
import com.zane.router.EasyRouterSet;
import com.zane.router.converter.GsonConventerFactory;


/**
 * Created by Zane on 2016/12/25.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        EasyRouter.init(this);
    }
}
