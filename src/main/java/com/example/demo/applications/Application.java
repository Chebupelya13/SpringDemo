package com.example.demo.applications;

import com.example.demo.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.UUID;

@Schema
public class Application {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id = UUID.randomUUID();

    @Schema(description = "Клиент")
    private User user;

    @Schema(description = "статус заявки", accessMode = Schema.AccessMode.READ_ONLY)
    private ApplicationStatus status = ApplicationStatus.IN_PROGRESS;
    @Schema(description = "Сумма кредита")
    private int amount;

    @Min(1) @Max(12)
    @Schema(example = "4", description = "Срок погашения кредита (1 - 12)")
    private int termMonths;

    public User getUser() {
        return user;
    }

    @Schema
    enum ApplicationStatus{
        ACCEPTED, DECLINED, IN_PROGRESS
    }

    public void setTermMonths(byte termMonths) {
        this.termMonths = termMonths;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
