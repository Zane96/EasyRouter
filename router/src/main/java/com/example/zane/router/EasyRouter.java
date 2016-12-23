package com.example.zane.router;


import com.example.zane.router.hook.Hooker;
import com.example.zane.router.router.BaseRouter;
import com.example.zane.router.router.Table;

/**
 *
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class EasyRouter {

    private BaseRouter baseRouter;

    public static void hook(Table table){
        Hooker.hookRoute(table);
    }
}
