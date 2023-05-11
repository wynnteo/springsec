package com.wynn.springsec.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    SecurityContextRepository securityContextRepository;

    @Override
    public boolean login(String email, String password, HttpServletRequest request, HttpServletResponse response) {
        UserDetails user = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, password,
                user.getAuthorities());
        authenticationManager.authenticate(token);
        boolean isAuthenticated = token.isAuthenticated();
        if (isAuthenticated) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(token);
            securityContextRepository.saveContext(context, request, response);
        }

        return isAuthenticated;
    }

}
