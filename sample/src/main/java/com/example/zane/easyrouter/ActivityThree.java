package com.example.zane.easyrouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.Param;
import com.example.Route;

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
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}
