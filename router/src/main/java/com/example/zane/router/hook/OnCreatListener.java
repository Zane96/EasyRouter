package com.example.zane.router.hook;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by zane on 2017/1/18.
 */

public interface OnCreatListener {
    /**
     * onCreat()调用之前的回调
     * @param activity
     * @param savedInstanceState
     */
    void beforeOnCreat(Activity activity, Bundle savedInstanceState);
}
