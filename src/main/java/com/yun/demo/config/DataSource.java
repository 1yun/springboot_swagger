package com.yun.demo.config;

import java.lang.annotation.*;

/**
 * 自定义注解
 * 类 和 方法生效
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DataSourceEnum value() default DataSourceEnum.DB1;
}