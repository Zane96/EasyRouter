package com.example.zane.router.result;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by zhuchenxi on 16/11/28.
 *
 * changeby Zane
 */

public class HookFragment extends Fragment {

    private OnActivityResultListener listener;

    void startActivityForResult(int requestCode, Intent intent, OnActivityResultListener listener){
        startActivityForResult(intent, requestCode);
        this.listener = listener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        listener.onActivityResult(resultCode, data);
    }
}
