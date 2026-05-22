package com.example.demo.controller;

import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.dao.AgreementDao;
import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.UserDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agreement")
@Tag(name = "Договоры", description = "Операции с кредитными договорами")
public class AgreementController {

    private final AgreementDao agreementDao;
    private final UserDao userDao;
    private final ApplicationDao applicationsDB;

    @Autowired
    public AgreementController(AgreementDao agreementDao, UserDao userDao, ApplicationDao applicationsDB) {
        this.applicationsDB = applicationsDB;
        this.agreementDao = agreementDao;
        this.userDao = userDao;
    }

    @GetMapping
    @Operation(summary = "Получение списка всех договоров")
    public ResponseEntity<List<Agreement>> getAllAgreements() {
        List<Agreement> agreements = agreementDao.getAgreements();

        return agreements.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(agreements);
    }

    @PostMapping
    @Operation(summary = "Создание нового договора")
    public HttpStatus createAgreement(
            @RequestParam int userId,
            @RequestParam int applicationId
    ) {
        try {
            Application application = applicationsDB.getApplicationById(applicationId);
            User user = userDao.getUserById(userId);
            agreementDao.addAgreement(user, application);

            return HttpStatus.CREATED;

        } catch (Exception e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    @GetMapping("/findByUser")
    @Operation(summary = "Получение договоров пользователя")
    public ResponseEntity<List<Agreement>> getUsersAgreements( @RequestParam int userId ) {
        try {
            List<Agreement> usersAgreements = agreementDao.getUsersAgreements(userId);
            return usersAgreements.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(usersAgreements);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/sign")
    @Operation(summary = "Изменение статуса договора на 'подписано'")
    public HttpStatus signAgreement( @RequestParam int agreementId ) {
        try {
            boolean signStatus = agreementDao.singAgreement(agreementId);
            if (signStatus)
                return HttpStatus.ACCEPTED;

            return HttpStatus.NOT_FOUND;

        } catch (IllegalArgumentException e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    @GetMapping("/findByApplication")
    @Operation(summary = "Получение договора по идентификатору заявки")
    public ResponseEntity<Agreement> getAgreementByApplication(@RequestParam int applicationId ) {
        try {
            Agreement agreement = agreementDao.getAgreementByApplicationId(applicationId);

            return agreement == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(agreement);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
