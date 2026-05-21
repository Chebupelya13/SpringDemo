package com.example.demo.service;

import com.example.demo.entity.Agreement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgreementsDB {
    @Autowired
    private final EntityManagerFactory entityManagerFactory;

    public AgreementsDB(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Agreement> getAgreements () {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Agreement> agreements = entityManager.createQuery("from Agreement", Agreement.class).getResultList();
        entityManager.close();

        return agreements;
    }

    public void addAgreement(int userId, int applicationId) {
       entityManagerFactory.runInTransaction(entityManager -> {
            entityManager.persist(new Agreement(userId, applicationId));
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
                "from Agreement where userId=:userId", Agreement.class
                )
                .setParameter("userId", userId)
                .getResultList();
        entityManager.close();
        return agreements;
    }

    public Agreement getAgreementByApplicationId(int applicationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Agreement agreement = entityManager.createQuery(
                        "from Agreement where applicationId=:applicationId", Agreement.class
                )
                .setParameter("applicationId", applicationId)
                .getSingleResultOrNull();
        entityManager.close();

        return agreement;
    }

}
