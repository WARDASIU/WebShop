package com.wardasiu.project.wardasiu.security;

import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        log.info("User " + user.getUsername() + " found");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toUpperCase()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public boolean isLoginAvailable(String username) {
        User user = userRepository.findByUsername(username);
        return user == null;
    }

    public boolean isEmailAvailable(String email) {
        User user = userRepository.findByEmail(email);
        return user == null;
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findUserByUsername(final String name) {
        return userRepository.findByUsername(name);
    }

    public void updateUser(User user, Map<String, Object> updateFields) {
        for (Map.Entry<String, Object> entry : updateFields.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            Field field = ReflectionUtils.findField(User.class, fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    if (fieldName.equals("phone")) {
                        fieldValue = Integer.parseInt((String) fieldValue);
                    }
                    if (fieldName.equals("newsletter")) {
                        fieldValue = Boolean.valueOf((String) fieldValue);
                    }
                    field.set(user, fieldValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error updating user field: " + fieldName, e);
                }
            }
        }
        userRepository.save(user);
    }

    public List<User> getUsersWithNewsletter() {
        return userRepository.findAllByNewsletterTrue();
    }

    public boolean existsByEmail(final String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public String generateVerificationCode() {
        int codeLength = 6;

        String codeChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            int index = random.nextInt(codeChars.length());
            char codeChar = codeChars.charAt(index);
            codeBuilder.append(codeChar);
        }
        return codeBuilder.toString();
    }

}