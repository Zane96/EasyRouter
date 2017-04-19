package com.zane.easyrouter_compiler.process;

import com.zane.easyrouter_annotation.Param;
import com.zane.easyrouter_annotation.Result;
import com.zane.easyrouter_compiler.entity.ParameAnnotationClass;
import com.zane.easyrouter_compiler.entity.ParameAnnotationGenerator;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * 参数数据自动注入的APT
 * Created by Zane on 2016/12/28.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

@AutoService(Processor.class)
public class zParameProcess extends BaseProcess {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        ParameAnnotationGenerator.getInstance().clear();

        for (Element parameElement : roundEnv.getElementsAnnotatedWith(Param.class)){
            if (!judgeKind(parameElement)) {
                generatedFiled(parameElement, false);
            }
        }

        for (Element resultElement : roundEnv.getElementsAnnotatedWith(Result.class)) {
            if (!judgeKind(resultElement)) {
                generatedFiled(resultElement, true);
            }
        }

        try {
            ParameAnnotationGenerator.getInstance().generateCode(elementUtils, filer);
        } catch (IOException e) {
        }

        return true;
    }

    private void generatedFiled(Element element, boolean isResult) {
        ParameAnnotationClass parameAnnotationClass = new ParameAnnotationClass(element, isResult);
        ParameAnnotationGenerator.getInstance().put(getClassName(element), parameAnnotationClass);
    }

    private boolean judgeKind(Element element) {
        if (!Validator.isField(element)){
            printError(element, "Only field can be annotated with @Param");
            return true;
        } else if (Validator.isPrivate(element)) {
            printError(element, "Field can't be the private with @Param");
            return true;
        }
        return false;
    }

    //获取Activity或者Fragment的类名
    private ClassName getClassName(Element element){
        //获取class type
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        return ClassName.bestGuess(classElement.getQualifiedName().toString());
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> anntations = new LinkedHashSet<>();
        anntations.add(Param.class.getCanonicalName());
        anntations.add(Result.class.getCanonicalName());
        return anntations;
    }
}
