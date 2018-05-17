package com.zane.easyrouter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zane.easyrouter.R;
import com.zane.router.EasyRouter;
import com.zane.router.message.MessageBuilder;
import com.zane.router.result.OnActivityResultListener;

/**
 * Created by zane on 2017/1/15.
 */

public class FragmentTwo extends Fragment{
    private OnResultListener listener;

    public interface OnResultListener {
        void onResult();
    }

    public void setListener(OnResultListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_fragment_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyRouter.routeForResult(getActivity(), new MessageBuilder().setAddress("activity://three")
                                                                 .addParam("data", "data from two", String.class)
                                                                 .build(), new OnActivityResultListener() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        listener.onResult();
                    }
                });
            }
        });
    }
}
