package com.zhide.dtsystem.models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExcelTitle {
    String value() default "";

    int rowIndex() default 0;

    int start() default 0;

    int end() default 0;

    boolean merge() default true;
}
