package cn.yuanye1818.autils.compiler.annotation.onactivityresult;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface OnResult {
    int value();
}
