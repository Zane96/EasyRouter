package com.zane.easyrouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.zane.easyrouter.R;
import com.zane.easyrouter_annotation.Param;
import com.zane.easyrouter_annotation.Route;
import com.zane.router.EasyRouter;
import com.zane.router.message.MessageBuilder;

/**
 * Created by Zane on 2016/12/25.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

@Route("activity://three")
public class ActivityThree extends AppCompatActivity{

    @Param("data")
    public String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

        findViewById(R.id.button_return_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyRouter.setResult(ActivityThree.this, 0, new MessageBuilder()
                                                                 .addParam("result_three", "data from three", String.class)
                                                                 .build());
                ActivityThree.this.finish();
            }
        });
    }
}
