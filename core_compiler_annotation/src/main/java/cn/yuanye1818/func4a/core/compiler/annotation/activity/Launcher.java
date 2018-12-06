package cn.yuanye1818.func4a.core.compiler.annotation.activity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Launcher {

    Class[] paramClasses() default {};

    String[] paramNames() default {};

}
