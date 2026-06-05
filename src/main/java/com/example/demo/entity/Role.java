package com.example.demo.entity;

import com.example.demo.enums.Roles;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @ManyToMany(mappedBy = "roles") // Имя поля в классе User
    private List<User> users;

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

}
