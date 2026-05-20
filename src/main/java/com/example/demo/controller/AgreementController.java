package com.example.demo.controller;

import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.service.AgreementsDB;
import com.example.demo.service.ApplicationDB;
import com.example.demo.service.UsersDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/agreement")
@Tag(name = "Договоры", description = "Операции с кредитными договорами")
public class AgreementController {

    private final AgreementsDB agreementsDB;
    private final UsersDB usersDB;
    private final ApplicationDB applicationsDB;

    @Autowired
    public AgreementController(AgreementsDB agreementsDB, UsersDB usersDB, ApplicationDB applicationsDB) {
        this.applicationsDB = applicationsDB;
        this.agreementsDB = agreementsDB;
        this.usersDB = usersDB;
    }

    @GetMapping
    @Operation(description = "Получение списка всех договоров")
    public List<Agreement> getAllAgreements() {
        return agreementsDB.getAgreements();
    }

    @PostMapping
    @Operation(description = "Создание нового договора")
    public HttpStatus createAgreement(
            @RequestParam
            String userId,
            @RequestParam
            String applicationId
    ) {
        try {
            User user = usersDB.getUserById(UUID.fromString(userId));
            Application application = applicationsDB.getApplicationById(UUID.fromString(applicationId));
            if (user == null || application == null) {
                return HttpStatus.NOT_FOUND;
            }

            agreementsDB.addAgreement(UUID.fromString(userId), UUID.fromString(applicationId));
            return HttpStatus.CREATED;

        } catch (IllegalArgumentException e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    @GetMapping("/findByUser/{userId}")
    @Operation(description = "Получение договоров пользователя")
    public List<Agreement> getUsersAgreements( @PathVariable String userId ) {
        try {
            return agreementsDB.getUsersAgreements(UUID.fromString(userId));
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    @PutMapping("/sign/{agreementId}")
    @Operation(description = "Изменение статуса договора на 'подписано'")
    public HttpStatus signAgreement( @PathVariable String agreementId ) {
        try {
            boolean signStatus = agreementsDB.singAgreement(UUID.fromString(agreementId));
            if (signStatus)
                return HttpStatus.ACCEPTED;

            return HttpStatus.NOT_FOUND;

        } catch (IllegalArgumentException e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    @GetMapping("/findByApplication/{applicationId}")
    @Operation(description = "Получение договоров пользователя")
    public ResponseEntity<Agreement> getAgreementByApplication(@PathVariable String applicationId ) {
        try {
            Agreement agreement = agreementsDB.getAgreementByApplicationId(UUID.fromString(applicationId));

            if (agreement != null) {
                return ResponseEntity.ok(agreement);
            }

            return ResponseEntity.notFound().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
