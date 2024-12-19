package com.historialplus.historialplus.service.AuthService;

import com.historialplus.historialplus.entities.UserEntity;
import com.historialplus.historialplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.historialplus.historialplus.common.constants.RoleConstants.ROLE_ADMIN;

/**
 * Servicio para gestionar los intentos de inicio de sesión de los usuarios.
 * Este servicio rastrea los intentos de inicio de sesión exitosos y fallidos,
 * permitiendo bloquear la cuenta después de un número específico de intentos fallidos.
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_DURATION_MINUTES = 3; // 3 minutos
    private static final long PERMANENT_LOCK_DURATION = Long.MAX_VALUE;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registra un intento de inicio de sesión exitoso para un usuario.
     * Restablece el contador de intentos de inicio de sesión fallidos y desbloquea la cuenta del usuario.
     *
     * @param username el nombre de usuario del que tuvo éxito en el inicio de sesión
     */
    @Transactional
    public void loginSucceeded(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setFailedLoginAttempts(0);
            userEntity.setBlocked(false);
            userEntity.setBlockedUntil(null); // Eliminar el bloqueo si el login fue exitoso
            userEntity.setLastLoginAt(Timestamp.from(Instant.now())); // Actualizar la fecha de último inicio de sesión
            userRepository.save(userEntity);
        }
    }

    /**
     * Registra un intento de inicio de sesión fallido para un usuario.
     * Incrementa el contador de intentos de inicio de sesión fallidos y establece el tiempo de bloqueo
     * si los intentos superan el límite.
     *
     * @param username el nombre de usuario del que falló el intento de inicio de sesión
     */
    @Transactional
    public void loginFailed(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            int attempts = userEntity.getFailedLoginAttempts() + 1;
            userEntity.setFailedLoginAttempts(attempts);

            // Bloquear al usuario si ha superado el límite de intentos fallidos
            if (attempts >= MAX_FAILED_ATTEMPTS) {
                userEntity.setBlocked(true);
                userEntity.setBlockedUntil(Timestamp.from(Instant.now().plus(LOCK_DURATION_MINUTES, ChronoUnit.MINUTES)));
            }
            userRepository.save(userEntity);
        }
    }

    /**
     * Verifica si la cuenta del usuario está bloqueada debido a intentos fallidos excesivos de inicio de sesión.
     * Considera el tiempo de bloqueo establecido en `blockedUntil`.
     *
     * @param username el nombre de usuario del que se desea verificar el estado
     * @return verdadero si la cuenta del usuario está bloqueada, falso en caso contrario
     */
    public boolean isBlocked(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            if (userEntity.isBlocked()) {
                // Verifica si el bloqueo sigue activo comparando `blockedUntil` con la fecha actual
                Instant now = Instant.now();
                if (userEntity.getBlockedUntil() != null && userEntity.getBlockedUntil().toInstant().isAfter(now)) {
                    return true; // Bloqueo aún activo
                } else {
                    // Desbloquea al usuario si ha pasado el tiempo de bloqueo
                    userEntity.setBlocked(false);
                    userEntity.setBlockedUntil(null);
                    userRepository.save(userEntity);
                }
            }
        }
        return false;
    }

    // Métodos auxiliares

    /**
     * Recupera el rol del usuario autenticado desde el contexto de seguridad.
     *
     * @return el nombre del rol del usuario autenticado
     * @throws IllegalStateException si no se encuentra ninguna autoridad
     */
    @Override
    public String getAuthenticatedUserRole() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No authority found"))
                .getAuthority();
    }

    /**
     * Verifica si el rol del usuario autenticado es ROLE_ADMIN.
     *
     * @param role el nombre del rol a verificar
     * @return verdadero si el rol es ROLE_ADMIN, falso en caso contrario
     */
    @Override
    public boolean isAdmin(String role) {
        return ROLE_ADMIN.equals(role);
    }

    @Override
    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public void blockUserPermanently(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setBlocked(true);
            userEntity.setBlockedUntil(Timestamp.from(Instant.now().plus(PERMANENT_LOCK_DURATION, ChronoUnit.MILLIS)));
            userRepository.save(userEntity);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }
}
