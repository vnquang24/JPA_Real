package com.example.jpa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.example.jpa")
@EnableJpaRepositories(basePackages = "com.example.jpa")
@EnableTransactionManagement
public class JpaConfig {
} 