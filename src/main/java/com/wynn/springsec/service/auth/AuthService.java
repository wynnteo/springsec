package com.wynn.springsec.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    boolean login(String email, String password, HttpServletRequest request, HttpServletResponse response);
}
