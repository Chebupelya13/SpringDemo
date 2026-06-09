package com.example.demo;

import com.example.demo.dto.response.ApplicationResponseDto;
import com.example.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ApplicationStatusSubscriber {

    private final ApplicationService applicationService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(20);

    @Autowired
    public ApplicationStatusSubscriber(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Async
    public void handleMessage(String message) {
        System.out.println("новая заявка ==> " + message);
        try {
            int applicationId = Integer.parseInt(message);
            long delaySeconds = new Random().nextInt(61);
            System.out.println("обработка займет ==> " + delaySeconds);

            scheduler.schedule(() -> {
                try {
                    applicationService.processApplicationDecision(applicationId);

                    CompletableFuture<HttpStatus> future = applicationService.getAndRemovePendingRequest(applicationId);
                    if (future != null) {
                        future.complete(HttpStatus.CREATED);
                    }

                } catch (Exception e) {
                    CompletableFuture<HttpStatus> future = applicationService.getAndRemovePendingRequest(applicationId);
                    if (future != null) {
                        future.completeExceptionally(e); // вернет 500
                    }
                    e.printStackTrace();
                }
            }, delaySeconds, TimeUnit.SECONDS);

        } catch (NumberFormatException e) {
            System.err.println("Ошибка парсинга ID заявки ==> " + message);
        }
    }
}