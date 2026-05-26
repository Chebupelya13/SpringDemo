package com.example.demo.dto.request;


import com.example.demo.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema
public class UserRequestDto {

    @Schema(description = "Имя", example = "Иван")
    public String firstname;
    @Schema(description = "Фамилия", example = "Иванов")
    public String surname;
    @Schema(description = "Отчество", example = "Иванович")
    public String patronymic;

    @Schema(description = "Серия паспорта", example = "1234")
    public int passportSeries;
    @Schema(description = "Номер паспорта", example = "123456")
    public int passportNumber;

    @Schema
    public String username;
    @Schema
    public String password;

    @Schema(description = "Адрес", example = "ул. Пушкина, д. Колотушкина")
    public String address;
    @Schema(description = "Семейное положение", example = "MARRIED")
    public User.MaritalStatus maritalStatus;

    @Schema(description = "Дата рождения")
    public Date birthday;
    @Schema(description = "Номер телефона", example = "79998886655")
    public String phoneNumber;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public void setPassportSeries(int passportSeries) {
        this.passportSeries = passportSeries;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMaritalStatus(User.MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public int getPassportSeries() {
        return passportSeries;
    }

    public int getPassportNumber() {
        return passportNumber;
    }

    public String getAddress() {
        return address;
    }

    public User.MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
