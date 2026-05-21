package com.example.demo.controller;

import com.example.demo.entity.Application;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@Tag(description = "Операции с заявками", name = "Заявки")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final UserService userService;

    @Autowired
    public ApplicationController(ApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }

    @GetMapping
    @Operation(description = "Получение всех заявок")
    public ResponseEntity<List<Application>> getApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return applications.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(applications);
    }

    @GetMapping("/findByUser/{userId}")
    @Operation(description = "Получение заявок пользователя")
    public ResponseEntity<List<Application>> getUsersApplications (
            @PathVariable
            int userId
    ) {
        List<Application> usersApplications = applicationService.getApplicationsByUser(userId);
        return usersApplications.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(usersApplications);
    }

    @GetMapping("/accepted")
    @Operation(description = "Получение списка всех одобренных заявок")
    public ResponseEntity<List<Application>> getAllAcceptedApplications() {
        List<Application> acceptedApplications = applicationService.getAllAccepted();
        return acceptedApplications.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(acceptedApplications);
    }

    @PostMapping
    @Operation(description = "Создание новой заявки на кредит")
    public HttpStatus createApplication(
            @RequestParam int amount,
            @RequestParam int termMonths,
            @RequestParam int userId
    ) {
        boolean isCreated = applicationService.createApplication(userId, amount, termMonths);
        return isCreated ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }
}
