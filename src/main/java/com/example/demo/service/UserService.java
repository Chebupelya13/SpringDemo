package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.UserDto;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUserByFilters(UserDto user) {
        ObjectMapper mapper = new ObjectMapper();

        return userDao.getUserByFilters(mapper.convertValue(user, HashMap.class));
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

    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

}
