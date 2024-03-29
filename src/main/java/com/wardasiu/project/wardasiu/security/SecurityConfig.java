package com.wardasiu.project.wardasiu.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/verification").hasAuthority("LOCKED")
                        .antMatchers("/admin/**").hasAuthority("ADMIN")
                        .antMatchers("/admin").hasAuthority("ADMIN")
                        .antMatchers("/profile").hasAnyAuthority("ADMIN", "NORMAL")
                        .antMatchers("/update-profile").hasAnyAuthority("ADMIN", "NORMAL")
                        .antMatchers("/change-password").hasAnyAuthority("ADMIN", "NORMAL")
                        .anyRequest().permitAll()
                )
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .successHandler(new CustomAuthenticationSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .csrf()
                .disable()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}