package com.example.demo.service;

import com.example.demo.dao.AgreementDao;
import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AgreementService {

    private final AgreementDao agreementDao;
    private final UserDao userDao;
    private final ApplicationDao applicationDao;

    @Autowired
    public AgreementService(AgreementDao agreementDao, UserDao userDao, ApplicationDao applicationDao) {
        this.agreementDao = agreementDao;
        this.userDao = userDao;
        this.applicationDao = applicationDao;
    }

    public List<Agreement> getAgreements() {
        return agreementDao.getAgreements();
    }

    public List<Agreement> getUsersAgreements(int userId) {
        return agreementDao.getUsersAgreements(userId);
    }

    public Agreement getAgreementByApplicationId(int applicationId) {
        return agreementDao.getAgreementByApplicationId(applicationId);
    }

    public boolean signAgreement(int agreementId) {
        try {
            agreementDao.singAgreement(agreementId);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void addAgreement(int applicationId) {
        Application application = applicationDao.getApplicationById(applicationId);

        agreementDao.addAgreement(application.getUser(), application);
    }

}
