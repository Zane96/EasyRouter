package com.example;

import com.example.entity.ParameAnnotationClass;
import com.example.entity.ParameAnnotationClasses;
import com.example.entity.RouterAnnotationClasses;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

import static org.mockito.Mockito.*;

/**
 * Created by Zane on 2017/1/2.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class ParameAnntationClassesTest {

    @Test
    public void testPut() throws Exception {
        ParameAnnotationClasses parameAnnotationClasses = Mockito.mock(ParameAnnotationClasses.class);
        ParameAnnotationClass parameAnnotationClass = Mockito.mock(ParameAnnotationClass.class);

        verify(parameAnnotationClasses).put("ActivityTwo", parameAnnotationClass);
    }
}
