package com.example.demo;

import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import com.example.demo.entity.EmploymentPeriod;
import com.example.demo.entity.User;
import jakarta.persistence.PersistenceConfiguration;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.tool.schema.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@org.springframework.context.annotation.Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        return new Configuration()
                .addAnnotatedClass(Agreement.class)
                .addAnnotatedClass(Application.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(EmploymentPeriod.class)
                .setProperty(
                        PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, Action.UPDATE
                )
//               ; .addPackage("classpath:com.example.demo.entity")
                .buildSessionFactory();
    }

}




