package com.example.zane.easyrouter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zane.router.EasyRouter;
import com.example.zane.router.message.MessageBuilder;

/**
 * Created by zane on 2017/1/15.
 */

public class FragmentTwo extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.text_fragment_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyRouter.route(getActivity(), new MessageBuilder().setAddress("activity://three")
                                                        .addParam("data", "data from two", String.class)
                                                        .build());
            }
        });
    }
}
