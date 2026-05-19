package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public class Agreement {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id = UUID.randomUUID();
    private UUID applicationId;
    private UUID userId;
    private AgreementStatus status = AgreementStatus.WAITING_TO_SIGN;

    public Agreement(UUID userId, UUID applicationId) {
        this.userId = userId;
        this.applicationId = applicationId;
    }

    public enum AgreementStatus {
        SIGNED, WAITING_TO_SIGN
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setStatus(AgreementStatus status) {
        this.status = status;
    }

    public void setApplicationId(UUID applicationId) {
        this.applicationId = applicationId;
    }

    public AgreementStatus getStatus() {
        return status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getApplicationId() {
        return applicationId;
    }
}
