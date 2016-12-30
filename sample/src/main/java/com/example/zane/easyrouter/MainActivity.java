package com.example.zane.easyrouter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.Route;
import com.example.zane.router.EasyRouter;

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
                Intent intent = new Intent();
                intent.putExtra("data", "data from main");
                EasyRouter.routeForResult(MainActivity.this, "activity://two", intent, REQUEST_CODE);
            }
        });

        findViewById(R.id.button_start_activitytwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyRouter.route(MainActivity.this, "activity://two");
            }
        });

        findViewById(R.id.button_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyRouter.route(MainActivity.this, "http://www.baidu.com");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE:
                String result = data.getStringExtra(ActivityTwo.RETURN_DATA);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
