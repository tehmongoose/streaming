package com.efx.pet.configuration.multitenancy.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContextualValue {
    @AliasFor("propertyName")
    String value() default "";

    @AliasFor("value")
    String propertyName() default "";

    boolean ignorePartner() default true;
}
