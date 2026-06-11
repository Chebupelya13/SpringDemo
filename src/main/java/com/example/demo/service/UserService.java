package com.example.demo.service;

import com.example.demo.dao.RoleDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.dto.response.ListResponseDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.Photo;
import com.example.demo.entity.User;
import com.example.demo.enums.PhotoType;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.storage.MinioService;
import com.example.demo.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;
    private final StorageService storageService;

    @Autowired
    public UserService(
            UserDao userDao, UserMapper userMapper, PasswordEncoder passwordEncoder,
            RoleDao roledao, StorageService storageService
    ) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roledao;
        this.storageService = storageService;
    }

    public List<InputStream> getUserFiles(int userId, PhotoType fileType) {
        User user = userDao.getUserById(userId);

        List<InputStream> usersFiles = new ArrayList<>();

        for (Photo photo : user.getPhotos()) {
            usersFiles.add(storageService.getFile(photo.getPath()));
        }

        return usersFiles;
    }

    @Transactional
    @Caching(put = {
        @CachePut(value = "users:byId", key = "#result.getId()"),
        @CachePut(value = "users:byUsername", key = "#result.getUsername()")
    })
    public UserResponseDto giveRoot(int userId) {
        userDao.giveRoot(userId);

        return userMapper.toResponseDto(userDao.getUserById(userId));
    }

    public ListResponseDto<Application> getUsersApplications(int userId) {
        return new ListResponseDto<>(userDao.getUsersApplications(userId));
    }

    public ListResponseDto<UserResponseDto> getUserByFilters(UserRequestDto user, int limit, int offset) {
        List<User> foundUsers = userDao.getUserByFilters(user, limit, offset);

        return new ListResponseDto<>(
                foundUsers.stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList())
        );
    }

    @Cacheable(value = "users:byUsername", key = "#username")
    public UserResponseDto getUserByUsername(String username) {
        return userMapper.toResponseDto(userDao.getUserByUsername(username));
    }

    public ListResponseDto<UserResponseDto> getAllUsers() {
        List<User> users = userDao.getAllUsers();

        return new ListResponseDto<UserResponseDto>(
                users.stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList())
        );
    }

    public ListResponseDto<UserResponseDto> getUsersByName(String firstName, String surName) {
        List<User> users = userDao.getUsersByName(firstName, surName);

        return new ListResponseDto<>(
                users.stream()
                        .map(userMapper::toResponseDto)
                        .collect(Collectors.toList())
        );
    }

    public UserResponseDto getUserByFullPassport(int passportSeries, int passportNumber) {
        User user = userDao.getUserByFullPassport(passportSeries, passportNumber);
        return user == null ? null : userMapper.toResponseDto(user);
    }

    public UserResponseDto getUserByPhone(String phoneNumber) {
        User user = userDao.getUsersByPhone(phoneNumber);
        return user == null ? null : userMapper.toResponseDto(user);
    }

    @Transactional
    public UserResponseDto addUser(UserRequestDto requestDto) {
        User user = userMapper.toEntityFromRequest(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleDao.getUser());
        userDao.addUser(user);
        return userMapper.toResponseDto(user);
    }

    @Transactional
    public UserResponseDto addAdmin(UserRequestDto requestDto) {
        User user = userMapper.toEntityFromRequest(requestDto);
        user.setRole(roleDao.getAdmin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
        return userMapper.toResponseDto(user);
    }

    @Cacheable(value = "users:byId", key = "#userId")
    public UserResponseDto getUserById(int userId) {
        return userMapper.toResponseDto(userDao.getUserById(userId));
    }

}
