package com.example.entity;

import com.example.Constant;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class ParameAnnotationGenerator {

    //保持一个Activity名字和ParameAnnotationClass的映射关系
    private Map<ClassName, List<ParameAnnotationClass>> mParameAnnotationMap;

    private ParameAnnotationGenerator(){
        mParameAnnotationMap = new HashMap<>();
    }

    private static class SingletonHolder{
        private static final ParameAnnotationGenerator instance = new ParameAnnotationGenerator();
    }

    public static ParameAnnotationGenerator getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * 添加到映射数据的集合里面
     * @param className
     * @param parame
     */
    public void put(ClassName className, ParameAnnotationClass parame){
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
    public List<ParameAnnotationClass> get(ClassName className){
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
            Map.Entry<ClassName, List<ParameAnnotationClass>> entry = (Map.Entry<ClassName, List<ParameAnnotationClass>>) anEntrySet;
            ClassName className = entry.getKey();
            List<ParameAnnotationClass> parameAnnatationClasses = entry.getValue();

            MethodSpec injectMethod = getInjectMethod(parameAnnatationClasses, className);
            TypeSpec.Builder injectClassBuilder = getClassTyoe(className, injectMethod);

            String packageName = Constant.GENERATED_PACKAGENAME;
            JavaFile javaFile = JavaFile.builder(packageName, injectClassBuilder.build()).build();
            javaFile.writeTo(filer);
        }
    }

    private TypeSpec.Builder getClassTyoe(ClassName className, MethodSpec injectMethod) {
        ClassName inject = ClassName.get(Constant.INJECT_PACKAGENAME, "Inject");
        String urlClassName = RouterAnnotationGenerator.getInstance().getUrl(className.toString());
        TypeSpec.Builder injectClassBuilder = TypeSpec.classBuilder(String.format("%s$$Inject", urlClassName))
                                                      .addSuperinterface(inject)
                                                      .addModifiers(Modifier.PUBLIC);
        injectClassBuilder.addMethod(injectMethod);
        return injectClassBuilder;
    }

    /**
     * public void injectData(Activity activity){
            ((xxxActivity) activity).data = activity.getIntent().getString("data");
     }
     */
    private MethodSpec getInjectMethod(List<ParameAnnotationClass> parameAnnatationClasses, ClassName className) {
        ClassName activity = ClassName.get("android.app", "Activity");
        MethodSpec.Builder injectDataBuilder = MethodSpec.methodBuilder("injectData")
                                                       .addModifiers(Modifier.PUBLIC)
                                                       .addAnnotation(Override.class)
                                                       .addParameter(activity, "activity");
        for (ParameAnnotationClass parameAnnotationClass : parameAnnatationClasses) {
            injectDataBuilder.addStatement("(($T) activity).$N = activity.getIntent().getStringExtra($S)",
                    className, parameAnnotationClass.getParameName(), parameAnnotationClass.getKey());
        }

        return injectDataBuilder.build();
    }
}
