package com.zane.easyrouter_compiler.entity;

import com.zane.easyrouter_compiler.Constant;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
    private Map<ClassName, List<ParameAnnotationClass>> mResultAnnotationMap;
    private Set<ClassName> allClass;

    private ParameAnnotationGenerator(){
        mParameAnnotationMap = new HashMap<>();
        mResultAnnotationMap = new HashMap<>();
        allClass = new HashSet<>();
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
        allClass.add(className);
        List<ParameAnnotationClass> parames;

        if (parame.isResult()) {
            parames = mResultAnnotationMap.get(className);
        } else {
            parames = mParameAnnotationMap.get(className);
        }

        if (parames == null){
            parames = new ArrayList<>();
        }
        parames.add(parame);

        if (parame.isResult()) {
            mResultAnnotationMap.put(className, parames);
        } else {
            mParameAnnotationMap.put(className, parames);
        }
    }

    /**
     *  获取映射的集合数据
     * @param className
     * @return
     */
    public List<ParameAnnotationClass> getParame(ClassName className){
        return mParameAnnotationMap.get(className);
    }

    public List<ParameAnnotationClass> getResult(ClassName className) {
        return mResultAnnotationMap.get(className);
    }

    /**
     * 清除
     */
    public void clear(){
        mParameAnnotationMap.clear();
        mResultAnnotationMap.clear();
        allClass.clear();
    }

    public void generateCode(Elements elementUtils, Filer filer) throws IOException{
        for (ClassName className : allClass) {
            MethodSpec injectParam = getInjectMethod(mParameAnnotationMap.get(className), className, false);
            MethodSpec injectResult = getInjectMethod(mResultAnnotationMap.get(className), className, true);
            TypeSpec.Builder injectClassBuilder = getClassType(className, injectParam, injectResult);

            String packageName = Constant.GENERATED_PACKAGENAME;
            JavaFile javaFile = JavaFile.builder(packageName, injectClassBuilder.build()).build();
            javaFile.writeTo(filer);
        }
    }

    private TypeSpec.Builder getClassType(ClassName className, MethodSpec injectParam, MethodSpec injectResult) {
        ClassName inject = ClassName.get(Constant.INJECT_PACKAGENAME, "Inject");
        String urlClassName = RouterAnnotationGenerator.getInstance().getUrl(className.toString());
        TypeSpec.Builder injectClassBuilder = TypeSpec.classBuilder(String.format("%s$$Inject", urlClassName))
                                                      .addSuperinterface(inject)
                                                      .addModifiers(Modifier.PUBLIC);

        injectClassBuilder
                .addMethod(injectParam)
                .addMethod(injectResult);
        return injectClassBuilder;
    }

    /**
     * public void injectData(Activity activity){
            ((xxxActivity) activity).data = activity.getIntent().getString("data");
     }
     */
    private MethodSpec getInjectMethod(List<ParameAnnotationClass> parameAnnatationClasses, ClassName className, boolean isResult) {
        ClassName activity = ClassName.get("android.app", "Activity");
        ClassName message = ClassName.get(Constant.MESSAGE_PACKAGE, "Message");
        ClassName baseRouter = ClassName.get(Constant.BASEROUTER_PACKAGE, "BaseRouter");
        ClassName datas = ClassName.get(Map.class);
        ClassName converter = ClassName.get(Constant.COVERTER_PACKAGE, "Converter");
        ClassName easyRouterSet = ClassName.get(Constant.EASYROUTERSET_PACKAGE, "EasyRouterSet");
        ClassName convertException = ClassName.get(Constant.EXCEPTION_PACKAGE, "ConverterExpection");
        ClassName zlog = ClassName.get(Constant.ZLOG_PACKAGE, "ZLog");
        ClassName intent = ClassName.get("android.content", "Intent");


        MethodSpec.Builder injectDataBuilder;

        if (isResult) {
            injectDataBuilder = MethodSpec.methodBuilder("injectResult");
        } else {
            injectDataBuilder = MethodSpec.methodBuilder("injectParam");
        }

        injectDataBuilder.addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(activity, "activity")
                .addParameter(intent, "intent");

        if (parameAnnatationClasses != null) {

            injectDataBuilder.addStatement("$T message = intent.getParcelableExtra($T.ROUTER_MESSAGE)", message, baseRouter)
                    .addStatement("$T.Body body = message.getBody()", message)
                    .addStatement("$T<String, String> datas = body.getDatas()", datas)
                    .addStatement("$T.Factory factory = $T.getConverterFactory()", converter, easyRouterSet)
                    .beginControlFlow("try");

            for (ParameAnnotationClass parameAnnotationClass : parameAnnatationClasses) {
                injectDataBuilder.addStatement("(($T) activity).$N = ($T) factory.decodeConverter($T.class).convert(datas.get($S))",
                        className,
                        parameAnnotationClass.getParameName(),
                        parameAnnotationClass.getTypeName(),
                        parameAnnotationClass.getTypeName(),
                        parameAnnotationClass.getKey());
            }

            injectDataBuilder.endControlFlow()
                    .beginControlFlow("catch ($T e)", convertException)
                    .addStatement("$T.e($S, e.getMessage())", zlog, className.toString() + "$$Inject")
                    .endControlFlow();
        }

        return injectDataBuilder.build();
    }
}
