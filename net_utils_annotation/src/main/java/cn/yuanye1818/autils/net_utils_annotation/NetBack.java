package cn.yuanye1818.autils.net_utils_annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.zip.Deflater;

@Retention(RetentionPolicy.SOURCE)
public @interface NetBack {

    Class value() default Void.class;

}
