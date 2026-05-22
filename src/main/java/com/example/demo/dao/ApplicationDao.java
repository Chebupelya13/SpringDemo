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
        sessionFactory.runInTransaction(session -> {
            session.persist(application);
        });
    }

    public Application getApplicationById(int applicationId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Application.class, applicationId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Application> getApplicationsByUser(int userId) {
        Session session = sessionFactory.openSession();

        return session.createQuery(
                "from Application where user.id=:userId", Application.class
                )
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Application> getAllAccepted () {
        Session session = sessionFactory.openSession();

        return session.createQuery(
                        "from Application where status=:status", Application.class
                )
                .setParameter("status", Application.ApplicationStatus.ACCEPTED)
                .getResultList();
    }

    public List<Application> getAllApplications() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Application", Application.class)
                    .getResultList();
        }
    }

}
