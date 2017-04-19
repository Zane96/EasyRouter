package com.zane.easyrouter_compiler.entity;

import com.zane.easyrouter_compiler.Constant;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

/**
 * RouterAnntationClass的holder类
 * Created by Zane on 2016/12/20.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class RouterAnnotationGenerator {

    private Map<String, ClassName> routers;
    private List<RouterAnnotationClass> datas;

    private RouterAnnotationGenerator(){
        routers = new HashMap<>();
        datas = new ArrayList<>();
    }

    private static class SingltonHokder{
        public static final RouterAnnotationGenerator instance = new RouterAnnotationGenerator();
    }

    public static RouterAnnotationGenerator getInstance(){return SingltonHokder.instance;}

    /**
     * 添加到map中
     */
    public void put(RouterAnnotationClass data){
        datas.add(data);
        String url = data.getUrl();
        ClassName target = data.getClassName();
        routers.put(url, target);
    }

    /**
     * 在map中查询
     * @param url
     * @return
     */
    public ClassName get(String url){
        return routers.get(url);
    }

    /**
     * 这个方法给参数注入的Holder类持有类给调用
     * 回去到类对应的url，用url去构建注入类。方便开发者自定义url
     * @param className
     * @return
     */
    public String getUrl(String className) {
        Set<Map.Entry<String, ClassName>> routerSet = routers.entrySet();
        Iterator<Map.Entry<String, ClassName>> iterator = routerSet.iterator();
        String url = "";
        while (iterator.hasNext()) {
            Map.Entry<String, ClassName> entry = iterator.next();
            if (entry.getValue().toString().equals(className)) {
                url = getAuthority(entry.getKey());
                break;
            }
        }
        return url;
    }

    /**
     * 清空
     */
    public void clear(){
        routers.clear();
    }

    /**
     * 根据之前收集到的数据信息（String url, Class target）
     * 生成路由表的.java文件代码
     * @param elementUtils
     * @param filer
     * @throws IOException
     */
    public void generateCode(Elements elementUtils, Filer filer) throws IOException{
        if (datas.size() > 0){
            FieldSpec routerTable = getTableField();
            MethodSpec queryTable = getQueryMethod();
            MethodSpec initTable = getInjectMethod();
            MethodSpec constructor = getConstructorMethod(routerTable, initTable);
            TypeSpec routerTableClass = getClassType(routerTable, queryTable, initTable, constructor);

            String packageName = Constant.GENERATED_PACKAGENAME;
            JavaFile javaFile = JavaFile.builder(packageName, routerTableClass).build();
            javaFile.writeTo(filer);
        }
    }

    private TypeSpec getClassType(FieldSpec routerTable, MethodSpec queryTable, MethodSpec initTable, MethodSpec constructor) {
        return TypeSpec.classBuilder("EasyRouterTable")
                                                    .addModifiers(Modifier.PUBLIC)
                                                    .addMethod(constructor)
                                                    .addMethod(initTable)
                                                    .addMethod(queryTable)
                                                    .addField(routerTable)
                                                    .build();
    }

    /**
     * 构造函数
     * private RouterTableExample() {
     routerTable = new HashMap<>();
     initTable();
     }
     */
    private MethodSpec getConstructorMethod(FieldSpec routerTable, MethodSpec initTable) {
        ClassName hashMap = ClassName.get(HashMap.class);
        ClassName activity = ClassName.get("android.app", "Activity");
        return MethodSpec.constructorBuilder()
                                         .addModifiers(Modifier.PUBLIC)
                                         .addStatement("$N = new $T<String, Class<? extends $T>>()", routerTable, hashMap, activity)
                                         .addStatement("$N()", initTable)
                                         .build();
    }

    /**
     * private void initTable(){
     //添加信息到routerTable中
     }
     */
    private MethodSpec getInjectMethod() {
        MethodSpec.Builder initTableBuilder = MethodSpec.methodBuilder("initTable")
                                                      .addModifiers(Modifier.PRIVATE);
        Set<Map.Entry<String, ClassName>> routerSet = routers.entrySet();
        Iterator<Map.Entry<String, ClassName>> iterator = routerSet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ClassName> entry = iterator.next();
            initTableBuilder.addStatement("routerTable.put($S, $T.class)", entry.getKey(), entry.getValue());
        }
        return initTableBuilder.build();
    }

    /**
     *表查询函数
     * public Class queryTable(String url){
     Class<?> target = routerTable.get(url);
     if (target == null){
     return null;
     }
     return target;
     }
     */
    private MethodSpec getQueryMethod() {
        ParameterSpec url = ParameterSpec.builder(String.class, "url").build();
        return MethodSpec.methodBuilder("queryTable")
                                        .addModifiers(Modifier.PUBLIC)
                                        .addParameter(url)
                                        .addStatement("Class<? extends Activity> target = (Class<? extends Activity>) routerTable.get(url)")
                                        .beginControlFlow("if (target == null)")
                                        .addStatement("throw new $T(url)", NoSuchElementException.class)
                                        .endControlFlow()
                                        .addStatement("return target")
                                        .returns(Class.class)
                                        .build();
    }

    private FieldSpec getTableField() {
        return FieldSpec.builder(Map.class, "routerTable")
                                                .addModifiers(Modifier.PRIVATE)
                                                .build();
    }

    /**
     * 获取url的authority
     * @param url
     * @return
     */
    private String getAuthority(String url){
        return url.substring(url.indexOf(":") + 3, url.length());
    }
}