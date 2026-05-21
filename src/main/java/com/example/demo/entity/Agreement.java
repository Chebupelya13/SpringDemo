package com.example.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;
import java.util.UUID;

@Schema
@Entity
@Table(name = "agreements")
public class Agreement {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @GeneratedValue
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "application_id", nullable = false)
    private int applicationId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "status", nullable = false)
    private AgreementStatus status = AgreementStatus.WAITING_TO_SIGN;

    public Agreement(int applicationId, int userId) {
        this.applicationId = applicationId;
        this.userId = userId;
    }

    public Agreement() {
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "id=" + id +
                ", applicationId=" + applicationId +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }

    public enum AgreementStatus {
        SIGNED, WAITING_TO_SIGN
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStatus(AgreementStatus status) {
        this.status = status;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public AgreementStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getApplicationId() {
        return applicationId;
    }
}
