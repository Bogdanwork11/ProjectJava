package com.example.databasework.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${postgres.datasource.pool-name}")
    private String poolName;

    @Value("${postgres.datasource.url}")
    private String url;

    @Value("${postgres.datasource.username}")
    private String username;

    @Value("${postgres.datasource.password}")
    private String password;

    @Value("${postgres.datasource.connection-timeout}")
    private long connectionTimeout;

    @Value("${postgres.datasource.idle-timeout}")
    private long idleTimeout;

    @Value("${postgres.datasource.maximum-pool-size}")
    private int maximumPoolSize;

    @Value("${postgres.datasource.minimum-idle}")
    private int minimumIdle;

    @Primary
    @Bean(name = "appDataSource")
    public DataSource appDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "postgresDataSource")
    public DataSource postgresDataSource() {
        HikariConfig config = new HikariConfig();

        config.setPoolName(poolName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setMinimumIdle(minimumIdle);

        return new HikariDataSource(config);
    }
}