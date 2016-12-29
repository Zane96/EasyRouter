package com.example.entity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        mParameAnnotationMap.put(className, parames);
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

    public void generateCode(Elements elementUtils, Filer filer) throws IOException{

        Set entrySet = mParameAnnotationMap.entrySet();
        for (Object anEntrySet : entrySet) {
            Map.Entry<String, List<ParameAnnotationClass>> entry = (Map.Entry<String, List<ParameAnnotationClass>>) anEntrySet;
            String className = entry.getKey();
            List<ParameAnnotationClass> parameAnnatationClasses = entry.getValue();

            //构建类
            //Inject接口
            ClassName inject = ClassName.get("com.example.zane.router.hook", "Inject");
            TypeSpec.Builder injectClassBuilder = TypeSpec.classBuilder(String.format("%s$$Inject", className.toLowerCase()))
                                                          .addSuperinterface(inject)
                                                          .addModifiers(Modifier.PUBLIC);

            /**
             * public void injectData(Activity activity){
             ((xxxActivity) activity).data = activity.getIntent().getString("data");
             ....
             }
             */
            ClassName activity = ClassName.get("android.app", "Activity");
            ClassName log = ClassName.get("android.util", "Log");
            MethodSpec.Builder injectDataBuilder = MethodSpec.methodBuilder("injectData")
                                                           .addModifiers(Modifier.PUBLIC)
                                                           .addAnnotation(Override.class)
                                                           .addParameter(activity, "activity");

            for (ParameAnnotationClass parameAnnotationClass : parameAnnatationClasses) {
                injectDataBuilder.addStatement("(($N) activity).$N = activity.getIntent().getStringExtra($S)",
                        className, parameAnnotationClass.getParameName(), parameAnnotationClass.getKey())
                        .addStatement("$T.i($S, (($N) activity).$N + $S)", log, "inject", className, parameAnnotationClass.getParameName(), "");
            }

            //组装
            injectClassBuilder.addMethod(injectDataBuilder.build());

            String packageName = elementUtils.getPackageOf(parameAnnatationClasses.get(0).getmParameElement()).getQualifiedName().toString();
            //开始写入
            JavaFile javaFile = JavaFile.builder(packageName, injectClassBuilder.build())
                                        .build();
            javaFile.writeTo(filer);
        }
    }
}
