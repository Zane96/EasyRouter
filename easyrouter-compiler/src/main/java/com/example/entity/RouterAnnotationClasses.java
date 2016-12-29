package com.example.entity;

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
import javax.lang.model.element.Name;
import javax.lang.model.util.Elements;

/**
 * RouterAnntationClass的holder类
 * Created by Zane on 2016/12/20.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class RouterAnnotationClasses {

    private Map<String, Name> routers;
    private List<RouterAnnotationClass> datas;

    private RouterAnnotationClasses(){
        routers = new HashMap<>();
        datas = new ArrayList<>();
    }

    private static class SingltonHokder{
        public static final RouterAnnotationClasses instance = new RouterAnnotationClasses();
    }

    public static RouterAnnotationClasses getInstance(){return SingltonHokder.instance;}

    /**
     * 添加到map中
     */
    public void put(RouterAnnotationClass data){

        datas.add(data);

        String url = data.getUrl();
        Name target = data.getClassName();
        if (routers.get(url) != null){
            // TODO: 2016/12/20  抛异常
        }
        routers.put(url, target);
    }

    /**
     * 在map中查询
     * @param url
     * @return
     */
    public Name get(String url){
        return routers.get(url);
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
            //private Map<String, Class<?>> routerTable;
            FieldSpec routerTable = FieldSpec.builder(Map.class, "routerTable")
                                            .addModifiers(Modifier.PRIVATE)
                                            .build();

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
            ParameterSpec url = ParameterSpec.builder(String.class, "url").build();
            MethodSpec queryTable = MethodSpec.methodBuilder("queryTable")
                                            .addAnnotation(Override.class)
                                            .addModifiers(Modifier.PUBLIC)
                                            .addParameter(url)
                                            .addStatement("Class<? extends Activity> target = (Class<? extends Activity>) routerTable.get(url)")
                                            .beginControlFlow("if (target == null)")
                                            .addStatement("throw new $T(url)", NoSuchElementException.class)
                                            .endControlFlow()
                                            .addStatement("return target")
                                            .returns(Class.class)
                                            .build();

            /**
             * private void initTable(){
             //添加信息到routerTable中
             }
             */
            MethodSpec.Builder initTableBuilder = MethodSpec.methodBuilder("initTable")
                                                          .addModifiers(Modifier.PRIVATE);
            Set<Map.Entry<String, Name>> routerSet = routers.entrySet();
            Iterator<Map.Entry<String, Name>> iterator = routerSet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Name> entry = iterator.next();
                initTableBuilder.addStatement("routerTable.put($S, $N.class)", entry.getKey(), entry.getValue());
            }
            MethodSpec initTable = initTableBuilder.build();

            /**
             * 构造函数
             * private RouterTableExample() {
             routerTable = new HashMap<>();
             initTable();
             }
             */
            ClassName hashMap = ClassName.get(HashMap.class);
            ClassName activity = ClassName.get("android.app", "Activity");
            MethodSpec constructor = MethodSpec.constructorBuilder()
                                             .addModifiers(Modifier.PUBLIC)
                                             .addStatement("$N = new $T<String, Class<? extends $T>>()", routerTable, hashMap, activity)
                                             .addStatement("$N()", initTable)
                                             .build();

//            /**
//             * 内部静态holder类
//             * private static final class SingletonHolder{
//             private static final RouterTableExample instance = new RouterTableExample();
//             }
//             */
//            ClassName instanceClass = ClassName.get("com.example.zane.testhook", "RouterTable");
//            CodeBlock initBlock = CodeBlock.builder()
//                                          .addStatement("instance = new $T()", instanceClass)
//                                          .build();
//            TypeSpec singletonHolder = TypeSpec.classBuilder("SingletonHolder")
//                                               .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
//                                               .addField(instanceClass, "instance", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
//                                               .addStaticBlock(initBlock)
//                                               .build();
//
//            /**
//             * public static RouterTableExample getInstacne(){
//             return SingletonHolder.instance;
//             }
//             */
//            MethodSpec getInstance = MethodSpec.methodBuilder("getInstance")
//                                             .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
//                                             .addStatement("return $N.instance", singletonHolder)
//                                             .returns(instanceClass)
//                                             .build();

            //Table接口
            ClassName table = ClassName.get("com.example.zane.router.router", "Table");
            //开始组装类
            TypeSpec routerTableClass = TypeSpec.classBuilder("EasyRouterTable")
                                                .addModifiers(Modifier.PUBLIC)
                                                .addMethod(constructor)
                                                .addMethod(initTable)
                                                .addMethod(queryTable)
                                                //.addMethod(getInstance)
                                                //.addType(singletonHolder)
                                                .addField(routerTable)
                                                .addSuperinterface(table)
                                                .build();

            String packageName = elementUtils.getPackageOf(datas.get(0).getElement()).getQualifiedName().toString();

            //开始写入
            JavaFile javaFile = JavaFile.builder(packageName, routerTableClass)
                                        .build();

            javaFile.writeTo(filer);
        }
    }
}