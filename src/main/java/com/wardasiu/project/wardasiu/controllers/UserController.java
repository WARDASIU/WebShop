package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.security.UserService;
import com.wardasiu.project.wardasiu.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @ResponseBody
    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestParam String usernameToRegister,
                                            @RequestParam String passwordToRegister,
                                            @RequestParam String emailToRegister,
                                            @RequestParam @Nullable String newsletter) {
        Map<String, Object> response = new HashMap<>();
        if (userService.isLoginAvailable(usernameToRegister)) {
            if (userService.isEmailAvailable(emailToRegister)) {
                String verificationCode = userService.generateVerificationCode();
                userService.saveUser(new User(usernameToRegister, passwordToRegister, "LOCKED", emailToRegister, Boolean.parseBoolean(newsletter), verificationCode));
                emailService.sendSimpleEmail(emailToRegister,"Kod weryfikacyjny dla konta w sklepie EasyStep", "Kod weryfikacyjny: " + verificationCode);
            } else {
                response.put("errors", Collections.singletonMap("emailTaken", true));
            }
        } else {
            response.put("errors", Collections.singletonMap("loginTaken", true));
        }
        return response;
    }

    @PostMapping("/verify")
    public ModelAndView verifyUser(Authentication authentication,
                                   @RequestParam String verificationCode) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByUsername(authentication.getName());

        if (user != null && user.getVerificationCode().equals(verificationCode)) {
            user.setEnabled(true);
            user.setRole("NORMAL");
            userService.saveUserWithoutEncode(user);
            modelAndView.setViewName("index");
            modelAndView.addObject("isLoggedIn", true);
        } else {
            modelAndView.setViewName("verification");
            modelAndView.addObject("error", true);
            modelAndView.addObject(authentication);
        }
        return modelAndView;
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean exists = userService.existsByEmail(email);

        if (exists) {
            String newPassword = randomStringGenerator();
            User user = userService.findUserByEmail(email);

            emailService.sendNewPasswordEmail(email, user.getUsername(), newPassword);
            user.setPassword(newPassword);

            userService.saveUser(user);
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ModelAndView getProfilePage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        boolean isLoggedIn = authentication != null;

        if (isLoggedIn) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
            modelAndView.addObject("isAdmin", isAdmin);
        }

        User user = userService.findUserByUsername(authentication.getName());
        modelAndView.addObject("isLoggedIn", isLoggedIn);
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateUser(@RequestBody Map<String, Object> updateFields, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.updateUser(user, updateFields);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> updatePassword(@RequestBody String confirmNewPassword,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        user.setPassword(confirmNewPassword.replace("\"",""));
        userService.saveUser(user);
        log.info(confirmNewPassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/sendMail")
    public ResponseEntity<?> sendNewsletter(@RequestParam String title,
                                            @RequestParam String subject,
                                            @RequestParam(defaultValue = "false") String inHtml) {
        List<String> userEmails = userService.getUsersWithNewsletter()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());

        if (Objects.equals(inHtml, "false")) {
            emailService.sendNewsletter(userEmails, title, subject);
        } else {
            emailService.sendHtmlNewsletter(userEmails, title, subject);
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("message", "Newsletter został wysłany!");

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/checkLogin")
    public boolean isLoggedIn(Authentication authentication) {
        return authentication != null;
    }

    private String randomStringGenerator() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 9;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}