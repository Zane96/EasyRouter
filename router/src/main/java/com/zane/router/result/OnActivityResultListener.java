package com.zane.router.result;

import android.content.Intent;

/**
 * Created by zhuchenxi on 2017/1/11.
 */

public interface OnActivityResultListener {

    /**
     * 只需要关心resultCode，因为都是一对一定向启动Activity的
     * @param resultCode
     * @param data
     */
    void onActivityResult(int resultCode, Intent data);
}
