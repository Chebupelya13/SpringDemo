package com.example.demo.dao;

import com.example.demo.entity.Role;
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
        return findByName(Role.Roles.USER);
    }

    public Role getAdmin() {
        return findByName(Role.Roles.ADMIN);
    }

    public Role findByName(Role.Roles role) {
        return sessionFactory.getCurrentSession().createQuery("from Role where role=:role", Role.class)
                .setParameter("role", role)
                .getSingleResultOrNull();
    }

    public void addRole(Role role) {
        sessionFactory.getCurrentSession().persist(role);
    }

}
