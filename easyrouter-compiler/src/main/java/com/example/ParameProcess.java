package com.example;

import com.example.entity.ParameAnnotationClass;
import com.example.entity.ParameAnnotationClasses;
import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * 参数自动注入的APT
 * Created by Zane on 2016/12/28.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

@AutoService(Processor.class)
public class ParameProcess extends BaseProcess{

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element parameElement : roundEnv.getElementsAnnotatedWith(Parame.class)){
            if (parameElement.getKind() != ElementKind.FIELD){
                printError(parameElement, "Only field can be annotated with @Parame");
                return true;
            }
            ParameAnnotationClass parameAnnotationClass = new ParameAnnotationClass(parameElement);
            ParameAnnotationClasses.getInstance().put(getClassName(parameElement), parameAnnotationClass);
        }

        return false;
    }

    //获取Activity或者Fragment的类名
    private String getClassName(Element element){
        //获取class type
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        return classElement.getQualifiedName().toString();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> anntations = new LinkedHashSet<>();
        anntations.add(Parame.class.getCanonicalName());
        return anntations;
    }
}
