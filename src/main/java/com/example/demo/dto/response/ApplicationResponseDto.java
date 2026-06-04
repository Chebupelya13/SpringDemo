package com.example.demo.dto.response;

import com.example.demo.enums.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class ApplicationResponseDto {

    @Schema(description = "ID заявки", example = "1")
    private int id;

    @Schema(description = "статус заявки", example = "IN_PROGRESS")
    private ApplicationStatus status;

    @Schema(description = "Сумма кредита", example = "100000")
    private int amount;

    @Schema(description = "Срок погашения кредита (1 - 12)", example = "4")
    private int termMonths;

    @Schema(description = "ID пользователя", example = "1")
    private int userId;

    @Schema(description = "Список фотографий документов")
    private List<PhotoResponseDto> photos = new ArrayList<>();

    public List<PhotoResponseDto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoResponseDto> photos) {
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
