package com.example.demo.service;

import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationDB {

    @Autowired
    private final EntityManagerFactory entityManagerFactory;

    public ApplicationDB(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void addApplication(Application application) {
        entityManagerFactory.runInTransaction(entityManager -> {
            entityManager.persist(application);
        });
    }

    public Application getApplicationById(int applicationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Application application = entityManager.createQuery("from Application", Application.class)
                .getSingleResultOrNull();
        entityManager.close();
        return application;
    }

    public List<Application> getApplicationsByUser(int userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Application> applications = entityManager.createQuery(
                "from Application where userId=:userId", Application.class
                )
                .setParameter("userId", userId)
                .getResultList();
        entityManager.close();

        return applications;
    }

    public List<Application> getAllAccepted () {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Application> applications = entityManager.createQuery(
                        "from Application where a.status=:status", Application.class
                )
                .setParameter("status", Application.ApplicationStatus.ACCEPTED)
                .getResultList();
        entityManager.close();

        return applications;
    }

    public List<Application> getApplications() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Application> applications = entityManager.createQuery("from Application", Application.class)
                .getResultList();
        entityManager.close();
        return applications;

    }

}
