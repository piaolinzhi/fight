package com.fighthard.dtodomain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitize {
    public String fieldName() default "fieldName";
    public String key() default "";
    public String algorithmName() default "DES";
}
