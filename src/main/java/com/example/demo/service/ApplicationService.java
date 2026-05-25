package com.example.demo.service;

import com.example.demo.dao.ApplicationDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ApplicationService {
    private final ApplicationDao applicationDao;
    private final UserDao userDao;

    public ApplicationService(ApplicationDao applicationDao, UserDao userDao) {
        this.applicationDao = applicationDao;
        this.userDao = userDao;
    }

    public List<Application> getAllApplications() {
        return applicationDao.getAllApplications();
    }

    public List<Application> getApplicationsByUser(int userId) {
        return applicationDao.getApplicationsByUser(userId);
    }

    public List<Application> getAllAccepted() {
        return applicationDao.getAllAccepted();
    }

    public boolean createApplication(int userId, int amount, int termMonths) {
        User user = userDao.getUserById(userId);
        if (user == null || termMonths > 12 || termMonths < 1)
            return false;

        Application new_application = new Application(user, amount, termMonths);
        Random rand = new Random();
        boolean decision = rand.nextBoolean();
        new_application.setStatus(
                decision ? Application.ApplicationStatus.ACCEPTED : Application.ApplicationStatus.DECLINED
        );
        applicationDao.addApplication(new_application);

        return true;
    }

}
