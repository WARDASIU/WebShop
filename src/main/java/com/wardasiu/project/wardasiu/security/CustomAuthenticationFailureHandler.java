package com.wardasiu.project.wardasiu.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // add a loginError flag to the ModelAndView
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("loginError", true);
        String errorMessage = "Invalid username or password";
        mav.addObject("loginError", errorMessage);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        mav.setViewName("login");
        response.sendRedirect("/login?error=" + errorMessage);
    }
}
