package com.historialplus.historialplus.service.AuthService;

import com.historialplus.historialplus.entities.UserEntity;
import com.historialplus.historialplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

/**
 * Servicio para gestionar los intentos de inicio de sesión de los usuarios.
 * Este servicio rastrea los intentos de inicio de sesión exitosos y fallidos,
 * permitiendo bloquear la cuenta después de un número específico de intentos fallidos.
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;

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
        Optional<UserEntity> user = userRepository.findByName(username);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setFailedLoginAttempts(0);
            userEntity.setBlocked(false);
            userEntity.setLastLoginAt(Timestamp.from(Instant.now())); // Actualizar la fecha de último inicio de sesión
            userRepository.save(userEntity);
        }
    }

    /**
     * Registra un intento de inicio de sesión fallido para un usuario.
     * Incrementa el contador de intentos de inicio de sesión fallidos y bloquea la cuenta del usuario
     * si los intentos superan el límite (por ejemplo, 3).
     *
     * @param username el nombre de usuario del que falló el intento de inicio de sesión
     */
    @Transactional
    public void loginFailed(String username) {
        Optional<UserEntity> user = userRepository.findByName(username);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            int attempts = userEntity.getFailedLoginAttempts() + 1;
            userEntity.setFailedLoginAttempts(attempts);
            if (attempts >= 3) {
                userEntity.setBlocked(true);
            }
            userRepository.save(userEntity);
        }
    }

    /**
     * Verifica si la cuenta del usuario está bloqueada debido a intentos fallidos excesivos de inicio de sesión.
     *
     * @param username el nombre de usuario del que se desea verificar el estado
     * @return verdadero si la cuenta del usuario está bloqueada, falso en caso contrario
     */
    public boolean isBlocked(String username) {
        Optional<UserEntity> user = userRepository.findByName(username);
        return user.isPresent() && user.get().isBlocked();
    }
}
