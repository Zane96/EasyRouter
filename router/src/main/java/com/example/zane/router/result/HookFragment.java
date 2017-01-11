package com.example.zane.router.result;

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;

import com.example.zane.router.result.OnActivityResultListener;

import java.util.HashMap;

/**
 * Created by zhuchenxi on 16/11/28.
 */

public class HookFragment extends Fragment {
    static final String TAG = "PortalHookFragment";

    private HashMap<Integer,OnActivityResultListener> mResultMap = new HashMap<>();

    void startActivityForResult(Intent intent, OnActivityResultListener listener){
        int resultCode = listener.hashCode();
        startActivityForResult(intent,resultCode);
        mResultMap.put(resultCode,listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Ferryman","data"+data);
        OnActivityResultListener cur = mResultMap.get(requestCode);
        if (cur!=null){
            cur.onActivityResult(resultCode,data);
            mResultMap.remove(resultCode);
        }
    }
}
