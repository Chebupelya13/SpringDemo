package com.example.demo.dao;

import com.example.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUser(User user) {
        sessionFactory.runInTransaction(entityManager -> {
            entityManager.persist(user);
        });
    }

    public User getUserByFilters(User user) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder critBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> critQuery = critBuilder.createQuery(User.class);
            Root<User> root = critQuery.from(User.class);
            critQuery.select(root).where(root.equalTo(user));

            return session.createQuery(critQuery).getSingleResultOrNull();
        }
    }

    public List<User> getAllUsers() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        List<User> users = entityManager.createQuery("from User", User.class).getResultList();
        entityManager.close();
        return users;
    }

    public User getUserById(int userId) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        User user = entityManager.createQuery("from User where id=:userId", User.class)
                .setParameter("userId", userId).getSingleResultOrNull();
        entityManager.close();
        return user;
    }

    public User getUserByPassportSeries(int passportSeries) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        User user = entityManager.createQuery("from User where passportSeries=:passportSeries", User.class)
                .setParameter("passportSeries", passportSeries).getSingleResultOrNull();
        entityManager.close();

        return user;
    }

    public User getUserByPassportNumber(int passportNumber) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        User user = entityManager.createQuery("from User where passportNumber=:passportNumber", User.class)
                .setParameter("passportNumber", passportNumber).getSingleResultOrNull();
        entityManager.close();

        return user;
    }

    public User getUserByFullPassport(int passportSeries, int passportNumber) {
        try (Session session = sessionFactory.openSession()){
            User user = session.createQuery(
                    "from User where passportSeries=:passportSeries and passportNumber=:passportNumber",
                    User.class
                    )
                    .setParameter("passportSeries", passportSeries)
                    .setParameter("passportNumber", passportNumber)
                    .getSingleResultOrNull();
            return user;
        }
    }

    public List<User> getUsersByName (String firstName, String surName) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        List<User> users = entityManager.createQuery(
                "from User where firstname=:firstname and surname=:surname", User.class)
                .setParameter("firstname", firstName)
                .setParameter("surname", surName)
                .getResultList();
        entityManager.close();
        return users;
    }

    public User getUsersByPhone (String phone) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        User user = entityManager.createQuery("from User where phoneNumber=:phoneNumber", User.class)
                .setParameter("phoneNumber", phone).getSingleResultOrNull();
        entityManager.close();
        return user;
    }

}
