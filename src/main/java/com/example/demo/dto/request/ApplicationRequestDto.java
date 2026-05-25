package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class ApplicationRequestDto {

    @Schema(description = "id клиента", example = "1")
    private int userId;

    @Schema(description = "Сумма кредита", example = "100000")
    private int amount;

    @Schema(description = "Срок погашения кредита (1 - 12)", example = "4")
    private int termMonths;

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
