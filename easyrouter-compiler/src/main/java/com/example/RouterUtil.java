package com.example;

/**
 * 分解uri的工具
 * content://com.example.project:200/folder/subfolder/etc
 \---------/  \---------------------------/ \---/ \--------------------------/
 scheme                 host               port        path
              \--------------------------------/
                        authority
 * Created by Zane on 2017/1/1.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class RouterUtil {

    /**
     * 获取url的scheme
     * @param url
     * @return
     */
    public static String getScheme(String url){
        return url.substring(0, url.indexOf(":"));
    }

    /**
     * 获取url的authority
     * @param url
     * @return
     */
    public static String getAuthority(String url){
        return url.substring(url.indexOf(":") + 3, url.length());
    }
}
