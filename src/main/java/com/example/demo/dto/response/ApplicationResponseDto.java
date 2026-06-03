package com.example.demo.dto.response;

import com.example.demo.enums.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApplicationResponseDto {

    @Schema(description = "ID заявки", example = "1")
    private int id;

    @Schema(description = "статус заявки", example = "IN_PROGRESS")
    private ApplicationStatus status;

    @Schema(description = "Сумма кредита", example = "100000")
    private int amount;

    @Schema(description = "Срок погашения кредита (1 - 12)", example = "4")
    private int termMonths;

    @Schema
    private String passportPhotoPath;
    @Schema
    private String registrationPhotoPath;
    @Schema
    private String userPhotoPath;

    @Schema(description = "ID пользователя", example = "1")
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassportPhotoPath(String passportPhotoPath) {
        this.passportPhotoPath = passportPhotoPath;
    }

    public void setRegistrationPhotoPath(String registrationPhotoPath) {
        this.registrationPhotoPath = registrationPhotoPath;
    }

    public void setUserPhotoPath(String userPhotoPath) {
        this.userPhotoPath = userPhotoPath;
    }

    public String getPassportPhotoPath() {
        return passportPhotoPath;
    }

    public String getRegistrationPhotoPath() {
        return registrationPhotoPath;
    }

    public String getUserPhotoPath() {
        return userPhotoPath;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
