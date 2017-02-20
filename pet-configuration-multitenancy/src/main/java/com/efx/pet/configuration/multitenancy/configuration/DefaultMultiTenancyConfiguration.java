package com.efx.pet.configuration.multitenancy.configuration;

import com.efx.pet.configuration.multitenancy.annotation.ContextualValueAnnotationBeanPostProcessor;
import com.efx.pet.configuration.multitenancy.factory.MultiTenantValueFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultMultiTenancyConfiguration {

    @Bean
    protected MultiTenantValueFactory multiTenantValueFactory() {
        //TODO: Figure out what the default should be...
        return null;
    }

    @Bean
    protected ContextualValueAnnotationBeanPostProcessor contextualValueAnnotationBeanPostProcessor(MultiTenantValueFactory factory) {
        return new ContextualValueAnnotationBeanPostProcessor(factory);
    }
}
