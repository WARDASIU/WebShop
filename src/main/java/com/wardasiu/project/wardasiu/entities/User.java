package com.wardasiu.project.wardasiu.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @GeneratedValue
    @Id
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private boolean newsletter;

    private String name;

    private String surname;

    private Integer phone;

    private String address;

    @Column(name = "post_code")
    private String postCode;

    public User(final String username, final String password, final String role, final String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }
}