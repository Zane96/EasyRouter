package com.example.entity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

/**
 * Created by Zane on 2016/12/28.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class ParameAnnotationClasses {

    //保持一个Activity名字和ParameAnnotationClass的映射关系
    private Map<String, List<ParameAnnotationClass>> mParameAnnotationMap;

    private ParameAnnotationClasses(){
        mParameAnnotationMap = new HashMap<>();
    }

    private static class SingletonHolder{
        private static final ParameAnnotationClasses instance = new ParameAnnotationClasses();
    }

    public static ParameAnnotationClasses getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * 添加到映射数据的集合里面
     * @param className
     * @param parame
     */
    public void put(String className, ParameAnnotationClass parame){
        List<ParameAnnotationClass> parames = mParameAnnotationMap.get(className);
        if (parames == null){
            parames = new ArrayList<>();
        }
        parames.add(parame);
    }

    /**
     *  获取映射的集合数据
     * @param className
     * @return
     */
    public List<ParameAnnotationClass> get(String className){
        return mParameAnnotationMap.get(className);
    }

    /**
     * 清除
     */
    public void clear(){
        mParameAnnotationMap.clear();
    }

    public void generateCode(Elements elementUtils, Filer filer){

        ClassName activity = ClassName.get("android.app", "Activity");
        MethodSpec injectData = MethodSpec.methodBuilder("injectData")
                .addModifiers(Modifier.PUBLIC);

    }
}
