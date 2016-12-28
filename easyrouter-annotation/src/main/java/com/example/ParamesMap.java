package com.example;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Zane on 2016/12/27.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface ParamesMap {
    String[] index();
    Class<?>[] type();
}
