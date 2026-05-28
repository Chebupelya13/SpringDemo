package com.example.demo.service;

import com.example.demo.dao.RoleDao;
import com.example.demo.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {

    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role findByName(Role.Roles role) {
        return roleDao.findByName(role);
    }

    public void addRole(Role role) {
        roleDao.addRole(role);
    }

}
