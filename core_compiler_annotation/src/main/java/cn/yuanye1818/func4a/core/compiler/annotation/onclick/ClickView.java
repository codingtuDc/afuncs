package cn.yuanye1818.func4a.core.compiler.annotation.onclick;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ClickView {
    int[] value();
}
