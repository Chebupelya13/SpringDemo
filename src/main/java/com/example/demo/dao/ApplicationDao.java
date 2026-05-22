package com.example.demo.dao;

import com.example.demo.entity.Application;
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
        try {
            return sessionFactory.openSession().get(Application.class, applicationId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Application> getApplicationsByUser(int userId) {
        return sessionFactory.openSession().createQuery(
                "from Application where user.id=:userId", Application.class
                )
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Application> getAllAccepted () {
        return sessionFactory.openSession().createQuery(
                        "from Application where status=:status", Application.class
                )
                .setParameter("status", Application.ApplicationStatus.ACCEPTED)
                .getResultList();
    }

    public List<Application> getAllApplications() {
        return sessionFactory.openSession().createQuery("from Application", Application.class).getResultList();
    }

}
