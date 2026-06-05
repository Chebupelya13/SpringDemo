package com.example.demo.dao;

import com.example.demo.entity.Application;
import com.example.demo.enums.ApplicationStatus;
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
        Session session = sessionFactory.getCurrentSession();
        session.persist(application);
    }

    public Application getApplicationById(int applicationId) {
        try {
            return sessionFactory.getCurrentSession().get(Application.class, applicationId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Application> getApplicationsByUser(int userId) {
        return sessionFactory.getCurrentSession().createQuery(
                "from Application where user.id=:userId", Application.class
                )
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Application> getAllAccepted () {
        return sessionFactory.getCurrentSession().createQuery(
                        "from Application where status=:status", Application.class
                )
                .setParameter("status", ApplicationStatus.ACCEPTED)
                .getResultList();
    }

    public List<Application> getAllApplications(int limit, int offset) {
        return sessionFactory.getCurrentSession().createQuery("from Application", Application.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

}
