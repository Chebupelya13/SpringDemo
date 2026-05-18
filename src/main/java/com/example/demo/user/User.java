package com.example.demo.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.UUID;

@Schema
public class User {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id = UUID.randomUUID();

    @Schema(example = "Иван", accessMode = Schema.AccessMode.AUTO)
    private String firstname;
    @Schema(example = "Иванов")
    private String surname;
    @Schema(example = "Иванович")
    private String patronymic;

    @Schema(example = "1970-01-01")
    private Date birthday;
    @Schema(example = "12 34 567890")
    private String passport;
    @Schema(example = "г. Иваново, ул Иванова, д. -1")
    private String address;
    @Schema(allowableValues = {"MARRIED", "NOT_MARRIED"})
    private MaritalStatus maritalStatus;

    @Schema(example = "+71234567890")
    private String phoneNumber;

    @Schema(example = "")
    private EmploymentPeriod employmentPeriod;


    @Schema(description = "Информация о месте работы")
    static class EmploymentPeriod {

        private Date since;
        private Date upTo;

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

    enum MaritalStatus {
        MARRIED, NOT_MARRIED
    }

    public UUID getId() {
        return id;
    }

    public User(String firstname, String surname, String patronymic,
                  Date birthday, String passport, String address,
                  MaritalStatus maritalStatus, String phoneNumber,
                  EmploymentPeriod employmentPeriod) {
        this.firstname = firstname;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.passport = passport;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.phoneNumber = phoneNumber;
        this.employmentPeriod = employmentPeriod;
    }

    public EmploymentPeriod getEmploymentPeriod() {
        return employmentPeriod;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public String getAddress() {
        return address;
    }

    public String getPassport() {
        return passport;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public User() {}

}
