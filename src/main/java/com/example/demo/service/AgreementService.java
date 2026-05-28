package com.example.demo.service;

import com.example.demo.dao.AgreementDao;
import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.AgreementRequestDto;
import com.example.demo.dto.response.AgreementResponseDto;
import com.example.demo.dto.response.ListResponseDto;
import com.example.demo.entity.Agreement;
import com.example.demo.entity.Application;
import com.example.demo.mapper.AgreementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AgreementService {

    private final AgreementDao agreementDao;
    private final UserDao userDao;
    private final ApplicationDao applicationDao;
    private final AgreementMapper agreementMapper;

    @Autowired
    public AgreementService(AgreementDao agreementDao, UserDao userDao, ApplicationDao applicationDao, AgreementMapper agreementMapper) {
        this.agreementDao = agreementDao;
        this.userDao = userDao;
        this.applicationDao = applicationDao;
        this.agreementMapper = agreementMapper;
    }

    public ListResponseDto<AgreementResponseDto> getAgreements() {
        List<AgreementResponseDto> agreements = agreementDao.getAgreements().stream()
                .map(agreementMapper::toResponseDto)
                .collect(Collectors.toList());

        return new ListResponseDto<>(agreements);
    }

    public ListResponseDto<AgreementResponseDto> getUsersAgreements(int userId) {
        List<AgreementResponseDto> agreements =  agreementDao.getUsersAgreements(userId).stream()
                .map(agreementMapper::toResponseDto)
                .collect(Collectors.toList());

        return new ListResponseDto<>(agreements);
    }

    public AgreementResponseDto getAgreementByApplicationId(int applicationId) {
        Agreement agreement = agreementDao.getAgreementByApplicationId(applicationId);
        return agreement == null ? null : agreementMapper.toResponseDto(agreement);
    }

    public boolean signAgreement(int agreementId) {
        try {
            agreementDao.singAgreement(agreementId);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void addAgreement(AgreementRequestDto requestDto) {
        Application application = applicationDao.getApplicationById(requestDto.getApplicationId());

        agreementDao.addAgreement(application.getUser(), application);
    }

}
