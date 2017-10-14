package com.ef.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by mohamed on 14/10/17.
 */
@ComponentScan
@Import(value = {FlywayConfig.class , HibernateConfig.class})
public class MainConfig {

}
