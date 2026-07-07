package com.example.databasework.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {


    @Primary
    @Bean(name = "appDataSource")
    public DataSource appDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean(name = "postgresDataSource")
    public DataSource postgresDataSource() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("BankBogdan");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/bank_db");
        config.setUsername("postgres");
        config.setPassword("28085678");
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        return new HikariDataSource(config);
    }
}