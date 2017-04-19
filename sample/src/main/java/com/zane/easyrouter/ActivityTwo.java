package com.zane.easyrouter;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zane.easyrouter.R;
import com.zane.easyrouter_annotation.Param;
import com.zane.easyrouter_annotation.Result;
import com.zane.easyrouter_annotation.Route;
import com.zane.router.EasyRouter;
import com.zane.router.message.MessageBuilder;

/**
 * Created by Zane on 2016/12/25.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

@Route("activity://two")
public class ActivityTwo extends AppCompatActivity{

    @Param("data")
    public String data;
    @Param("person")
    public Person person;

    @Result("result_three")
    public String result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        TextView mText = (TextView) findViewById(R.id.text_person);
        mText.setText("age: " + person.getAge() + " name: " + person.getName());

        findViewById(R.id.button_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyRouter.setResult(ActivityTwo.this, 0, new MessageBuilder().addParam("result_two", "data from two", String.class)
                                                                            .build());
                ActivityTwo.this.finish();
            }
        });

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FragmentTwo fragmentTwo = new FragmentTwo();
        fragmentTwo.setListener(new FragmentTwo.OnResultListener() {
            @Override
            public void onResult() {
                Toast.makeText(ActivityTwo.this, result, Toast.LENGTH_SHORT).show();
            }
        });
        transaction.add(R.id.fragment_activity_two, fragmentTwo).commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result_two", "data from two");
        setResult(0, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
