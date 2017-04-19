package com.zane.router.exception;

import java.io.IOException;

/**
 * 传递数据的转换异常
 * Created by Zane on 2017/4/16.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class ConverterExpection extends IOException{
    public ConverterExpection(String message) {
        super(message);
    }
}
