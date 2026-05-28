package com.example.demo.dao;

import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
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
        return sessionFactory.getCurrentSession().createQuery("from Agreement", Agreement.class).getResultList();
    }

    public void addAgreement(User user, Application application) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(new Agreement(application, user));
    }

    public boolean singAgreement(int agreementId) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.createQuery(
                            "update Agreement set status=:status where Id=:agreementId"
                    ).setParameter("status", Agreement.AgreementStatus.SIGNED)
                    .setParameter("agreementId", agreementId).executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public List<Agreement> getUsersAgreements(int userId) {

        return sessionFactory.getCurrentSession().createQuery(
                "from Agreement where application.user.id=:userId", Agreement.class
                )
                .setParameter("userId", userId)
                .getResultList();
    }

    public Agreement getAgreementByApplicationId(int applicationId) {
        return sessionFactory.getCurrentSession().createQuery(
                        "from Agreement where application.id=:applicationId", Agreement.class
                )
                .setParameter("applicationId", applicationId)
                .getSingleResultOrNull();
    }

}
