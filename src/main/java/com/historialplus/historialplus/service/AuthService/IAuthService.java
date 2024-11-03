package com.historialplus.historialplus.service.AuthService;

public interface IAuthService {
    void loginSucceeded(String username);

    void loginFailed(String username);

    boolean isBlocked(String username);
}
