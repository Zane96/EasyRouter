package com.example;

import com.example.entity.ParameAnnotationClass;
import com.example.entity.ParameAnnotationGenerator;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * Created by Zane on 2017/1/2.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class ParameAnntationClassesTest {

    @Test
    public void testPut() throws Exception {
        ParameAnnotationGenerator parameAnnotationClasses = Mockito.mock(ParameAnnotationGenerator.class);
        ParameAnnotationClass parameAnnotationClass = Mockito.mock(ParameAnnotationClass.class);

        verify(parameAnnotationClasses).put("ActivityTwo", parameAnnotationClass);
    }
}
