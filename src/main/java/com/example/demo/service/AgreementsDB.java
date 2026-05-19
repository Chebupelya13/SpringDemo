package com.example.demo.service;

import com.example.demo.model.Agreement;
import com.example.demo.model.Application;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class AgreementsDB {

    private ArrayList<Agreement> agreements = new ArrayList<Agreement>();

    public ArrayList<Agreement> getAgreements() {
        return agreements;
    }

    public void addAgreement(UUID userId, UUID applicationId) {
        Agreement agreement = new Agreement(userId, applicationId);

        agreements.add(agreement);
    }

    public boolean singAgreement(UUID agreementId) {
        for (Agreement agreement : agreements) {
            if ( agreement.getId().equals(agreementId) ) {
                agreement.setStatus(Agreement.AgreementStatus.SIGNED);
                return true;
            }
        }

        return false;
    }

    public ArrayList<Agreement> getUsersAgreements(UUID userId) {
        ArrayList<Agreement> usersAgreements = new ArrayList<Agreement>();

        for (Agreement agreement : agreements) {
            if ( agreement.getUserId().equals(userId) ) {
                usersAgreements.add(agreement);
            }
        }

        return usersAgreements;
    }

    public Agreement getAgreementByApplicationId(UUID applicationId) {
        for (Agreement agreement: agreements) {
            if (agreement.getApplicationId().equals(applicationId)) {
                return agreement;
            }
        }

        return null;
    }

}
