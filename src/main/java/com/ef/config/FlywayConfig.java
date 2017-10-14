package com.ef.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by mohamed on 14/10/17.
 */
@Configuration
public class FlywayConfig {

    private static final String MIGRATION_SCRIPTS_SCHEMA_TABLE = "migration_scripts_flyway_meta_schema";
    public static final String CLEAN_MIGRATE = "cleanMigrate";

    @Bean
    public Flyway flywayMigrationBean(DataSource dataSource) {
        Flyway flywayMigrationBean = new Flyway();
        flywayMigrationBean.setLocations("db/migration");
        flywayMigrationBean.setDataSource(dataSource);
        flywayMigrationBean.setTable(MIGRATION_SCRIPTS_SCHEMA_TABLE);
        flywayMigrationBean.setBaselineVersionAsString("0");
        flywayMigrationBean.clean();
        flywayMigrationBean.baseline();
        flywayMigrationBean.migrate();
        return flywayMigrationBean;
    }

    @Bean
    public DataSource dataSource() throws Exception {
        Properties props = new Properties();
        props.setProperty("jdbcUrl", "jdbc:mysql://localhost:3306/parser?createDatabaseIfNotExist=true&autoReconnect=true&useUnicode=true&connectionCollation=utf8_general_ci&characterSetResults=utf8&characterEncoding=utf8");
        props.setProperty("dataSource.user", "root");
        props.setProperty("dataSource.password", "root");

        HikariConfig config = new HikariConfig(props);
        HikariDataSource ds = new HikariDataSource(config);

        return ds;
    }
}
