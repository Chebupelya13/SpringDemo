package com.example.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Schema(description = "Информация о месте работы")
@Entity
@Table(name = "employment_periods")
public class EmploymentPeriod {

    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "since")
    private Date since;
    @Column(name = "up_to")
    private Date upTo;
    @Column(name = "company_title")
    private String companyTitle;

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