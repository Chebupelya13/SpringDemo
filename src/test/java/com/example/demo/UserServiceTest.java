package com.example.demo;

import com.example.demo.dao.RoleDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.dto.response.ListResponseDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleDao roleDao;

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

        ListResponseDto<UserResponseDto> result = userService.getAllUsers();

        assertNotNull(result, "Результат не должен быть null");
        assertEquals(2, result.items.size(), "Размер списка должен быть 2");
        assertEquals(dto1, result.items.get(0), "Первый элемент должен совпадать с dto1");
        assertEquals(dto2, result.items.get(1), "Второй элемент должен совпадать с dto2");

        verify(userDao, times(1)).getAllUsers();
        verify(userMapper, times(1)).toResponseDto(user1);
        verify(userMapper, times(1)).toResponseDto(user2);
    }

    @Test
    void getAllUsers_ShouldReturnEmptyList_WhenUsersEmpty() {
        List<User> mockUsers = new ArrayList<>();

        when(userDao.getAllUsers()).thenReturn(mockUsers);

        ListResponseDto<UserResponseDto> result = userService.getAllUsers();

        assertNotNull(result, "Результат не может быть пустым");
        assertEquals(0, result.items.size(), "Список должен быть пустым");

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

        ListResponseDto<UserResponseDto> result = userService.getUsersByName(expectedFirstName, expectedSurname);

        assertNotNull(result, "Результат не может быть пустым");
        assertEquals(1, result.items.size(), "Размер списка должен быть 1");

        UserResponseDto actualDto = result.items.get(0);
        assertEquals(expectedFirstName, actualDto.getFirstname(), "Имя должно совпадать");
        assertEquals(expectedSurname, actualDto.getSurname(), "Фамилия должна совпадать");
    }

    @Test
    void getUsersApplications_ShouldReturnApplications_WhenExist() {
        int userId = 1;
        Application app = new Application();
        List<Application> expectedApplications = Collections.singletonList(app);

        when(userDao.getUsersApplications(userId)).thenReturn(expectedApplications);

        ListResponseDto<Application> result = userService.getUsersApplications(userId);

        assertNotNull(result);
        assertEquals(1, result.items.size());
        assertEquals(app, result.items.get(0));

        verify(userDao, times(1)).getUsersApplications(userId);
    }

    @Test
    void getUserByFilters_ShouldReturnMappedDtoList() {
        UserRequestDto requestDto = new UserRequestDto();
        User user = new User();
        List<User> mockUsers = Collections.singletonList(user);

        UserResponseDto expectedDto = new UserResponseDto();

        when(userDao.getUserByFilters(requestDto)).thenReturn(mockUsers);
        when(userMapper.toResponseDto(user)).thenReturn(expectedDto);

        ListResponseDto<UserResponseDto> result = userService.getUserByFilters(requestDto);

        assertNotNull(result);
        assertEquals(1, result.items.size());
        assertEquals(expectedDto, result.items.get(0));

        verify(userDao, times(1)).getUserByFilters(requestDto);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    void getUserByFullPassport_ShouldReturnUserDto_WhenUserExists() {
        int passportSeries = 1234;
        int passportNumber = 567890;

        User user = new User();
        UserResponseDto expectedDto = new UserResponseDto();

        when(userDao.getUserByFullPassport(passportSeries, passportNumber)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(expectedDto);

        UserResponseDto result = userService.getUserByFullPassport(passportSeries, passportNumber);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(userDao, times(1)).getUserByFullPassport(passportSeries, passportNumber);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    void getUserByFullPassport_ShouldReturnNull_WhenUserDoesNotExist() {
        int passportSeries = 1234;
        int passportNumber = 567890;

        when(userDao.getUserByFullPassport(passportSeries, passportNumber)).thenReturn(null);

        UserResponseDto result = userService.getUserByFullPassport(passportSeries, passportNumber);

        assertNull(result);

        verify(userDao, times(1)).getUserByFullPassport(passportSeries, passportNumber);
        verify(userMapper, never()).toResponseDto(any(User.class));
    }

    @Test
    void getUserByPhone_ShouldReturnUserDto_WhenUserExists() {
        String phoneNumber = "1234567890";

        User user = new User();
        UserResponseDto expectedDto = new UserResponseDto();

        when(userDao.getUsersByPhone(phoneNumber)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(expectedDto);

        UserResponseDto result = userService.getUserByPhone(phoneNumber);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(userDao, times(1)).getUsersByPhone(phoneNumber);
        verify(userMapper, times(1)).toResponseDto(user);
    }

    @Test
    void getUserByPhone_ShouldReturnNull_WhenUserDoesNotExist() {
        String phoneNumber = "1234567890";

        when(userDao.getUsersByPhone(phoneNumber)).thenReturn(null);

        UserResponseDto result = userService.getUserByPhone(phoneNumber);

        assertNull(result);

        verify(userDao, times(1)).getUsersByPhone(phoneNumber);
        verify(userMapper, never()).toResponseDto(any(User.class));
    }

    @Test
    void addUser_ShouldSaveUserWithEncodedPassword() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setPassword("plain_password");

        User user = new User();
        user.setPassword("plain_password");
        user.setUsername("testuser");

        when(userMapper.toEntityFromRequest(requestDto)).thenReturn(user);
        when(passwordEncoder.encode("plain_password")).thenReturn("encoded_password");
        when(roleDao.getUser()).thenReturn(new com.example.demo.entity.Role());

        userService.addUser(requestDto);

        assertEquals("encoded_password", user.getPassword());
        verify(userDao, times(1)).addUser(user);
    }

    @Test
    void getUserById_ShouldReturnUserDto() {
        int userId = 1;
        User user = new User();
        UserResponseDto expectedDto = new UserResponseDto();

        when(userDao.getUserById(userId)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(expectedDto);

        UserResponseDto result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(userDao, times(1)).getUserById(userId);
        verify(userMapper, times(1)).toResponseDto(user);
    }
}
