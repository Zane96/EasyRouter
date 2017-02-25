package com.example.zane.router.result;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by zane on 2017/1/11.
 * 负责startActivityForResult（）的hook引擎启动
 */

public class ActivityResultEngine{

    private static final String TAG = "ActivityResultEngine";

    /**
     * 适用于startActivityForResult()的两个重写的方法的hook启动
     * @param activity
     */
    public static void startHookFragment(Activity activity, int requestCode, Intent rawIntent, OnActivityResultListener listener){
        HookFragment fragment = getValidFragment((FragmentActivity) activity);
        fragment.startActivityForResult(requestCode, rawIntent, listener);
    }

    private static HookFragment getValidFragment(FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        HookFragment mHookFragment = (HookFragment) manager.findFragmentByTag(TAG);
        boolean isNewInstance = mHookFragment == null;

        if (isNewInstance) {
            mHookFragment = new HookFragment();
            manager.beginTransaction()
                    .add(mHookFragment, TAG)
                    .commit();
            manager.executePendingTransactions();
        }

        return mHookFragment;
    }

}
