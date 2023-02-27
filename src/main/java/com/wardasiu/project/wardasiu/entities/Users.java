package com.wardasiu.project.wardasiu.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class Users {
    @Setter
    @Getter
    @GeneratedValue
    @Id
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Setter
    @Getter
    @Column(name = "username", nullable = false)
    private String username;

    @Setter
    @Getter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Getter
    @Column(name = "role", nullable = false)
    private String role;
}