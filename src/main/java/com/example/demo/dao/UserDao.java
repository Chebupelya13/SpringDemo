package com.example.demo.dao;

import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.service.RoleService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    private final PasswordEncoder passwordEncoder;
    private final SessionFactory sessionFactory;
    private final RoleService roleService;

    @Autowired
    public UserDao(PasswordEncoder passwordEncoder, SessionFactory sessionFactory, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.sessionFactory = sessionFactory;
        this.roleService = roleService;
    }

    public void giveRoot(int userId) {
        sessionFactory.getCurrentSession().createQuery(
                "update User set role=:role where id=:userId")
                .setParameter("role", roleService.getAdmin())
                .setParameter("userId", userId)
                .executeUpdate();
    }

    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        session.persist(user);
    }

    public User getUserByUsername(String username) {
        return sessionFactory.getCurrentSession()
                .createQuery("from User where username=:username", User.class)
                .setParameter("username", username)
                .getSingleResultOrNull();
    }

    public List<User> getUserByFilters(UserRequestDto user) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder critBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<User> critQuery = critBuilder.createQuery(User.class);
        Root<User> root = critQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (user.firstname != null && !user.firstname.isEmpty())
            predicates.add(critBuilder.equal(root.get("firstname"), user.firstname));
        if (user.surname != null && !user.surname.isEmpty())
            predicates.add(critBuilder.equal(root.get("surname"), user.surname));
        if (user.patronymic != null && !user.patronymic.isEmpty())
            predicates.add(critBuilder.equal(root.get("patronymic"), user.patronymic));
        if (user.birthday != null)
            predicates.add(critBuilder.equal(root.get("birthday"), user.birthday));
        if (user.passportSeries != 0)
            predicates.add(critBuilder.equal(root.get("passportSeries"), user.passportSeries));
        if (user.passportNumber != 0)
            predicates.add(critBuilder.equal(root.get("passportNumber"), user.passportNumber));
        if (user.address != null && !user.address.isEmpty())
            predicates.add(critBuilder.equal(root.get("address"), user.address));
        if (user.phoneNumber != null && !user.phoneNumber.isEmpty())
            predicates.add(critBuilder.equal(root.get("phoneNumber"), user.phoneNumber));
        if (user.maritalStatus != null)
            predicates.add(critBuilder.equal(root.get("maritalStatus"), user.maritalStatus));

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

    public List<Application> getUsersApplications(int userId) {
        User user = sessionFactory.getCurrentSession().createQuery("from User where id=:userId", User.class)
                .setParameter("userId", userId)
                .getSingleResultOrNull();

        return user.getApplications();
    }

}
