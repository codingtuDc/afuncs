# 创建项目

（一）创建项目

在android studio中创建新项目，步骤不累述

（二）添加依赖

在模块的**build.gradle**中添加依赖

```groovy
dependencies {
    ...
    implementation 'com.github.yuanye1818.afuncs:core:1.0.9'
    implementation 'com.github.yuanye1818.afuncs:func4j:1.0.9'
    implementation 'com.github.yuanye1818.afuncs:core_compiler_annotation:1.0.9'
    annotationProcessor 'com.github.yuanye1818.afuncs:core_compiler:1.0.9'
    //Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
    //RxJava
    implementation 'io.reactivex.rxjava2:rxjava:2.2.2'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
    ...
}

```

（三）创建配置文件

创建**Configs**，继承自**CoreConfigs**

```java
public class Configs extends CoreConfigs {
   ...
}
```

