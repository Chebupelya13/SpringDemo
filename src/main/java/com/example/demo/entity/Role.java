package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Role {

    @GeneratedValue
    @Id
    private int id;

    @Column(name = "role")
    private Roles role;

    public enum Roles {
        ADMIN, USER
    }

}
