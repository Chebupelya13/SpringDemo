package com.example.demo.dao;

import com.example.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private final EntityManagerFactory entityManagerFactory;

    public UserDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void addUser(User user) {
        entityManagerFactory.runInTransaction(entityManager -> {
            entityManager.persist(user);
        });
    }

    public List<User> getAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<User> users = entityManager.createQuery("from User", User.class).getResultList();
        entityManager.close();
        return users;
    }

    public User getUserById(int userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = entityManager.createQuery("from User where id=:userId", User.class)
                .setParameter("userId", userId).getSingleResultOrNull();
        entityManager.close();
        return user;
    }

    public User getUserByPassportSeries(int passportSeries) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = entityManager.createQuery("from User where passportSeries=:passportSeries", User.class)
                .setParameter("passportSeries", passportSeries).getSingleResultOrNull();
        entityManager.close();

        return user;
    }

    public User getUserByPassportNumber(int passportNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = entityManager.createQuery("from User where passportNumber=:passportNumber", User.class)
                .setParameter("passportNumber", passportNumber).getSingleResultOrNull();
        entityManager.close();

        return user;
    }

    public List<User> getUsersByName (String firstName, String surName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<User> users = entityManager.createQuery(
                "from User where firstname=:firstname and surname=:surname", User.class)
                .setParameter("firstname", firstName)
                .setParameter("surname", surName)
                .getResultList();
        entityManager.close();
        return users;
    }

    public User getUsersByPhone (String phone) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = entityManager.createQuery("from User where phoneNumber=:phoneNumber", User.class)
                .setParameter("phoneNumber", phone).getSingleResultOrNull();
        entityManager.close();
        return user;
    }

}
