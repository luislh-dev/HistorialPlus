package com.historialplus.historialplus.common.security;

import com.historialplus.historialplus.internal.user.repository.UserRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.historialplus.historialplus.common.constants.RoleEnum.ROLE_ADMIN;

@Aspect
@Component
public class AdminOnlyAspect {

    private final UserRepository userRepository;


    public AdminOnlyAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Before("@annotation(AdminOnly)")
    public void checkAdminRole() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAdmin = userRepository.findByUsername(username)
                .map(user -> user.getRoleEntities().stream().anyMatch(role -> role.getName().equals(ROLE_ADMIN)))
                .orElse(false);
        if (!isAdmin) {
            throw new AccessDeniedException("Usuario no autorizado.");
        }
    }

}
