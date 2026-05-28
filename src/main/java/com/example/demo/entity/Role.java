package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @GeneratedValue
    @Id
    private int id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role;

    public Role(Roles role) {
        this.role = role;
    }

    public Role() {}

    @Override
    public String toString() {
        return this.role.name();
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public Roles getRole() {
        return role;
    }

    public enum Roles {
        ADMIN, USER
    }

}
