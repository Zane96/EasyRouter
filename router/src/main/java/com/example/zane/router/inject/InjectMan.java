package com.example.zane.router.inject;

import android.app.Activity;
import android.os.Bundle;

import com.example.zane.router.router.BaseRouter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据注入的始作俑者
 * Created by zane on 2017/1/18.
 */

public class InjectMan {

    //数据注入类的缓存
    private final Map<String, Inject> injectMap;

    public InjectMan() {
        injectMap = new HashMap<>();
    }

    /**
     * 注入数据的逻辑
     * @param activity
     * @param savedInstanceState
     */
    public void inject(Activity activity, Bundle savedInstanceState) {
        //开始进行传递数据的注入
        String className = "";
        String packageName = "com.example.zane.easyrouter_generated";

        Inject inject = injectMap.get(className);
        boolean isClassFound = true;
        if (inject == null) {
            try {
                Class<?> injectClass = Class.forName(String.format("%s.%s$$Inject", packageName, className));
                inject = (Inject) injectClass.newInstance();
                injectMap.put(className, inject);
            } catch (ClassNotFoundException e) {
                isClassFound = false;
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
        }

        if (inject != null){
            inject.injectData(activity);
        }
    }

}
