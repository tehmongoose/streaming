package com.efx.pet.configuration.multitenancy.annotation;

import javax.annotation.PostConstruct;

public interface MultiTenantConfigurer {
    //Put the base beans thingies you need to get the multi-tenancy up and going

    @PostConstruct
    void initialize();
}
