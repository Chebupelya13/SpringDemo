package com.example.demo.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "agreements")
public class Agreement {

    @GeneratedValue
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AgreementStatus status = AgreementStatus.WAITING_TO_SIGN;

    public Agreement(Application application, User user) {
        this.application = application;
        this.user = user;
    }

    public Agreement() {
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "id=" + id +
                ", applicationId=" + application.getId() +
                ", user=" + user.getId() +
                ", status=" + status +
                '}';
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public enum AgreementStatus {
        SIGNED, WAITING_TO_SIGN
    }

    public void setStatus(AgreementStatus status) {
        this.status = status;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public AgreementStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public Application getApplication() {
        return application;
    }

}
