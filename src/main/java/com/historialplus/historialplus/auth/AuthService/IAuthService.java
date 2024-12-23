package com.historialplus.historialplus.auth.AuthService;

public interface IAuthService {
    void loginSucceeded(String username);

    void loginFailed(String username);

    boolean isBlocked(String username);

    String getAuthenticatedUserRole();

    boolean isAdmin(String role);

    String getUsername();

    void blockUserPermanently(String username);
}
