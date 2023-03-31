package com.wardasiu.project.wardasiu.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            if (authority.getAuthority().equals("LOCKED")) {
                response.sendRedirect("/verification");
            } else {
                setDefaultTargetUrl("/");
            }
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
