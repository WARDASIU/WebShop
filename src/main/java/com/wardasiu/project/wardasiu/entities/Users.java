package com.wardasiu.project.wardasiu.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails {
    @Transient
    private Users user;

    @Setter
    @Getter
    @GeneratedValue
    @Id
    @Column(name = "id_user", nullable = false)
    public Long idUser;

    @Setter
    @Getter
    @Column(name = "username", nullable = false)
    public String username;

    @Setter
    @Getter
    @Column(name = "password", nullable = false)
    public String password;

    @Setter
    @Column(name = "role", nullable = false)
    public String authorities;

    @Setter
    @Getter
    public boolean enabled;

    @Setter
    @Getter
    public boolean expired;

    @Setter
    @Getter
    public boolean locked;

    @Setter
    @Getter
    public boolean credentialsExpired;


    public Users(final String username, final String password, final boolean enabled, final boolean expired, final boolean locked, final boolean credentialsExpired, final Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.expired = expired;
        this.locked = locked;
        this.credentialsExpired = credentialsExpired;
    }

    public Users getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
