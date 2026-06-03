package com.example.demo.service;

import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.ApplicationRequestDto;
import com.example.demo.dto.response.ApplicationResponseDto;
import com.example.demo.dto.response.ListResponseDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.enums.ApplicationStatus;
import com.example.demo.enums.PhotoType;
import com.example.demo.mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationService {

    private final ApplicationDao applicationDao;
    private final UserDao userDao;
    private final ApplicationMapper applicationMapper;
    private final MinioService minioService;

    @Autowired
    public ApplicationService(ApplicationDao applicationDao, UserDao userDao, ApplicationMapper applicationMapper, MinioService minioService) {
        this.applicationDao = applicationDao;
        this.userDao = userDao;
        this.applicationMapper = applicationMapper;
        this.minioService = minioService;
    }

    public ListResponseDto<ApplicationResponseDto> getAllApplications() {
        List<ApplicationResponseDto> applications = applicationDao.getAllApplications().stream()
                .map(applicationMapper::toResponseDto)
                .collect(Collectors.toList());

        return new ListResponseDto<>(applications);
    }

    private InputStream getApplicationFile(int applicationId, PhotoType fileType) {
        Application application= applicationDao.getApplicationById(applicationId);

        switch (fileType) {
            case REGISTRATION -> {
                return minioService.getFile(application.getUserPhotoPath());
            } case PASSPORT -> {
                return minioService.getFile(application.getPassportPhotoPath());
            } default -> {
                return minioService.getFile(application.getRegistrationPhotoPath());
            }
        }
    }

    public InputStream getPassportFile(int applicationId) {
        return this.getApplicationFile(applicationId, PhotoType.PASSPORT);
    }

    public InputStream getRegistrationFile(int applicationId) {
        return this.getApplicationFile(applicationId, PhotoType.REGISTRATION);
    }

    public InputStream getAvatarFile(int applicationId) {
        return this.getApplicationFile(applicationId, PhotoType.AVATAR);
    }

    public ListResponseDto<ApplicationResponseDto> getApplicationsByUser(int userId) {
        List<ApplicationResponseDto> applications = applicationDao.getApplicationsByUser(userId).stream()
                .map(applicationMapper::toResponseDto)
                .collect(Collectors.toList());

        return new ListResponseDto<>(applications);
    }

    public ListResponseDto<ApplicationResponseDto> getAcceptedApplicationsByUser(int userId) {
        List<Application> applications = applicationDao.getApplicationsByUser(userId);
        ArrayList<Application> acceptedApplications = new ArrayList<>();

        for (Application application : applications) {
            if (application.getStatus() == ApplicationStatus.ACCEPTED)
                acceptedApplications.add(application);
        }

        List<ApplicationResponseDto> acceptedApplicationDtos = acceptedApplications.stream()
                .map(applicationMapper::toResponseDto)
                .collect(Collectors.toList());

        return new ListResponseDto<>(acceptedApplicationDtos);
    }

    public ListResponseDto<ApplicationResponseDto> getAllAccepted() {
        List<ApplicationResponseDto> applications = applicationDao.getAllAccepted().stream()
                .map(applicationMapper::toResponseDto)
                .collect(Collectors.toList());

        return new ListResponseDto<>(applications);
    }

    public boolean createApplication(ApplicationRequestDto requestDto) {
        User user = userDao.getUserById(requestDto.getUserId());
        if (user == null || requestDto.getTermMonths() > 12 || requestDto.getTermMonths() < 1)
            return false;

        Application new_application = applicationMapper.toEntityFromRequest(requestDto);

        new_application.setPassportPhotoPath(minioService.uploadFile(requestDto.getPassportPhoto(), PhotoType.PASSPORT));
        new_application.setRegistrationPhotoPath(minioService.uploadFile(requestDto.getRegistrationPhoto(), PhotoType.REGISTRATION));
        new_application.setUserPhotoPath(minioService.uploadFile(requestDto.getUserPhoto(), PhotoType.AVATAR));

        new_application.setUser(user);

        boolean decision = new Random().nextBoolean();
        new_application.setStatus(
                decision ? ApplicationStatus.ACCEPTED : ApplicationStatus.DECLINED
        );

        applicationDao.addApplication(new_application);

        return true;
    }



}
