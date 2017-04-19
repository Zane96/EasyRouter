package com.zane.easyrouter_compiler.exception;

/**
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class NoSuchUrlException extends Exception{

    public NoSuchUrlException(String url) {
        super(String.format("No such url in RouterTable's map, %s", url));
    }

}
