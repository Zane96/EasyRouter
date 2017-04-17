package com.example.zane.easyrouter;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Param;
import com.example.Route;
import com.example.zane.router.message.Message;
import com.example.zane.router.utils.ZLog;

/**
 * Created by Zane on 2016/12/25.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

@Route("activity://two")
public class ActivityTwo extends AppCompatActivity{

    public static final String RETURN_DATA = "return_code";
    public static final int RESULT_CODE = 2;

    @Param("data")
    public String data;
    @Param("person")
    public Person person;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        //String data = getIntent().getStringExtra(MainActivity.DATA);
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        TextView mText = (TextView) findViewById(R.id.text_person);
        mText.setText("age: " + person.getAge() + " name: " + person.getName());

        findViewById(R.id.button_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(RETURN_DATA, "data from two");
                setResult(RESULT_CODE, intent);
                ActivityTwo.this.finish();
            }
        });

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_activity_two, new FragmentTwo()).commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(RETURN_DATA, "data from two");
        setResult(RESULT_CODE, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
