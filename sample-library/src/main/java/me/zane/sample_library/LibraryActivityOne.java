package me.zane.sample_library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zane.easyrouter_annotation.Param;
import com.zane.easyrouter_annotation.Route;

/**
 * Created by Zane on 2018/4/18.
 * Email: zanebot96@gmail.com
 */

@Route("activity://library_acitivity_one")
public class LibraryActivityOne extends AppCompatActivity{
    @Param("data")
    public String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_main);

        TextView mText = findViewById(R.id.text_data);
        mText.setText(data);
    }
}
