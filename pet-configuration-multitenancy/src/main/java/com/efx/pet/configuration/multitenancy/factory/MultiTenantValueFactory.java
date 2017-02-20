package com.efx.pet.configuration.multitenancy.factory;

public interface MultiTenantValueFactory {
    Object getObject(String key, String tenant, String partner);
}
