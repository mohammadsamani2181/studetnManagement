package com.studentManagement.config;


import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    @Value("${spring.datasource.url}")
    private String JDBC_URL;

    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER_CLASS_NAME;

    @Value("${spring.datasource.username}")
    private String USERNAME;

    @Value("${spring.datasource.password}")
    private String PASSWORD;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String DDL_AUTO;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String DIALECT;

    @Value("${project.spring.datasource.hikari.maximum-pool-size}")
    private int MAXIMUM_POOL_SIZE;

    @Value("${project.spring.datasource.hikari.maxLifetime}")
    private String MAX_LIFE_TIME;


    private DataSource dataSource;

    @PostConstruct
    private void init() {
        dataSource = createDataSource();
    }

    @Bean("projectDS")
    public DataSource dataSource() {
        return dataSource;
    }


    private DataSource createDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setJdbcUrl(JDBC_URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        dataSource.setPoolName("project_db_pool");
        dataSource.setMaxLifetime(Long.parseLong(MAX_LIFE_TIME));
        dataSource.setMaximumPoolSize(MAXIMUM_POOL_SIZE);

        return dataSource;
    }

    @Bean(name = "projectTransactionManager")
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(oneSessionFactory().getObject());
        transactionManager.setNestedTransactionAllowed(false);
        transactionManager.setDataSource(dataSource());

        return transactionManager;
    }

    @Bean("projectSessionFactory")
    public LocalSessionFactoryBean oneSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                "com.studentManagement.model");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }


    @Bean
    @Primary
    public EntityManagerFactory entityManagerFactory() {
        return oneSessionFactory().getObject();
    }

    private Properties hibernateProperties() {

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", DDL_AUTO);
        hibernateProperties.setProperty(
                "hibernate.dialect", DIALECT);
        hibernateProperties.setProperty(
                "hibernate.hikari.maximumPoolSize", String.valueOf(MAXIMUM_POOL_SIZE));
        hibernateProperties.setProperty(
                "hibernate.hikari.dataSource.maxLifetime", MAX_LIFE_TIME);
        hibernateProperties.setProperty(
                "hibernate.connection.CharSet", "utf8mb4");
        hibernateProperties.setProperty(
                "hibernate.connection.characterEncoding", "utf8mb4");
        hibernateProperties.setProperty(
                "hibernate.connection.useUnicode", "true");

        return hibernateProperties;
    }

}
