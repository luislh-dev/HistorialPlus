package com.historialplus.historialplus.auth.service;

public interface AuthService {
    void loginSucceeded(String username);

    void loginFailed(String username);

    boolean isBlocked(String username);

    String getAuthenticatedUserRole();

    boolean isAdmin(String role);

    String getUsername();

    void blockUserPermanently(String username);
}
