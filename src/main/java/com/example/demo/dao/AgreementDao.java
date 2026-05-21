package com.example.demo.dao;

import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgreementDao {
    @Autowired
    private final EntityManagerFactory entityManagerFactory;

    public AgreementDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Agreement> getAgreements () {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Agreement> agreements = entityManager.createQuery("from Agreement", Agreement.class).getResultList();
        entityManager.close();

        return agreements;
    }

    public void addAgreement(User user, Application application) {
       entityManagerFactory.runInTransaction(entityManager -> {
            entityManager.persist(new Agreement(application, user));
        });
    }

    public boolean singAgreement(int agreementId) {
        entityManagerFactory.runInTransaction(entityManager -> {
            entityManager.createQuery(
                            "update Agreement set status=:status where Id=:agreementId"
                    ).setParameter("status", Agreement.AgreementStatus.SIGNED)
                    .setParameter("agreementId", agreementId).executeUpdate();

        });
        return true;
    }

    public List<Agreement> getUsersAgreements(int userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Agreement> agreements = entityManager.createQuery(
                "from Agreement where user.id=:userId", Agreement.class
                )
                .setParameter("userId", userId)
                .getResultList();
        entityManager.close();
        return agreements;
    }

    public Agreement getAgreementByApplicationId(int applicationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Agreement agreement = entityManager.createQuery(
                        "from Agreement where application.id=:applicationId", Agreement.class
                )
                .setParameter("applicationId", applicationId)
                .getSingleResultOrNull();
        entityManager.close();

        return agreement;
    }

}
