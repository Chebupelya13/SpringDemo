package com.example.demo.service;

import com.example.demo.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersDB {

    @Autowired
    private final EntityManagerFactory entityManagerFactory;

    public UsersDB(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void addUser(User user) {
        entityManagerFactory.runInTransaction(entityManager -> {
            entityManager.createNativeQuery("insert :user into users");
        });
    }

    public List<User> getAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNativeQuery("select * from users", User.class).getResultList();
    }
}
//
//    public User getUserById(UUID userId) {
//        for (User user : users) {
//            if (user.getId().equals(userId))
//                return user;
//        }
//        return null;
//    }
//
//    public User getUserByPassport(String passport) {
//        String soughtForPassport = passport.replace(" ", "");
//
//        for (User user: users){
//            String usersPassport = user.getPassport().replace(" ", "");
//
//            if ( usersPassport.equals(soughtForPassport) ) {
//                return user;
//            }
//        }
//
//        return null ;
//    }
//
//    public ArrayList<User> getUsersByName (String firstName, String surName) {
//        ArrayList<User> usersList = new ArrayList<User>();
//
//        for(User user : users) {
//            if (
//                    user.getFirstname().equalsIgnoreCase(firstName) && user.getSurname().equalsIgnoreCase(surName)
//            ) {
//                usersList.add(user);
//            }
//        }
//
//        return usersList;
//    }
//
//    public User getUsersByPhone (String phone) {
//
//        for(User user : users) {
//            if (user.getPhoneNumber().equals(phone)){
//                return user;
//            }
//        }
//
//        return null;
//    }
//
//    public ArrayList<UUID> getAllIds() {
//        ArrayList<UUID> usersIds = new ArrayList<UUID>();
//
//        for (User user : users) {
//            usersIds.add(user.getId());
//        }
//
//        return usersIds;
//    }
//
//
//}
