package com.example.demo.service;

import com.example.demo.dao.RoleDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;

    @Autowired
    public UserService(UserDao userDao, UserMapper userMapper, PasswordEncoder passwordEncoder, RoleDao roledao) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roledao;
    }

    public List<Application> getUsersApplications(int userId) {
        return userDao.getUsersApplications(userId);
    }

    public List<UserResponseDto> getUserByFilters(UserRequestDto user) {
        ObjectMapper mapper = new ObjectMapper();

        List<User> foundUsers = userDao.getUserByFilters(user);
        List<UserResponseDto> foundResponseDto = new ArrayList<>();

        for (User foundUser : foundUsers) {
            foundResponseDto.add(userMapper.toResponseDto(foundUser));
        }

        return foundResponseDto;
    }

    public UserResponseDto getUserByUsername(String username) {
        return userMapper.toResponseDto(userDao.getUserByUsername(username));
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        List<UserResponseDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(userMapper.toResponseDto(user));
        }
        return result;
    }

    public List<UserResponseDto> getUsersByName(String firstName, String surName) {
        List<User> users = userDao.getUsersByName(firstName, surName);
        List<UserResponseDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(userMapper.toResponseDto(user));
        }
        return result;
    }

    public UserResponseDto getUserByFullPassport(int passportSeries, int passportNumber) {
        User user = userDao.getUserByFullPassport(passportSeries, passportNumber);
        return user == null ? null : userMapper.toResponseDto(user);
    }

    public UserResponseDto getUserByPhone(String phoneNumber) {
        User user = userDao.getUsersByPhone(phoneNumber);
        return user == null ? null : userMapper.toResponseDto(user);
    }

    public void addUser(UserRequestDto requestDto) {
        User user = userMapper.toEntityFromRequest(requestDto);
        user.setRole(roleDao.getUser());
        userDao.addUser(user);
    }

    public void addAdmin(UserRequestDto requestDto) {
        User user = userMapper.toEntityFromRequest(requestDto);
        user.setRole(roleDao.getAdmin());
        userDao.addUser(user);
    }

    public UserResponseDto getUserById(int userId) {
        return userMapper.toResponseDto(userDao.getUserById(userId));
    }

}
