package com.zane.easyrouter_compiler.process;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @author Saeed Masoumi (saeed@6thsolution.com)
 */
final class Validator {

    static boolean isClass(Element element) {
        return element.getKind() == ElementKind.CLASS;
    }

    static boolean isAbstractClass(Element element) {
        return isClass(element) && getModifiers(element).contains(Modifier.ABSTRACT);
    }

    static boolean isField(Element element) {
        return element.getKind() == ElementKind.FIELD;
    }

    static boolean isNotAbstractClass(Element element) {
        return isClass(element) && !getModifiers(element).contains(Modifier.ABSTRACT);
    }

    static boolean isSubType(Element child, String parentCanonicalName,
                             ProcessingEnvironment procEnv) {
        return procEnv.getTypeUtils()
                       .isSubtype(child.asType(), getTypeElement(procEnv, parentCanonicalName).asType());
    }

    static boolean isPrivate(Element element) {
        return getModifiers(element).contains(Modifier.PRIVATE);
    }

    static boolean isMethod(Element element) {
        return ElementKind.METHOD == element.getKind();
    }

    static TypeElement getTypeElement(ProcessingEnvironment procEnv, String canonicalName) {
        return procEnv.getElementUtils().getTypeElement(canonicalName);
    }

    static Set<Modifier> getModifiers(Element element) {
        return element.getModifiers();
    }

}
