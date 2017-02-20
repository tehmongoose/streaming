package com.efx.pet.configuration.multitenancy.factory;

public class TestMultiTenantValueFactory implements MultiTenantValueFactory {

    public static final String phrase1 = "LSKDJFLSKDJFSLKDJ=";
    public static final String phrase2 = "SDLdkjslSLdjsdf=";

    @Override
    public Object getObject(String key, String tenant, String partner) {
        return tenant.equals("A") ? phrase1 : phrase2;
    }
}
