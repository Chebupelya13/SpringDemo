package com.example.demo.service;

import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.request.ApplicationRequestDto;
import com.example.demo.dto.response.ApplicationResponseDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public ApplicationService(ApplicationDao applicationDao, UserDao userDao, ApplicationMapper applicationMapper) {
        this.applicationDao = applicationDao;
        this.userDao = userDao;
        this.applicationMapper = applicationMapper;
    }

    public List<ApplicationResponseDto> getAllApplications() {
        return applicationDao.getAllApplications().stream()
                .map(applicationMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationResponseDto> getApplicationsByUser(int userId) {

        System.out.println("ROLE_" + userDao.getUserById(userId).getRole().toString());
        return applicationDao.getApplicationsByUser(userId).stream()
                .map(applicationMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationResponseDto> getAcceptedApplicationsByUser(int userId) {
        List<Application> applications = applicationDao.getApplicationsByUser(userId);
        ArrayList<Application> acceptedApplications = new ArrayList<>();

        for (Application application : applications) {
            if (application.getStatus() == Application.ApplicationStatus.ACCEPTED)
                acceptedApplications.add(application);
        }

        return acceptedApplications.stream()
                .map(applicationMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationResponseDto> getAllAccepted() {
        return applicationDao.getAllAccepted().stream()
                .map(applicationMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public boolean createApplication(ApplicationRequestDto requestDto) {
        User user = userDao.getUserById(requestDto.getUserId());
        if (user == null || requestDto.getTermMonths() > 12 || requestDto.getTermMonths() < 1)
            return false;

        Application new_application = applicationMapper.toEntityFromRequest(requestDto);
        new_application.setUser(user);

        Random rand = new Random();
        boolean decision = rand.nextBoolean();
        new_application.setStatus(
                decision ? Application.ApplicationStatus.ACCEPTED : Application.ApplicationStatus.DECLINED
        );
        applicationDao.addApplication(new_application);

        return true;
    }

}
