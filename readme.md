

# EasyRouter

欢迎加入Android技术交流群，群号码：577953847

EasyRouter是一个简易的使用字符串进行Activity，Browser跳转的路由框架，并支持组件化开发。

## Features
+ EasyRouter实现了通过字符串进行Activity之间跳转路由，通过APT在编译器实现路由表的构建，劫持了startActivity()进行动态路由
+ EasyRouter实现了Activity之间跳转，返回时的数据自动注入，完全屏蔽了原生的一套繁琐API
+ EasyRouter实现了通过字符串进程Browser的路由跳转
+ EasyRouter支持更换路由跳转时数据序列化的解析器，默认为Gson，可以通过EasyRouterSet进行更换
+ EasyRouter劫持了onActivityResult()，并将其改为接口回调
+ EasyRouter通过transform+ASM实现了一个gradle插件来支持组件化开发

## Usage

1.在Activity中自定义URL标识符，目前只支持单一的URL标识，URL的Scheme均为**activity://**

```java
@Route("activity://main")
public class MainActivity extends AppCompatActivity {
}
```

2.在Application中进行初始化

```java
EasyRouter.init(this);
```

3.跳转

+ Activity之间无参数传递跳转

```java
Message message = new MessageBuilder().setAddress("activity://two").build();
EasyRouter.route(MainActivity.this, message);
```

+ Activity之间有参数传递跳转
```java
Message message = new MessageBuilder()
                        .setAddress("activity://two")
                        .addParam("data", "haha", String.class)
                        .addParam("person", new Person(21, "Zane"), Person.class)
                        .build();
EasyRouter.route(MainActivity.this, message);
```

被启动Activity需要用@Param标记参数变量
```java
@Route("activity://two")
public class ActivityTwo extends AppCompatActivity{

    @Param("data")
    public String data;
    @Param("person")
    public Person person;
}
```
+ Activity需要回调的启动（startActivityForResult()）
```java
Message message = new MessageBuilder()
                               .setAddress("activity://two")
                               .addParam("data", "haha", String.class)
                               .addParam("person", new Person(21, "Zane"), Person.class)
                               .build();
EasyRouter.routeForResult(MainActivity.this, message, new OnActivityResultListener() {
          @Override
          public void onActivityResult(int resultCode, Intent data) {
                //dosomething
          }
});
```

如果有返回的数据，需要通过@Result标记返回参数变量
```java
@Route("activity://main")
public class MainActivity extends AppCompatActivity {

    @Result("result_three")
    public String resultThree;
    @Result("result_two")
    public String resultTwo;
}
```

+ Activity的setResult()
```java
EasyRouter.setResult(ActivityTwo.this, 0, new MessageBuilder()
                                                   .addParam("result_two", "data from two", String.class)
                                                   .build());
finish();
```

+ Web页面跳转

```java
EasyRouter.route(MainActivity.this, new MessageBuilder()
                                             .setAddress("http://xzane.cc")
                                             .build());
```

+ 更改数据序列化的工具

首先需要实现一个序列化工具的工厂类，可以参考框架中的GsonConvertFactory

```java
EasyRouterSet.setConverterFactory(GsonConventerFactory.creat());
```

## Principle

+ 使用动态代理生成**Instrumentation**代理类并通过反射替换ActivityThread中的Instrumentation变量，劫持execStartActivity()方法达到动态查找路由表并进行跳转的效果
+ 所有的参数注入代码以及路由表的代码生成均通过APT在编译期完成
+ onActivityResult方法的劫持是通过生成无View的Fragment达到的，借鉴了RxPermission
+ 通过registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback)去劫持所有Activity的onCreat()方法
+ 编译期通过transform获取编译产物，使用ASM进行AOP操作融合application和library的路由表

## Dependency

+ 在Project build.gradle中
```groovy
allprojects {
 repositories {
    maven { url "https://jitpack.io" }
 }
}
```
+ 在Application build.gradle中
```groovy
dependencies {
    compile 'com.github.Zane96.EasyRouter:router:v1.1.0'
    annotationProcessor 'com.github.Zane96.EasyRouter:easyrouter-compiler:v1.1.0
}
```

+ 如果您希望支持Android组件化开发，那么还需要在Project的build.gradle中添加如下依赖：

```groovy
buildscript {
    dependencies {
        classpath 'me.zane.routermerge:easyrouterMerge:1.0.0'
    }
}
```

并在Application和Library的build.gradle中均添加如下插件：

```groovy
apply plugin: 'com.zane.easyrouterMerge'
```



## TODO

+ ~返回数据，自动注入~
+ ~请求报文实体类封装，Builder类生成URL，头部，数据body~
+ ~支持组件化开发~
+ 跳转时候的参数应该不依赖key的值，要自动或者手动注入
+ 地址做成多个

## License

    Copyright 2017 Zane

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
