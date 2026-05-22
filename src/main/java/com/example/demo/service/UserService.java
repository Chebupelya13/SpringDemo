package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUserByFilters(User user) {
        return userDao.getUserByFilters(user);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public List<User> getUsersByName(String firstName, String surName) {
        return userDao.getUsersByName(firstName, surName);
    }

    public User getUserByFullPassport(int passportSeries, int passportNumber) {
        return userDao.getUserByFullPassport(passportSeries, passportNumber);
    }

    public User getUserByPhone(String phoneNumber) {
        return userDao.getUsersByPhone(phoneNumber);
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }


}
