package com.zhide.dtsystem.models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
    int rowIndex() default 1;

    int columnIndex() default 0;

    String field() default "";

    String title() default "";

    int width() default 20;

    int height() default 30;

    String dataSource() default "";

    String dataType() default "String";

    String format() default "";
}
