package com.efx.pet.configuration.multitenancy.annotation;

import com.efx.pet.configuration.multitenancy.factory.MultiTenantValueFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Modifier;

public class ContextualValueAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    private final Log logger = LogFactory.getLog(getClass());

    private final MultiTenantValueFactory multiTenantFactory;

    public ContextualValueAnnotationBeanPostProcessor(MultiTenantValueFactory multiTenantFactory) {
        this.multiTenantFactory = multiTenantFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(
            PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeanCreationException {
        try {
            ReflectionUtils.doWithLocalFields(bean.getClass(), field -> {
                AnnotationAttributes ann = findAutowiredAnnotation(field);
                if (ann != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        if (logger.isWarnEnabled()) {
                            logger.warn("Autowired annotation is not supported on static fields: " + field);
                        }
                        return;
                    }
                    Object value = new SimpleMultiTenantValue<>(multiTenantFactory);
                    ReflectionUtils.makeAccessible(field);
                    field.set(bean, value);
                }
            });
        } catch (BeanCreationException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of multi-tenant dependencies failed", ex);
        }

        return pvs;
    }

    private AnnotationAttributes findAutowiredAnnotation(AccessibleObject ao) {
        if (ao.getAnnotations().length > 0) {
            AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, ContextualValue.class);
            if (attributes != null) {
                return attributes;
            }
        }
        return null;
    }
}