package com.wardasiu.project.wardasiu.controller;

import com.wardasiu.project.wardasiu.controllers.UserController;
import com.wardasiu.project.wardasiu.security.UserService;
import com.wardasiu.project.wardasiu.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class UserControllerTests {
    private final static String USERNAME_TO_REGISTER = "testuser";
    private final static String PASSWORD_TO_REGISTER = "password123";
    private final static String EMAIL_TO_REGISTER = "testuser@example.com";
    private final static String NEWSLETTER_VALUE = "true";

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Test
    public void testRegisterUser() {
        log.info("Checking username and login");
        assertTrue(userService.isLoginAvailable(USERNAME_TO_REGISTER));
        assertTrue(userService.isEmailAvailable(EMAIL_TO_REGISTER));

        log.info("Checking registration");
        Map<String, Object> responseEntity = userController.registerUser(USERNAME_TO_REGISTER, PASSWORD_TO_REGISTER, EMAIL_TO_REGISTER, NEWSLETTER_VALUE);
        assertNotNull(responseEntity);
        assertFalse(responseEntity.containsKey("errors"));
        assertNotNull(userService.findUserByUsername(USERNAME_TO_REGISTER));

        log.info("Deleting user");
        userService.deleteUser(userService.findUserByUsername(USERNAME_TO_REGISTER).getIdUser());
    }
}