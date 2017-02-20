package com.efx.pet.configuration.multitenancy.annotation;


import com.efx.pet.configuration.multitenancy.factory.MultiTenantValueFactory;

public class SimpleMultiTenantValue<K> implements MultiTenantValue<K> {

    private final MultiTenantValueFactory factory;

    public SimpleMultiTenantValue(MultiTenantValueFactory factory) {
        this.factory = factory;
    }

    @Override
    public MultiTenantValueFactory getMultiTenantValueFactory() {
        return factory;
    }
}
