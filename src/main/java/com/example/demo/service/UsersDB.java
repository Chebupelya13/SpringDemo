package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UsersDB {

    private static ArrayList<User> users = new ArrayList<User>();

    public User getFirst() {

        User user = users.get(0);

        if (user == null) {
            user = new User();
        }

        return user;

    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserByPassport(String passport) {
        String soughtForPassport = passport.replace(" ", "");

        for (User user: users){
            String usersPassport = user.getPassport().replace(" ", "");

            if ( usersPassport.equals(soughtForPassport) ) {
                return user;
            }
        }

        return null ;
    }

    public ArrayList<User> getUsersByName (String firstName, String surName) {
        ArrayList<User> usersList = new ArrayList<User>();

        for(User user : users) {
            if (
                    user.getFirstname().equalsIgnoreCase(firstName) && user.getSurname().equalsIgnoreCase(surName)
            ) {
                usersList.add(user);
            }
        }

        return usersList;
    }

    public User getUsersByPhone (String phone) {

        for(User user : users) {
            if (user.getPhoneNumber().equals(phone)){
                return user;
            }
        }

        return null;
    }

    public ArrayList<UUID> getAllIds() {
        ArrayList<UUID> usersIds = new ArrayList<UUID>();

        for (User user : users) {
            usersIds.add(user.getId());
        }

        return usersIds;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

}
