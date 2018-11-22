package cn.yuanye1818.autils.net_utils_annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface NetBack {
    String code();
}
