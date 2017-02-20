package com.efx.pet.configuration.multitenancy.context;

import org.springframework.core.NamedThreadLocal;

public class TenantExecutionContext {

    private static final NamedThreadLocal<TenantExecutionContext> threadLocal = new NamedThreadLocal<>("Multi_Tenant_Context");


    private String tenant;
    private String partner;

    private TenantExecutionContext(String tenant, String partner) {
        this.tenant = tenant;
        this.partner = partner;
    }


    public static TenantExecutionContext get() {
        return threadLocal.get();

    }

    public static void set(String tenant, String partner) {
        threadLocal.set(new TenantExecutionContext(tenant, partner));
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
