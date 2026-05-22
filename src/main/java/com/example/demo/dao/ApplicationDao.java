package com.example.demo.dao;

import com.example.demo.entity.Application;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicationDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public ApplicationDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addApplication(Application application) {
        sessionFactory.runInTransaction(entityManager -> {
            entityManager.persist(application);
        });
    }

    public Application getApplicationById(int applicationId) {
        try (Session session = sessionFactory.openSession()) {
            Application application = session.get(Application.class, applicationId);

            return application;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Application> getApplicationsByUser(int userId) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        List<Application> applications = entityManager.createQuery(
                "from Application where user.id=:userId", Application.class
                )
                .setParameter("userId", userId)
                .getResultList();
        entityManager.close();

        return applications;
    }

    public List<Application> getAllAccepted () {
        EntityManager entityManager = sessionFactory.createEntityManager();
        List<Application> applications = entityManager.createQuery(
                        "from Application where status=:status", Application.class
                )
                .setParameter("status", Application.ApplicationStatus.ACCEPTED)
                .getResultList();
        entityManager.close();

        return applications;
    }

    public List<Application> getAllApplications() {
        try (Session session = sessionFactory.openSession()) {
            List<Application> applications = session.createQuery("from Application", Application.class)
                    .getResultList();
            return applications;
        }
    }

}
