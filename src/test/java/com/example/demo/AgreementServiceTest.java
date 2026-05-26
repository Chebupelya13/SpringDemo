package com.example.demo;

import com.example.demo.dao.AgreementDao;
import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.AgreementRequestDto;
import com.example.demo.dto.response.AgreementResponseDto;
import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.mapper.AgreementMapper;
import com.example.demo.service.AgreementService;
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
public class AgreementServiceTest {

    @Mock
    private AgreementDao agreementDao;

    @Mock
    private UserDao userDao;

    @Mock
    private ApplicationDao applicationDao;

    @Mock
    private AgreementMapper agreementMapper;

    @InjectMocks
    private AgreementService agreementService;

    @Test
    void getAgreements_ShouldReturnMappedDtoList() {
        Agreement agreement = new Agreement();
        List<Agreement> mockAgreements = Collections.singletonList(agreement);

        AgreementResponseDto expectedDto = new AgreementResponseDto();

        when(agreementDao.getAgreements()).thenReturn(mockAgreements);
        when(agreementMapper.toResponseDto(agreement)).thenReturn(expectedDto);

        List<AgreementResponseDto> result = agreementService.getAgreements();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDto, result.get(0));

        verify(agreementDao, times(1)).getAgreements();
        verify(agreementMapper, times(1)).toResponseDto(agreement);
    }

    @Test
    void getUsersAgreements_ShouldReturnMappedDtoList() {
        int userId = 1;
        Agreement agreement = new Agreement();
        List<Agreement> mockAgreements = Collections.singletonList(agreement);

        AgreementResponseDto expectedDto = new AgreementResponseDto();

        when(agreementDao.getUsersAgreements(userId)).thenReturn(mockAgreements);
        when(agreementMapper.toResponseDto(agreement)).thenReturn(expectedDto);

        List<AgreementResponseDto> result = agreementService.getUsersAgreements(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDto, result.get(0));

        verify(agreementDao, times(1)).getUsersAgreements(userId);
        verify(agreementMapper, times(1)).toResponseDto(agreement);
    }

    @Test
    void getAgreementByApplicationId_ShouldReturnMappedDto_WhenAgreementExists() {
        int applicationId = 1;
        Agreement agreement = new Agreement();
        AgreementResponseDto expectedDto = new AgreementResponseDto();

        when(agreementDao.getAgreementByApplicationId(applicationId)).thenReturn(agreement);
        when(agreementMapper.toResponseDto(agreement)).thenReturn(expectedDto);

        AgreementResponseDto result = agreementService.getAgreementByApplicationId(applicationId);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(agreementDao, times(1)).getAgreementByApplicationId(applicationId);
        verify(agreementMapper, times(1)).toResponseDto(agreement);
    }

    @Test
    void getAgreementByApplicationId_ShouldReturnNull_WhenAgreementDoesNotExist() {
        int applicationId = 1;

        when(agreementDao.getAgreementByApplicationId(applicationId)).thenReturn(null);

        AgreementResponseDto result = agreementService.getAgreementByApplicationId(applicationId);

        assertNull(result);

        verify(agreementDao, times(1)).getAgreementByApplicationId(applicationId);
        verify(agreementMapper, never()).toResponseDto(any());
    }

    @Test
    void signAgreement_ShouldReturnTrue_WhenSuccess() {
        int agreementId = 1;

        when(agreementDao.singAgreement(agreementId)).thenReturn(true);

        boolean result = agreementService.signAgreement(agreementId);

        assertTrue(result);
        verify(agreementDao, times(1)).singAgreement(agreementId);
    }

    @Test
    void signAgreement_ShouldReturnFalse_WhenExceptionThrown() {
        int agreementId = 1;

        when(agreementDao.singAgreement(agreementId)).thenThrow(new RuntimeException());

        boolean result = agreementService.signAgreement(agreementId);

        assertFalse(result);
        verify(agreementDao, times(1)).singAgreement(agreementId);
    }

    @Test
    void addAgreement_ShouldAddAgreementSuccessfully() {
        AgreementRequestDto requestDto = new AgreementRequestDto();
        requestDto.setApplicationId(1);

        Application application = new Application();
        User user = new User();
        application.setUser(user);

        when(applicationDao.getApplicationById(requestDto.getApplicationId())).thenReturn(application);

        agreementService.addAgreement(requestDto);

        verify(applicationDao, times(1)).getApplicationById(requestDto.getApplicationId());
        verify(agreementDao, times(1)).addAgreement(user, application);
    }
}
