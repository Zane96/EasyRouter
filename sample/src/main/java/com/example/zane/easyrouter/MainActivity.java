package com.example.zane.easyrouter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.Route;
import com.example.zane.router.EasyRouter;
import com.example.zane.router.message.Message;
import com.example.zane.router.message.MessageBuilder;
import com.example.zane.router.result.OnActivityResultListener;
import com.google.gson.Gson;

@Route("activity://main")
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_start_activitytwo_foresult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.button_start_activitytwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new MessageBuilder()
                                          .setAddress("activity://two")
                                          .addParam("data", "haha", String.class)
                                          .addParam("num", 1, Integer.class)
                                          .addHeader("action", "asdaas")
                                          .build();
                EasyRouter.route(MainActivity.this, message);
            }
        });

        findViewById(R.id.button_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
