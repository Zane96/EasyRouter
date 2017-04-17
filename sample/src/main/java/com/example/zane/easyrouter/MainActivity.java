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
import com.example.zane.router.utils.ZLog;
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
                Message message = new MessageBuilder()
                                          .setAddress("activity://two")
                                          .addParam("data", "haha", String.class)
                                          .addParam("person", new Person(21, "Zane"), Person.class)
                                          .build();
                EasyRouter.routeForResult(MainActivity.this, message, REQUEST_CODE, new OnActivityResultListener() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        ZLog.i("testResult", data.getStringExtra(ActivityTwo.RETURN_DATA) + " result");
                        Toast.makeText(MainActivity.this, data.getStringExtra(ActivityTwo.RETURN_DATA), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.button_start_activitytwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new MessageBuilder()
                                          .setAddress("activity://two")
                                          .addParam("data", "haha", String.class)
                                          .addParam("person", new Person(21, "Zane"), Person.class)
                                          .build();
                EasyRouter.route(MainActivity.this, message);
            }
        });

        findViewById(R.id.button_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyRouter.route(MainActivity.this, new MessageBuilder()
                                                            .setAddress("http://www.baidu.com")
                                                            .build());
            }
        });
    }
}
