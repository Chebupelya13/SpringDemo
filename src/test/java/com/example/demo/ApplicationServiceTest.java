package com.example.demo;

import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.ApplicationRequestDto;
import com.example.demo.dto.response.ApplicationResponseDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.mapper.ApplicationMapper;
import com.example.demo.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationDao applicationDao;

    @Mock
    private UserDao userDao;

    @Mock
    private ApplicationMapper applicationMapper;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void getAllApplications_ShouldReturnMappedDtoList() {
        Application application = new Application();
        List<Application> mockApplications = Collections.singletonList(application);

        ApplicationResponseDto expectedDto = new ApplicationResponseDto();

        when(applicationDao.getAllApplications(1, 1)).thenReturn(mockApplications);
        when(applicationMapper.toResponseDto(application)).thenReturn(expectedDto);

        List<ApplicationResponseDto> result = applicationService.getAllApplications(1, 1).items;

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDto, result.get(0));

        verify(applicationDao, times(1)).getAllApplications(1, 1);
        verify(applicationMapper, times(1)).toResponseDto(application);
    }

    @Test
    void getApplicationsByUser_ShouldReturnMappedDtoList() {
        int userId = 1;
        Application application = new Application();
        List<Application> mockApplications = Collections.singletonList(application);

        ApplicationResponseDto expectedDto = new ApplicationResponseDto();

        when(applicationDao.getApplicationsByUser(userId)).thenReturn(mockApplications);
        when(applicationMapper.toResponseDto(application)).thenReturn(expectedDto);

        List<ApplicationResponseDto> result = applicationService.getApplicationsByUser(userId).items;

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDto, result.get(0));

        verify(applicationDao, times(1)).getApplicationsByUser(userId);
        verify(applicationMapper, times(1)).toResponseDto(application);
    }

    @Test
    void getAllAccepted_ShouldReturnMappedDtoList() {
        Application application = new Application();
        List<Application> mockApplications = Collections.singletonList(application);

        ApplicationResponseDto expectedDto = new ApplicationResponseDto();

        when(applicationDao.getAllAccepted()).thenReturn(mockApplications);
        when(applicationMapper.toResponseDto(application)).thenReturn(expectedDto);

        List<ApplicationResponseDto> result = applicationService.getAllAccepted().items;

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDto, result.get(0));

        verify(applicationDao, times(1)).getAllAccepted();
        verify(applicationMapper, times(1)).toResponseDto(application);
    }

    @Test
    void createApplication_ShouldReturnFalse_WhenUserNull() {
        ApplicationRequestDto requestDto = new ApplicationRequestDto();
        requestDto.setUserId(1);

        when(userDao.getUserById(requestDto.getUserId())).thenReturn(null);

        Application result = applicationService.createApplication(requestDto);

        assertNull(result);
        verify(applicationDao, never()).addApplication(any());
    }

    @Test
    void createApplication_ShouldReturnFalse_WhenTermMonthsGreaterThan12() {
        ApplicationRequestDto requestDto = new ApplicationRequestDto();
        requestDto.setUserId(1);
        requestDto.setTermMonths(13);

        User user = new User();
        when(userDao.getUserById(requestDto.getUserId())).thenReturn(user);

        Application result = applicationService.createApplication(requestDto);

        assertNull(result);
        verify(applicationDao, never()).addApplication(any());
    }

    @Test
    void createApplication_ShouldReturnFalse_WhenTermMonthsLessThan1() {
        ApplicationRequestDto requestDto = new ApplicationRequestDto();
        requestDto.setUserId(1);
        requestDto.setTermMonths(0);

        User user = new User();
        when(userDao.getUserById(requestDto.getUserId())).thenReturn(user);

        Application result = applicationService.createApplication(requestDto);

        assertNull(result);
        verify(applicationDao, never()).addApplication(any());
    }

    @Test
    void createApplication_ShouldReturnTrue_WhenConditionsMet() {
        ApplicationRequestDto requestDto = new ApplicationRequestDto();
        requestDto.setUserId(1);
        requestDto.setTermMonths(6);

        User user = new User();
        Application application = new Application();

        when(userDao.getUserById(requestDto.getUserId())).thenReturn(user);
        when(applicationMapper.toEntityFromRequest(requestDto)).thenReturn(application);

        Application result = applicationService.createApplication(requestDto);

        assertEquals(application, result);
        assertEquals(user, application.getUser());
        verify(applicationDao, times(1)).addApplication(application);
        // We are strictly avoiding assertions on the 'status' field as per user instructions
    }
}
