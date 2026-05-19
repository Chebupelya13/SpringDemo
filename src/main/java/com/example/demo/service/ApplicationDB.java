package com.example.demo.service;

import com.example.demo.model.Application;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class ApplicationDB {

    private static ArrayList<Application> applications = new ArrayList<Application>();

    public void addApplication(Application application) {
        applications.add(application);
    }

    public ArrayList<Application> getApplicationsByUser(User user){
        ArrayList<Application> usersApplications = new ArrayList<Application>();

        for (Application application : applications){
            if (application.getUserId() == user.getId()){
                usersApplications.add(application);
            }
        }

        return usersApplications;
    }

    public ArrayList<Application> getAllAccepted () {
        ArrayList<Application> acceptedApplications = new ArrayList<Application>();

        for (Application application : applications){
            if (application.getStatus().equals(Application.ApplicationStatus.ACCEPTED))
                acceptedApplications.add(application);
        }

        return acceptedApplications;
    }

    public ArrayList<Application> getApplicationsByUser(UUID userId){
        ArrayList<Application> usersApplications = new ArrayList<Application>();


        for (Application application : applications){
            System.out.println(userId.equals(application.getUserId()));
            if (userId.equals(application.getUserId())){
                usersApplications.add(application);
            }
        }

        return usersApplications;
    }
}
