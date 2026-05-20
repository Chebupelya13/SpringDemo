package com.example.demo;

import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.tool.schema.Action;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:database.properties")
public class HibernateConfig {
    @Value("${db.driver}")
    private String databaseDriver;
    @Value("${db.url}")
    private String databaseUrl;
    @Value("${db.username}")
    private String databaseUsername;
    @Value("${db.password}")
    private String databasePassword;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);
        return dataSource;
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
//        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactory.setDataSource(dataSource);
//        entityManagerFactory.setPackagesToScan("com.exaple.demo.entity");
//        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        entityManagerFactory.setJpaProperties(getHibernateProperties());
//        return entityManagerFactory;
//    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        System.out.println(hibernateDialect);
        return new PersistenceConfiguration("Bank")
                .managedClass(User.class)
                .managedClass(Application.class)
                .managedClass(Agreement.class)
                .property(PersistenceConfiguration.JDBC_DATASOURCE, dataSource())
                .property(JdbcSettings.SHOW_SQL, hibernateShowSql)
                .property(JdbcSettings.DIALECT, hibernateDialect)
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION,
                        Action.SPEC_ACTION_DROP_AND_CREATE)
                .createEntityManagerFactory();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hinernate.show_sql", hibernateShowSql);
        return properties;
    }

}




