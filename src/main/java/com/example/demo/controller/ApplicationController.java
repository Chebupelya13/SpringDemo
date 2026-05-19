package com.example.demo.controller;

import com.example.demo.service.ApplicationDB;
import com.example.demo.model.Application;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
@Tag(description = "Операции с заявками", name = "Заявки")
public class ApplicationController {
    private final ApplicationDB appDB;

    @Autowired
    public ApplicationController(ApplicationDB appDB) {
        this.appDB = appDB;
    }

    @GetMapping("/{user_id}")
    @Operation(description = "Получение заявок пользователя")
    public List<Application> getUsersApplications (
            @PathVariable
            String user_id
    ) {
        UUID userUUID = UUID.fromString(user_id);
        ArrayList<Application> usersApplications = appDB.getApplicationsByUser(userUUID);
        return usersApplications;
    }

    @GetMapping("/accepted")
    @Operation(description = "Получение списка всех одобренных заявок")
    public List<Application> getAllAccepted() {
        ArrayList<Application>acceptedApplications = appDB.getAllAccepted();

        return acceptedApplications;
    }

    @PostMapping
    @Operation(description = "Создание новой заявки на кредит")
    public HttpStatus create_application(
            @RequestBody
            Application application
    ) {
        Random rand = new Random();
        boolean decision = rand.nextBoolean();

        if(decision) {
            application.setStatus(Application.ApplicationStatus.ACCEPTED);
            appDB.addApplication(application);

            return HttpStatus.ACCEPTED;
        }

        application.setStatus(Application.ApplicationStatus.DECLINED);
        appDB.addApplication(application);

        return HttpStatus.NOT_ACCEPTABLE;

    }
}
