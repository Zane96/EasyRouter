package com.example.zane.router.result;

import android.app.Activity;

/**
 * Created by zane on 2017/1/11.
 * 负责startActivityForResult（）的hook引擎启动
 */

public class ActivityResultEngine{

    private static final String TAG = "ActivityResultEngine";
    private static HookFragment mHookFragment;

    static {
        mHookFragment= new HookFragment();
    }

    /**
     * 适用于startActivityForResult()的两个重写的方法的hook启动
     * @param activity
     */
    public static void startHookFragment(Activity activity, OnActivityResultListener listener){
        android.app.FragmentManager manager = activity.getFragmentManager();
        HookFragment hookFragment = (HookFragment) manager.findFragmentByTag(TAG);
        boolean isNewInstance = hookFragment == null;

        if (isNewInstance) {
            manager.beginTransaction()
                    .add(mHookFragment, TAG)
                    .commit();
            manager.executePendingTransactions();
        }
    }

}
