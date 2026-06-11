package com.example.demo.service;

import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.ApplicationRequestDto;
import com.example.demo.dto.response.ApplicationResponseDto;
import com.example.demo.dto.response.ListResponseDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.Photo;
import com.example.demo.entity.User;
import com.example.demo.enums.ApplicationStatus;
import com.example.demo.enums.PhotoType;
import com.example.demo.mapper.ApplicationMapper;
import com.example.demo.service.storage.MinioService;
import com.example.demo.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationService {

    private final ApplicationDao applicationDao;
    private final UserDao userDao;
    private final PhotoService service;
    private final ApplicationMapper applicationMapper;
    private final StorageService storageService;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic appStatusTopic;

    private final Map<Integer, CompletableFuture<HttpStatus>> pendingRequests = new ConcurrentHashMap<>();

    public ApplicationService(
            ApplicationDao applicationDao, UserDao userDao, PhotoService service, ApplicationMapper applicationMapper,
            StorageService storageService, RedisTemplate redisTemplate, ChannelTopic appStatusTopic
    ) {
        this.applicationDao = applicationDao;
        this.userDao = userDao;
        this.service = service;
        this.applicationMapper = applicationMapper;
        this.storageService = storageService;
        this.redisTemplate = redisTemplate;
        this.appStatusTopic = appStatusTopic;
    }

    public void registerPendingRequest(Integer applicationId, CompletableFuture<HttpStatus> future) {
        pendingRequests.put(applicationId, future);
    }

    public CompletableFuture<HttpStatus> getAndRemovePendingRequest(Integer applicationId) {
        return pendingRequests.remove(applicationId);
    }

    public ListResponseDto<ApplicationResponseDto> getAllApplications(int limit, int offset) {
        List<ApplicationResponseDto> applications = applicationDao.getAllApplications(limit, offset)
                .stream()
                .map(applicationMapper::toResponseDto)
                .collect(Collectors.toList());
        return new ListResponseDto<>(applications);
    }

    public List<InputStream> getApplicationFiles(int applicationId, PhotoType fileType) {
        Application application= applicationDao.getApplicationById(applicationId);

        List<InputStream> applicationsFiles = new ArrayList<>();

        for (Photo photo : application.getPhotos()) {
            storageService.getFile(photo.getPath());
        }

        return applicationsFiles;
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

    public Application createApplication(ApplicationRequestDto requestDto) {
        User user = userDao.getUserById(requestDto.getUserId());
        if (user == null || requestDto.getTermMonths() > 12 || requestDto.getTermMonths() < 1)
            return null;

        Application newApplication = applicationMapper.toEntityFromRequest(requestDto);

        Photo passportPhoto = new Photo(
                storageService.saveFile(requestDto.getPassportPhoto(), PhotoType.PASSPORT),
                PhotoType.PASSPORT,
                storageService.getStorageType()
        );
        newApplication.addPhoto(passportPhoto);
        Photo registrationPhoto = new Photo(
                storageService.saveFile(requestDto.getRegistrationPhoto(), PhotoType.REGISTRATION),
                PhotoType.REGISTRATION,
                storageService.getStorageType()
        );
        newApplication.addPhoto(registrationPhoto);
        Photo userPhoto = new Photo(
                storageService.saveFile(requestDto.getUserPhoto(), PhotoType.AVATAR),
                PhotoType.AVATAR,
                storageService.getStorageType()
        );
        newApplication.addPhoto(userPhoto);
        newApplication.setUser(user);

        user.addPhoto(passportPhoto);
        user.addPhoto(registrationPhoto);
        user.addPhoto(userPhoto);

        applicationDao.addApplication(newApplication);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                redisTemplate.convertAndSend(appStatusTopic.getTopic(), String.valueOf(newApplication.getId()));
            }
        });

        return newApplication;
    }

    public void processApplicationDecision(int applicationID) {
        Application application = applicationDao.getApplicationById(applicationID);
        boolean decision = new Random().nextBoolean();
        application.setStatus(decision ? ApplicationStatus.ACCEPTED : ApplicationStatus.DECLINED);
    }

    public ApplicationResponseDto getApplicationById(int appId) {
        Application application = applicationDao.getApplicationById(appId);

        return applicationMapper.toResponseDto(application);
    }

}
