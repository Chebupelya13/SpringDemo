package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    public List<Application> getUsersApplications(int userId) {
        return userDao.getUsersApplications(userId);
    }

    public List<UserResponseDto> getUserByFilters(UserRequestDto user) {
        ObjectMapper mapper = new ObjectMapper();

        List<User> foundUsers = userDao.getUserByFilters(mapper.convertValue(user, HashMap.class));
        List<UserResponseDto> foundResponseDto = new ArrayList<>();

        for (User foundUser : foundUsers) {
            foundResponseDto.add(userMapper.toResponseDto(foundUser));
        }

        return foundResponseDto;
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

    public UserResponseDto getUserById(int userId) {
        return userMapper.toResponseDto(userDao.getUserById(userId));
    }

}
