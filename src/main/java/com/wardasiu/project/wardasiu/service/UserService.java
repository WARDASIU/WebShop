package com.wardasiu.project.wardasiu.service;

import com.wardasiu.project.wardasiu.entities.Users;
import com.wardasiu.project.wardasiu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new Users(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                user.getAuthorities()
        );
    }
}