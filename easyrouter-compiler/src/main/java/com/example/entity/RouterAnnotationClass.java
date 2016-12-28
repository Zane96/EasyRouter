package com.example.entity;

import com.example.Route;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
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

public class RouterAnnotationClass {

    //源码中的类元素
    private TypeElement anntatedClassElement;

    //这个activity的url路由
    private String url;

    //这个类的Class
    private Name className;

    public RouterAnnotationClass(Element anntatedClassElement) {

        this.anntatedClassElement = (TypeElement) anntatedClassElement;

//        List<? extends AnnotationMirror> annotationMirrors = anntatedClassElement.getAnnotationMirrors();
//        for (AnnotationMirror annotationMirror : annotationMirrors){
//            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
//            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
//                Object value = entry.getValue().getValue();
//                url = (String) value;
//                className = this.anntatedClassElement.getSimpleName();
//            }
//        }

        //总结，class
        Route rawRoute = anntatedClassElement.getAnnotation(Route.class);
        //如果是编译了的.class就不会抛出异常
        String rawUrl = rawRoute.value();
        if (judgeUrl(rawUrl)){
            url = rawUrl;
        }
        className = anntatedClassElement.getSimpleName();

        //------------------------------如果格式正确------------------------------
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
