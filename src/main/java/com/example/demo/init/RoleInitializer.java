package com.example.demo.init;

import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleService roleService;

    public RoleInitializer(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Проверяем, есть ли уже роли, чтобы не создавать дубликаты
        if (roleService.findByName(Role.Roles.USER) == null ) {
            roleService.addRole(new Role(Role.Roles.USER));
        }

        if (roleService.findByName(Role.Roles.ADMIN) == null ) {
            roleService.addRole(new Role(Role.Roles.ADMIN));
        }
    }
}