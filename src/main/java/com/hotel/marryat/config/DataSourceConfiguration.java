package com.hotel.marryat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource({ "classpath:db-config.properties" })
@EnableJpaRepositories(
        basePackages = "com.hotel.marryat.repository"
)
@EnableTransactionManagement
public class DataSourceConfiguration {
}