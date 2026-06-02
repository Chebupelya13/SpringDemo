package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema
public class ApplicationRequestDto {

    @Schema(description = "id клиента", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int userId;

    @Schema(description = "Сумма кредита", example = "100000")
    private int amount;

    @Schema(description = "Срок погашения кредита (1 - 12)", example = "4")
    private int termMonths;

    @Schema
    private MultipartFile passportPhoto;

    @Schema
    private MultipartFile userPhoto;

    @Schema
    private MultipartFile registrationPhoto;

    public void setPassportPhoto(MultipartFile passportPhoto) {
        this.passportPhoto = passportPhoto;
    }

    public void setUserPhoto(MultipartFile userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setRegistrationPhoto(MultipartFile registrationPhoto) {
        this.registrationPhoto = registrationPhoto;
    }

    public MultipartFile getPassportPhoto() {
        return passportPhoto;
    }

    public MultipartFile getUserPhoto() {
        return userPhoto;
    }

    public MultipartFile getRegistrationPhoto() {
        return registrationPhoto;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

}
