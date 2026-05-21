package com.example.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.Check;

@Schema
@Entity
@Table(name = "applications")
public class Application {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @GeneratedValue
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Schema(description = "id клиента", accessMode = Schema.AccessMode.READ_ONLY)
    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Schema(description = "статус заявки", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status = ApplicationStatus.IN_PROGRESS;

    @Schema(description = "Сумма кредита")
    @Column(name = "amount", nullable = false)
    private int amount;

    @Min(value = 1) @Max(value = 12)
    @Schema(example = "4", description = "Срок погашения кредита (1 - 12)")
    @Column(name = "term_months", nullable = false, columnDefinition = "integer check (term_months >= 1 and term_months <= 12)")
    private int termMonths;

    public Application(User user, int amount, int termMonths) {
        this.user = user;
        this.amount = amount;
        this.termMonths = termMonths;
    }

    public Application() {    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", status=" + status +
                ", amount=" + amount +
                ", termMonths=" + termMonths +
                '}';
    }

    public User getUser() {
        return user;
    }

    @Schema
    public enum ApplicationStatus{
        ACCEPTED, DECLINED, IN_PROGRESS
    }

    public void setTermMonths(byte termMonths) {
        if ( 1 <= termMonths && termMonths <= 12)
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

    public int getId() {
        return id;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
