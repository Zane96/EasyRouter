package com.zane.router.inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zane.easyrouter_annotation.Route;
import com.zane.router.Constant;
import com.zane.router.message.Message;
import com.zane.router.router.BaseRouter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据注入的始作俑者
 * Created by zane on 2017/1/18.
 */

public class InjectMan {

    //数据注入类的缓存
    private static final Map<String, Inject> injectMap = new HashMap<>();


    /**
     * 注入正向参数传递
     * @param activity
     */
    public static void injectParam(Activity activity) {
        Intent intent = activity.getIntent();
        Message message = intent.getParcelableExtra(BaseRouter.ROUTER_MESSAGE);
        if (message != null) {
            if (message.getBody() != null) {
                String className = getTargetClassName(message.getUrl().getBaseUrl());
                String packageName = Constant.GENERATED_PACKAGE;
                Inject inject = getInject(className, packageName);

                if (inject != null) {
                    inject.injectParam(activity, intent);
                }
            }
        }
    }

    /**
     * 注入反向参数传递
     * @param activity
     * @param intent
     */
    public static void injectResult(Activity activity, Intent intent) {
        if (intent != null) {
            Message message = intent.getParcelableExtra(BaseRouter.ROUTER_MESSAGE);
            if (message != null) {
                Route route = activity.getClass().getAnnotation(Route.class);
                String className = getTargetClassName(route.value());
                String packageName = Constant.GENERATED_PACKAGE;
                Inject inject = getInject(className, packageName);

                if (inject != null) {
                    inject.injectResult(activity, intent);
                }
            }
        }
    }

    private static Inject getInject(String className, String packageName) {
        Inject inject = injectMap.get(className);

        if (inject == null) {
            try {
                Class<?> injectClass = Class.forName(String.format("%s.%s$$Inject", packageName, className));
                inject = (Inject) injectClass.newInstance();
                injectMap.put(className, inject);
            } catch (ClassNotFoundException e) {
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
        }

        return inject;
    }

    /**
     * 从url中获取被启动组件的名字
     * @param url
     * @return
     */
    private static String getTargetClassName(String url){
        return url.substring(11, url.length());
    }
}
