

# EasyRouter

EasyRouter是一个利用URL去进行Activity，Web页面跳转的路由框架。自动注入Activity之间传递的数据，减少了数据传递时Key的定义。使用回调的API去接受Activity返回的数据，更方便的去接受反馈数据。

## 使用

1. 在Activity中自定义URL标识符，目前只支持单一的URL标识，URL的Scheme均为**activity://**

```java
@Route("activity://main")
public class MainActivity extends AppCompatActivity {
}
```

2. 在Application中进行初始化，传入通过APT生成的路由表，在这个过程前先保证项目已经Build成功

```java
EasyRouter.hook(new EasyRouterTable());
```

3. 跳转

   Activity之间无参数传递跳转

```java
EasyRouter.route(MainActivity.this, "activity://two");
```

​	Activity返回数据的回调

```java
EasyRouter.routeForResult(MainActivity.this, "activity://two", intent, REQUEST_CODE, new            	OnActivityResultListener() {
         @Override
         public void onActivityResult(int resultCode, Intent data) {
                  String result = data.getStringExtra(ActivityTwo.RETURN_DATA);
                  Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
         }
 });
```

​	Web页面跳转

```java
EasyRouter.route(MainActivity.this, "http://xzane.cc");
```

​	Activity之间传递参数跳转

```java
@Param("key")
public String data;
```

```java
Intent intent = new Intent();
intent.putExtra("key", "data from main");
EasyRouter.route(MainActivity.this, "activity://two", intent);
```

## 原理

+ 使用动态代理生成**Instrumentation**（ActiivtyThread中）类并通过反射替换，劫持**execStartActivity()**方法达到动态查找路由表并进行跳转的效果
+ 所有的参数注入以及路由表的代码生成均通过APT完成
+ onActivityResult方法的劫持是通过生成Fragment达到的，借鉴了RxPermission

## TODO

+ 支持data，action等数据的添加
+ 统一吊起系统应用的API
+ 支持WebView
+ 返回数据的自动注入