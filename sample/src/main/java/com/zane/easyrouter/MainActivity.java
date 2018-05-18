package com.zane.easyrouter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zane.easyrouter.R;
import com.zane.easyrouter_annotation.Result;
import com.zane.easyrouter_annotation.Route;
import com.zane.router.EasyRouter;
import com.zane.router.message.Message;
import com.zane.router.message.MessageBuilder;
import com.zane.router.result.OnActivityResultListener;

@Route("activity://main")
public class MainActivity extends AppCompatActivity {

    @Result("result_three")
    public String resultThree;
    @Result("result_two")
    public String resultTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_start_library_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyRouter.route(MainActivity.this, new MessageBuilder()
                                                            .setAddress("activity://library_acitivity_one")
                                                            .addParam("data", "data from application", String.class)
                                                            .build());
            }
        });

        findViewById(R.id.button_start_activitytwo_foresult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new MessageBuilder()
                                          .setAddress("activity://two")
                                          .addParam("data", "haha", String.class)
                                          .addParam("person", new Person(21, "Zane"), Person.class)
                                          .build();
                EasyRouter.routeForResult(MainActivity.this, message, new OnActivityResultListener() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        Toast.makeText(MainActivity.this, resultTwo, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.button_start_activitythree_foresult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyRouter.routeForResult(MainActivity.this, new MessageBuilder()
                                                                     .setAddress("activity://three")
                                                                     .build(), new OnActivityResultListener() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        Toast.makeText(MainActivity.this, resultThree, Toast.LENGTH_SHORT).show();
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
