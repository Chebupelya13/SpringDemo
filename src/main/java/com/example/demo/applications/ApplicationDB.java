package com.example.demo.applications;

import com.example.demo.user.User;

import java.util.ArrayList;
import java.util.UUID;

public class ApplicationDB {

    private static ArrayList<Application> applications = new ArrayList<Application>();

    private static final ApplicationDB DB = new ApplicationDB();
    private ApplicationDB(){}
    public static ApplicationDB getDB(){
        return DB;
    }

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
