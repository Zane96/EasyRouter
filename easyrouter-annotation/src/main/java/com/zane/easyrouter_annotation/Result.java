package com.zane.easyrouter_annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用这个注解标记跳转的返回对象
 *
 * Created by Zane on 2017/4/17.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Result {
    String value();
}
