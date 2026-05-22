package com.example.demo.dao;

import com.example.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

//    public List<User> getUserByFilters(User user) {
//        try (Session session = sessionFactory.openSession()) {
//            CriteriaBuilder critBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<User> critQuery = critBuilder.createQuery(User.class);
//            Root<User> root = critQuery.from(User.class);
//
//            List<Predicate> predicates = new ArrayList<>();
//            for (Field field : User.class.getDeclaredFields()) {
//                field.setAccessible(true);
//                Object value = field.get(user);
//                if (value != null) {
//                    if (field.getType() == int.class && (Integer) value == 0) {
//                        continue;
//                    }
//                    predicates.add(critBuilder.equal(root.get(field.getName()), value));
//                }
//            }
//
//            if (!predicates.isEmpty()) {
//                critQuery.where(critBuilder.and(predicates));
//            }
//
//            return session.createQuery(critQuery).getResultList();
//        }
//    }

    public List<User> getAllUsers() {
        return sessionFactory.openSession().createQuery("from User", User.class).getResultList();
    }

    public User getUserById(int userId) {
        return sessionFactory.openSession().createQuery("from User where id=:userId", User.class)
                .setParameter("userId", userId).getSingleResultOrNull();
    }

    public User getUserByPassportSeries(int passportSeries) {
        return sessionFactory.openSession().createQuery("from User where passportSeries=:passportSeries", User.class)
                .setParameter("passportSeries", passportSeries).getSingleResultOrNull();
    }

    public User getUserByPassportNumber(int passportNumber) {
        return sessionFactory.openSession().createQuery("from User where passportNumber=:passportNumber", User.class)
                .setParameter("passportNumber", passportNumber).getSingleResultOrNull();
    }

    public User getUserByFullPassport(int passportSeries, int passportNumber) {
        return sessionFactory.openSession().createQuery(
                "from User where passportSeries=:passportSeries and passportNumber=:passportNumber",
                User.class
                )
                .setParameter("passportSeries", passportSeries)
                .setParameter("passportNumber", passportNumber)
                .getSingleResultOrNull();

    }

    public List<User> getUsersByName (String firstName, String surName) {
        return sessionFactory.openSession().createQuery(
                "from User where firstname=:firstname and surname=:surname", User.class)
                .setParameter("firstname", firstName)
                .setParameter("surname", surName)
                .getResultList();
    }

    public User getUsersByPhone (String phone) {
        return sessionFactory.openSession().createQuery("from User where phoneNumber=:phoneNumber", User.class)
                .setParameter("phoneNumber", phone).getSingleResultOrNull();
    }

}
