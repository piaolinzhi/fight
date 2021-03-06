package com.fighthard.dtodomain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对 domain 类中的字段进行加密. <br />
 * 使用方法为对需要加密的 domain 字段加上@Desensitize注解,现阶段支持String、Long、Integer三种类型的加密。
 * 如需指定转换后的字段名称，则设置 fieldName 属性为对应的字段名称.
 * @author plz
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitize {
    /**
     * 转换后的field名称，如不设置则设置同名的属性.
     * @return 对应的field名称.
     */
    String fieldName() default "";
}
