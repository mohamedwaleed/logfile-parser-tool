package com.ef.core.parsers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by mohamed on 15/10/17.
 */
@Configuration
@Profile("test")
public class PropertyConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer sourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return sourcesPlaceholderConfigurer;
    }
}