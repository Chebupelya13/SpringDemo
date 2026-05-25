package com.example.demo.dao;

import com.example.demo.dto.request.UserDto;
import com.example.demo.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    public List<User> getUserByFilters(Map<String, Object> user) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder critBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<User> critQuery = critBuilder.createQuery(User.class);
        Root<User> root = critQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : user.entrySet()){
            if (entry.getValue() != null) {
                if (entry.getValue() instanceof Integer && (Integer) entry.getValue() == 0){
                    continue;
                }

                predicates.add(critBuilder.equal(root.get(entry.getKey()), entry.getValue()));
            }

        }

        critQuery.where(critBuilder.and(predicates));
        return session.createQuery(critQuery).getResultList();
    }

    public List<User> getAllUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User", User.class).getResultList();
    }

    public User getUserById(int userId) {
        return sessionFactory.getCurrentSession().createQuery("from User where id=:userId", User.class)
                .setParameter("userId", userId).getSingleResultOrNull();
    }

    public User getUserByPassportSeries(int passportSeries) {
        return sessionFactory.getCurrentSession().createQuery("from User where passportSeries=:passportSeries", User.class)
                .setParameter("passportSeries", passportSeries).getSingleResultOrNull();
    }

    public User getUserByPassportNumber(int passportNumber) {
        return sessionFactory.getCurrentSession().createQuery("from User where passportNumber=:passportNumber", User.class)
                .setParameter("passportNumber", passportNumber).getSingleResultOrNull();
    }

    public User getUserByFullPassport(int passportSeries, int passportNumber) {
        return sessionFactory.getCurrentSession().createQuery(
                "from User where passportSeries=:passportSeries and passportNumber=:passportNumber",
                User.class
                )
                .setParameter("passportSeries", passportSeries)
                .setParameter("passportNumber", passportNumber)
                .getSingleResultOrNull();
    }

    public List<User> getUsersByName (String firstName, String surName) {
        return sessionFactory.getCurrentSession().createQuery(
                "from User where firstname=:firstname and surname=:surname", User.class)
                .setParameter("firstname", firstName)
                .setParameter("surname", surName)
                .getResultList();
    }

    public User getUsersByPhone (String phone) {
        return sessionFactory.getCurrentSession().createQuery("from User where phoneNumber=:phoneNumber", User.class)
                .setParameter("phoneNumber", phone).getSingleResultOrNull();
    }

}
