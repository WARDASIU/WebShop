package com.wardasiu.project.wardasiu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Setter
    @Getter
    @GeneratedValue
    @Id
    @Column(name = "id_user", nullable = false)
    public Long idUser;

    @Setter
    @Getter
    @Column(name = "login", nullable = false)
    public String username;

    @Setter
    @Getter
    @Column(name = "password", nullable = false)
    public String password;

    @Setter
    @Getter
    @Column(name = "role", nullable = false)
    public String role;
}
