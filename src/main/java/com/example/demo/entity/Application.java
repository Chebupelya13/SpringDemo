package com.example.demo.entity;


import com.example.demo.enums.ApplicationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Entity
@Table(name = "applications")
public class Application {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;


    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.IN_PROGRESS;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Min(value = 1) @Max(value = 12)

    @Column(name = "term_months", nullable = false, columnDefinition = "integer check (term_months >= 1 and term_months <= 12)")
    private int termMonths;

    @Column(name = "passport_photo_path")
    private String passportPhotoPath;

    @Column(name = "registration_photo_path")
    private String registrationPhotoPath;

    @Column(name = "user_photo_path")
    private String userPhotoPath;

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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
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
