package com.efx.pet.utility;

import com.sun.istack.internal.NotNull;
import org.apache.commons.configuration2.spring.ConfigurationPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class PetEnvPropertyConfiguration {
    private static final Logger log = LoggerFactory.getLogger(PetEnvPropertyConfiguration.class);

    @NotNull
    @Inject
    private Environment env;

    @NotNull
    @Autowired
    private DataSource dataSource;

    @Value("${MACHINE_NAME}")
    @Value("${ENVIRONMENT}")
    @Value("${MACHINE_NAME}")
    @Value("${MACHINE_NAME}")



    @PostConstruct
    public void initializeDatabasePropertySourceUsage() {
        MutablePropertySources propertySources = ((ConfigurableEnvironment) env).getPropertySources();

        try {
//            //CommonsConfigurationFactoryBean comes from https://java.net/projects/springmodules/sources/svn/content/tags/release-0_8/projects/commons/src/java/org/springmodules/commons/configuration/CommonsConfigurationFactoryBean.java?rev=2110
//            //Per https://jira.spring.io/browse/SPR-10213 I chose to just copy the raw source into our project
            CommonsConfigurationFactoryBean commonsConfigurationFactoryBean = new CommonsConfigurationFactoryBean(dbConfig);

            ConfigurationPropertySource configurationPropertySource = new ConfigurationPropertySource();

//            Properties dbProps = (Properties) commonsConfigurationFactoryBean.getObject();
//            PropertiesPropertySource propertySource = new PropertiesPropertySource("propertySource", dbProps);

            Properties props = new Properties();
            props.setProperty("canHasDataSource", dataSource == null ? "No" : "Yes");
            PropertiesPropertySource propertySource = new PropertiesPropertySource("myProps", props);


            //By being First, Database Properties take precedence over all other properties that have the same key name
            //You could put this last, or just in front of the application.properties if you wanted to...
            propertySources.addFirst(propertySource);
        } catch (Exception e) {
            log.error("Error during database properties setup", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Read why this is required: http://www.baeldung.com/2012/02/06/properties-with-spring/#java
     * It is important to be static: http://www.java-allandsundry.com/2013/07/spring-bean-and-propertyplaceholderconf.html
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
