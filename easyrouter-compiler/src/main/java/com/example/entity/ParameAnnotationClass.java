package com.example.entity;

import com.example.Parame;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by Zane on 2016/12/28.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class ParameAnnotationClass {

    //key值
    private String key;
    //数据变量名
    private String parameName;
    //被传递参数的元素
    private TypeElement mParameElement;

    public ParameAnnotationClass(Element mParameElement){
        this.mParameElement = (TypeElement) mParameElement;

        Parame parame = mParameElement.getAnnotation(Parame.class);
        key = parame.value();
        // TODO: 2016/12/28 测试两种名字获得的区别
        parameName = this.mParameElement.getSimpleName().toString();
    }

    public String getKey() {
        return key;
    }

    public String getParameName() {
        return parameName;
    }

    public TypeElement getmParameElement() {
        return mParameElement;
    }
}
