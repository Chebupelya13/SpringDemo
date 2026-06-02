package com.example.demo.dao;

import com.example.demo.entity.Role;
import com.example.demo.enums.Roles;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao {

    @Autowired
    private final SessionFactory sessionFactory;

    public RoleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Role getUser() {
        return findByName(Roles.USER);
    }

    public Role getAdmin() {
        return findByName(Roles.ADMIN);
    }

    public Role findByName(Roles role) {
        return sessionFactory.getCurrentSession().createQuery("from Role where role=:role", Role.class)
                .setParameter("role", role)
                .getSingleResultOrNull();
    }

    public void addRole(Role role) {
        sessionFactory.getCurrentSession().persist(role);
    }

}
