package com.efx.pet.configuration.multitenancy.annotation;

import com.efx.pet.configuration.multitenancy.context.TenantExecutionContext;
import com.efx.pet.configuration.multitenancy.factory.MultiTenantValueFactory;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.AnnotatedType;

public interface MultiTenantValue<K extends Object> {

    default K getValue() {
        AnnotatedType[] annotatedInterfaces = this.getClass().getAnnotatedInterfaces();
        if (ArrayUtils.isEmpty(annotatedInterfaces)) {
            return null;
        }


        for (AnnotatedType type : annotatedInterfaces) {
            //TODO: probably should read the annotation
            System.out.println("Type: " + type.getType().getClass().toString());
        }

        TenantExecutionContext context = TenantExecutionContext.get();
        return (K) getMultiTenantValueFactory().getObject("poop", context.getTenant(), context.getPartner());
    }

    MultiTenantValueFactory getMultiTenantValueFactory();
}
