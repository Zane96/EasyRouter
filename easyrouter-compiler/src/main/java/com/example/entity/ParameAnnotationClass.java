package com.example.entity;

import com.example.Param;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

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
    private VariableElement mParameElement;

    public ParameAnnotationClass(Element mParameElement){
        this.mParameElement = (VariableElement) mParameElement;

        Param parame = mParameElement.getAnnotation(Param.class);
        key = parame.value();

        parameName = this.mParameElement.getSimpleName().toString();
    }

    public String getKey() {
        return key;
    }

    public String getParameName() {
        return parameName;
    }

    public VariableElement getmParameElement() {
        return mParameElement;
    }
}
