package com.example.demo.dao;

import com.example.demo.entity.Application;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicationDao {

    @Autowired
    private final EntityManagerFactory entityManagerFactory;

    public ApplicationDao(EntityManagerFactory entityManagerFactory) {
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
                "from Application where user.id=:userId", Application.class
                )
                .setParameter("userId", userId)
                .getResultList();
        entityManager.close();

        return applications;
    }

    public List<Application> getAllAccepted () {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Application> applications = entityManager.createQuery(
                        "from Application where status=:status", Application.class
                )
                .setParameter("status", Application.ApplicationStatus.ACCEPTED)
                .getResultList();
        entityManager.close();

        return applications;
    }

    public List<Application> getAllApplications() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Application> applications = entityManager.createQuery("from Application", Application.class)
                .getResultList();
        entityManager.close();
        return applications;

    }

}
