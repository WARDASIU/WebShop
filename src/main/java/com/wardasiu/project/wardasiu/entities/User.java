package com.wardasiu.project.wardasiu.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

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

    @Column(name = "enabled")
    private boolean enabled;

    private String verificationCode;

    private boolean newsletter;

    private String name;

    private String surname;

    private Integer phone;

    private String address;

    @Column(name = "post_code")
    private String postCode;

    public User(final String username, final String password, final String role, final String email, boolean newsletter, final String verificationCode) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.newsletter = newsletter;
        this.verificationCode = verificationCode;
    }
}