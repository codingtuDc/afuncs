package cn.yuanye1818.autils.compiler.annotation.onclick;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ClickTag {
    int value();
}
