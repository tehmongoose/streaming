package com.efx.pet.configuration.multitenancy;

import com.efx.pet.configuration.multitenancy.annotation.ContextualValue;
import com.efx.pet.configuration.multitenancy.annotation.EnableMultiTenancy;
import com.efx.pet.configuration.multitenancy.annotation.MultiTenantValue;
import com.efx.pet.configuration.multitenancy.configuration.DefaultMultiTenancyConfiguration;
import com.efx.pet.configuration.multitenancy.context.TenantExecutionContext;
import com.efx.pet.configuration.multitenancy.factory.MultiTenantValueFactory;
import com.efx.pet.configuration.multitenancy.factory.TestMultiTenantValueFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


@ContextConfiguration(classes = {SimpleTest.Config.class})
@RunWith(SpringRunner.class)
public class SimpleTest {

    @Configuration
    @EnableMultiTenancy
    public static class Config extends DefaultMultiTenancyConfiguration {
        @Bean
        @Override
        public MultiTenantValueFactory multiTenantValueFactory() {
            return new TestMultiTenantValueFactory();
        }
    }

    @ContextualValue("com.efx.pet.passphrase")
    MultiTenantValue<String> passphrase;

    @Test
    public void test() throws IOException {
        TenantExecutionContext.set("A", "b");
        String value = passphrase.getValue();
        System.out.println(value);
        assertEquals(TestMultiTenantValueFactory.phrase1, value);

        TenantExecutionContext.set("c", "d");
        value = passphrase.getValue();
        System.out.println(value);
        assertEquals(TestMultiTenantValueFactory.phrase2, value);
    }
}
