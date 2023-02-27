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
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Setter
    @Getter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Getter
    @Column(name = "role", nullable = false)
    private String role;

    @Setter
    @Getter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public Users(final String username, final String password, final String role, final String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }
}