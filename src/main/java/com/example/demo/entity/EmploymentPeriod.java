package com.example.demo.entity;


import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "employment_periods")
public class EmploymentPeriod {

    @Column(name = "id")
    @Id
    private int id;
    @ManyToOne @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "since")
    private Date since;
    @Column(name = "up_to")
    private Date upTo;
    @Column(name = "company_title")
    private String companyTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public Date getUpTo() {
        return upTo;
    }

    public void setUpTo(Date upTo) {
        this.upTo = upTo;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }
}