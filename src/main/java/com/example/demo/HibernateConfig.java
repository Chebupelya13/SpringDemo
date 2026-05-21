package com.example.demo;

import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.tool.schema.Action;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
    public EntityManagerFactory entityManagerFactory() {
        return new PersistenceConfiguration("Bank")
                .managedClass(User.class)
                .managedClass(Application.class)
                .managedClass(Agreement.class)
                .property(JdbcSettings.URL, databaseUrl)
                .property(JdbcSettings.DRIVER, databaseDriver)
                .property(JdbcSettings.USER, databaseUsername)
                .property(JdbcSettings.PASS, databasePassword)
                .property(JdbcSettings.SHOW_SQL, hibernateShowSql)
                .property(JdbcSettings.DIALECT, hibernateDialect)
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, Action.ACTION_UPDATE)
                .createEntityManagerFactory();
    }

}




