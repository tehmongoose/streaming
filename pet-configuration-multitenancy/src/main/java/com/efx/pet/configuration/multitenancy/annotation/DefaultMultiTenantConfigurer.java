package com.efx.pet.configuration.multitenancy.annotation;

public class DefaultMultiTenantConfigurer implements MultiTenantConfigurer {

    @Override
    public void initialize() {
        System.out.println("DefaultMultiTenantConfigurer initialized");
    }
}
