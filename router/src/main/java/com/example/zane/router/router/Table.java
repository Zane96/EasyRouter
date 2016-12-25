package com.example.zane.router.router;

import android.app.Activity;

/**
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public interface Table {
    Class<? extends Activity> queryTable(String url);
}
