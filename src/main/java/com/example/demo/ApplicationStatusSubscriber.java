package com.example.demo;

import com.example.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ApplicationStatusSubscriber {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationStatusSubscriber(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Async
    public void handleMessage(String message) {
        System.out.println("новая заявка ==> " + message);
        try {
            int applicationId = Integer.parseInt(message);
            long timeSleep = new Random().nextInt(0,60);
            Thread.currentThread().sleep(timeSleep*1000);

            applicationService.processApplicationDecision(applicationId);
        } catch (NumberFormatException e) {
            System.err.println("ошибка парсинга ID заявки ==> " + message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}