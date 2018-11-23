package cn.yuanye1818.autils.compiler.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface BaseUrl {
    String value();
}
