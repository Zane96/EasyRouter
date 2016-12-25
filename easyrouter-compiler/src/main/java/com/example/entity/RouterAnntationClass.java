package com.example.entity;

import com.example.Route;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * 储存每一个被Route注解了的Activity的class信息
 * Created by Zane on 2016/12/20.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class RouterAnntationClass {

    //源码中的类元素
    private TypeElement anntatedClassElement;

    //这个activity的url路由
    private String url;

    //这个类的Class
    private Name className;

    public RouterAnntationClass(Element anntatedClassElement) {
        this.anntatedClassElement = (TypeElement) anntatedClassElement;
        //总结，class
        Route rawRoute = anntatedClassElement.getAnnotation(Route.class);

        //------------------------------如果格式正确------------------------------
        //获取url
        try {
            //如果是编译了的.class就不会抛出异常
            String rawUrl = rawRoute.url();
            if (judgeUrl(rawUrl)){
                url = rawUrl;
            }
            className = anntatedClassElement.getSimpleName();
        } catch (MirroredTypeException mte){
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            className = classTypeElement.getSimpleName();

            Route route = classTypeElement.getAnnotation(Route.class);
            String rawUrl = route.url();
            if (judgeUrl(rawUrl)){
                url = rawUrl;
            }
        }
    }

    /**
     * 判断url格式
     * @param url
     * @return
     */
    private boolean judgeUrl(String url){
        if ("activity://".equals(url.substring(0, 11))){
            return true;
        }
        return false;
    }

    public String getUrl() {
        return url;
    }

    public Name getClassName() {
        return className;
    }

    public TypeElement getElement(){
        return anntatedClassElement;
    }
}
