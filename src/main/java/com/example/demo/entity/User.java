package com.example.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Schema
@Entity
@Table(name = "users")
public class User {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @GeneratedValue
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Schema(example = "Иван")
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Schema(example = "Иванов")
    @Column(name = "surname", nullable = false)
    private String surname;

    @Schema(example = "Иванович")
    @Column(name = "patronymic")
    private String patronymic;

    @Schema(example = "1970-01-01")
    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Schema(example = "12 34 567890")
    @Column(name = "passport", nullable = false)
    private String passport;

    @Schema(example = "г. Иваново, ул Иванова, д. -1")
    @Column(name = "address", nullable = false)
    private String address;

    @Schema(allowableValues = {"MARRIED", "NOT_MARRIED"})
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @Schema(example = "+71234567890")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    public enum MaritalStatus {
        MARRIED, NOT_MARRIED
    }

    public int getId() {
        return id;
    }

    public User(String firstname, String surname, String patronymic,
                  Date birthday, String passport, String address,
                  MaritalStatus maritalStatus, String phoneNumber) {
        this.firstname = firstname;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.passport = passport;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.phoneNumber = phoneNumber;
    }

    public User() {}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                ", passport='" + passport + '\'' +
                ", address='" + address + '\'' +
                ", maritalStatus=" + maritalStatus +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

}

