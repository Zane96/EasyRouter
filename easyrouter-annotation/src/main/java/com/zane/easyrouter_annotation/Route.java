package com.zane.easyrouter_annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Zane on 2016/12/23.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Route{
    /**
     * 某个类的路由信息
     * @return
     */
    String value() default "null";
}
