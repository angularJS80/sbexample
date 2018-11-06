package com.example.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;


public class DatabaseConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource(DataSourceProperties dataSourceProperties, ApplicationProperties applicationProperties) {
        log.debug("Configuring Datasource");

        HikariConfig config = new HikariConfig();
        DefaultDatabaseConfig databaseConfig = new DefaultDatabaseConfig();
        config.setDataSource(databaseConfig.dataSource());
        /*config.setDataSourceClassName(dataSourceProperties.getDriverClassName());

        config.addDataSourceProperty("url", dataSourceProperties.getUrl());
        if (dataSourceProperties.getUsername() != null) {
            config.addDataSourceProperty("user", dataSourceProperties.getUsername());
        } else {
            config.addDataSourceProperty("user", ""); // HikariCP doesn't allow null user
        }
        if (dataSourceProperties.getPassword() != null) {
            config.addDataSourceProperty("password", dataSourceProperties.getPassword());
        } else {
            config.addDataSourceProperty("password", ""); // HikariCP doesn't allow null password
        }*/
        return new HikariDataSource(config);
    }
    
    
}