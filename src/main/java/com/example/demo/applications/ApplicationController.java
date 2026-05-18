package com.example.demo.applications;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
@Tag(description = "Операции с заявками", name = "Заявки")
public class ApplicationController {

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
            return HttpStatus.ACCEPTED;
        }

        application.setStatus(Application.ApplicationStatus.DECLINED);
        return HttpStatus.NOT_ACCEPTABLE;


    }
}
