package com.example.demo.dto.response;

import com.example.demo.entity.Application;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApplicationResponseDto {

    @Schema(description = "ID заявки", example = "1")
    private int id;

    @Schema(description = "статус заявки", example = "IN_PROGRESS")
    private Application.ApplicationStatus status;

    @Schema(description = "Сумма кредита", example = "100000")
    private int amount;

    @Schema(description = "Срок погашения кредита (1 - 12)", example = "4")
    private int termMonths;

    // Optional: Return minimal user information or user DTO instead of complete entity
    @Schema(description = "ID пользователя", example = "1")
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Application.ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(Application.ApplicationStatus status) {
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
