package com.example.demo;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_ShouldReturnMappedDtoList_WhenUsersExist() {
        User user1 = new User();
        User user2 = new User();
        List<User> mockUsers = Arrays.asList(user1, user2);

        UserResponseDto dto1 = new UserResponseDto();
        UserResponseDto dto2 = new UserResponseDto();

        when(userDao.getAllUsers()).thenReturn(mockUsers);
        when(userMapper.toResponseDto(user1)).thenReturn(dto1);
        when(userMapper.toResponseDto(user2)).thenReturn(dto2);

        List<UserResponseDto> result = userService.getAllUsers();

        assertNotNull(result, "Результат не должен быть null");
        assertEquals(2, result.size(), "Размер списка должен быть 2");
        assertEquals(dto1, result.get(0), "Первый элемент должен совпадать с dto1");
        assertEquals(dto2, result.get(1), "Второй элемент должен совпадать с dto2");

        verify(userDao, times(1)).getAllUsers();
        verify(userMapper, times(1)).toResponseDto(user1);
        verify(userMapper, times(1)).toResponseDto(user2);
    }

    @Test
    void getAllUsers_ShouldReturnEmptyList_WhenUsersEmpty() {
        List<User> mockUsers = new ArrayList<>();

        when(userDao.getAllUsers()).thenReturn(mockUsers);

        List<UserResponseDto> result = userService.getAllUsers();

        assertNotNull(result, "Результат не может быть пустым");
        assertEquals(0, result.size(), "Список должен быть пустым");

        verify(userDao, times(1)).getAllUsers();
    }

    @Test
    void getUsersByName_ShouldReturnUser_UserExist() {
        String expectedFirstName = "Иван";
        String expectedSurname = "Иванов";

        User user = new User();
        user.setFirstname(expectedFirstName);
        user.setSurname(expectedSurname);

        UserResponseDto expectedDto = new UserResponseDto();
        expectedDto.setFirstname(expectedFirstName);
        expectedDto.setSurname(expectedSurname);

        when(userDao.getUsersByName(expectedFirstName, expectedSurname))
                .thenReturn(Collections.singletonList(user));

        when(userMapper.toResponseDto(user)).thenReturn(expectedDto);

        List<UserResponseDto> result = userService.getUsersByName(expectedFirstName, expectedSurname);

        assertNotNull(result, "Результат не может быть пустым");
        assertEquals(1, result.size(), "Размер списка должен быть 1");

        UserResponseDto actualDto = result.get(0);
        assertEquals(expectedFirstName, actualDto.getFirstname(), "Имя должно совпадать");
        assertEquals(expectedSurname, actualDto.getSurname(), "Фамилия должна совпадать");
    }
}
