package com.example;

import com.example.entity.RouterAnnotationClass;
import com.example.entity.RouterAnnotationClasses;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Created by Zane on 2016/12/20.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

@AutoService(Processor.class)
public class RouterProcess extends BaseProcess {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv){
        RouterAnnotationClasses.getInstance().clear();

        for (Element anntatedElement : roundEnv.getElementsAnnotatedWith(Route.class)){
            //如果不是类类型的话就要报错
            if (anntatedElement.getKind() != ElementKind.CLASS){
                printError(anntatedElement, "Only classes can be annotated with @Route");
                return true;
            }
            RouterAnnotationClass routerAnntationClass = new RouterAnnotationClass(anntatedElement);
            RouterAnnotationClasses.getInstance().put(routerAnntationClass);
        }
        try {
            RouterAnnotationClasses.getInstance().generateCode(elementUtils, filer);
        } catch (IOException e) {
            System.out.print(e);
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> anntations = new LinkedHashSet<>();
        anntations.add(Route.class.getCanonicalName());
        return anntations;
    }

}
