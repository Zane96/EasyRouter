package com.example.entity;

import com.example.Param;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

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
    //数据的类型
    private TypeName typeName;
    //被传递参数的元素
    private VariableElement mParameElement;

    public ParameAnnotationClass(Element mParameElement){
        this.mParameElement = (VariableElement) mParameElement;
        Param parame = mParameElement.getAnnotation(Param.class);
        key = parame.value();
        parameName = this.mParameElement.getSimpleName().toString();
        typeName = convertClass(this.mParameElement.asType().toString());
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

    public TypeName getTypeName() {
        return typeName;
    }

    private static TypeName convertClass(String originClazz) {
        switch (originClazz) {
            case "int":
                return TypeName.INT;
            case "long":
                return TypeName.LONG;
            case "float":
                return TypeName.FLOAT;
            case "double":
                return TypeName.DOUBLE;
            case "short":
                return TypeName.SHORT;
            case "boolean":
                return TypeName.BOOLEAN;
            case "char":
                return TypeName.CHAR;
            default:
                return ClassName.bestGuess(originClazz);
        }
    }
}
