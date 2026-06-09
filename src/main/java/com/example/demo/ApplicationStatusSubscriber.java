package com.example.demo;

import com.example.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStatusSubscriber {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationStatusSubscriber(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void handleMessage(String message) {
        System.out.println("новая заявка ==> " + message);
        try {
            int applicationId = Integer.parseInt(message);
            applicationService.processApplicationDecision(applicationId);
        } catch (NumberFormatException e) {
            System.err.println("ошибка парсинга ID заявки ==> " + message);
        }
    }
}