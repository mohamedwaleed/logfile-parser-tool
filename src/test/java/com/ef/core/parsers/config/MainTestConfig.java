package com.ef.core.parsers.config;

import com.ef.config.HibernateConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Created by mohamed on 14/10/17.
 */
@ComponentScan
@Import(value = {FlywayConfig.class , HibernateConfig.class, PropertyConfig.class})
@Profile("test")
public class MainTestConfig {

}
