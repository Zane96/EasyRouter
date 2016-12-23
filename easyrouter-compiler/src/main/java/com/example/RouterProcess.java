package com.example;

import com.example.entity.RouterAnntationClass;
import com.example.entity.RouterAnntationClasses;
import com.example.exception.OnlyClassException;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by Zane on 2016/12/20.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

@AutoService(Processor.class)
public class RouterProcess extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    private Map<String, Class> factoryClasses = new LinkedHashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv){
        System.out.print("start");
        RouterAnntationClasses.getInstance().clear();

        for (Element anntatedElement : roundEnv.getElementsAnnotatedWith(Route.class)){
            //如果不是类类型的话就要报错
            if (anntatedElement.getKind() != ElementKind.CLASS){
                printError(anntatedElement, "Only classes can be annotated with @Route");
                //退出process
                //throw new OnlyClassException(anntatedElement.getKind().name());
                return true;
            }
            RouterAnntationClass routerAnntationClass = new RouterAnntationClass(anntatedElement);
            RouterAnntationClasses.getInstance().put(routerAnntationClass);

            // TODO: 2016/12/23 类型判断 
        }
        try {
            RouterAnntationClasses.getInstance().generateCode(elementUtils, filer);
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

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void printError(Element e, String msg, Object... args){
        messager.printMessage(Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }
}
