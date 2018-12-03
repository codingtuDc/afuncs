package cn.yuanye1818.autils.compiler.annotation.view;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface FindView {
    int value();
}
