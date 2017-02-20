package com.efx.pet.configuration.multitenancy.annotation;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableMultiTenancy {
    Class<? extends MultiTenantConfigurer> factoryConfigurer() default MultiTenantConfigurer.class;

    boolean enableCaching() default false;
}
