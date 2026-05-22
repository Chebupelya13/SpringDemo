package com.example.demo.dao;

import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgreementDao {

    private final SessionFactory sessionFactory;
    @Autowired
    public AgreementDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Agreement> getAgreements () {
        return sessionFactory.openSession().createQuery("from Agreement", Agreement.class).getResultList();

    }

    public void addAgreement(User user, Application application) {
       sessionFactory.runInTransaction(entityManager -> {
            entityManager.persist(new Agreement(application, user));
        });
    }

    public boolean singAgreement(int agreementId) {
        sessionFactory.runInTransaction(session -> {
            session.createQuery(
                            "update Agreement set status=:status where Id=:agreementId"
                    ).setParameter("status", Agreement.AgreementStatus.SIGNED)
                    .setParameter("agreementId", agreementId).executeUpdate();

        });
        return true;
    }

    public List<Agreement> getUsersAgreements(int userId) {

        return sessionFactory.openSession().createQuery(
                "from Agreement where user.id=:userId", Agreement.class
                )
                .setParameter("userId", userId)
                .getResultList();
    }

    public Agreement getAgreementByApplicationId(int applicationId) {
        return sessionFactory.openSession().createQuery(
                        "from Agreement where application.id=:applicationId", Agreement.class
                )
                .setParameter("applicationId", applicationId)
                .getSingleResultOrNull();
    }

}
