package com.example;

/**
 * Created by Zane on 2016/12/28.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class Example {

    public void injectData(Activity activity){
        ((xxxActivity) activity).data = activity.getIntent().getString("data");
    }
}
