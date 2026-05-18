package com.example.demo.applications;

import com.example.demo.user.User;

import java.util.ArrayList;

public class ApplicationDB {

    private static ArrayList<Application> applications = new ArrayList<Application>();

    private static final ApplicationDB DB = new ApplicationDB();
    private ApplicationDB(){}
    public ApplicationDB getDB(){
        return DB;
    }

    public void addApplication(Application application) {


        applications.add(application);
    }

    public ArrayList<Application> getApplicationsByUser(User user){
        ArrayList<Application> usersApplications = new ArrayList<Application>();

        for (Application application : applications){
            if (application.getUser() == user){
                usersApplications.add(application);
            }
        }
        return usersApplications;
    }
}
