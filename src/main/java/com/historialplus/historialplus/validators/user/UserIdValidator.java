package com.historialplus.historialplus.validators.user;

import com.historialplus.historialplus.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


/**
 * Validador personalizado para verificar si un ID de usuario es válido.
 * <p>
 * Este validador comprueba si el ID de usuario proporcionado existe en la base de datos
 * utilizando el repositorio {@link UserRepository}. Si el ID es nulo, se considera válido
 * para permitir valores opcionales.
 *
 * <p>Recomendación: Utilizar la anotación {@code @NonNull} de Java en las propiedades o métodos
 * donde se deba garantizar que el valor no sea nulo, especialmente si la lógica o reglas de negocio
 * requieren una validación estricta.</p>
 */
public class UserIdValidator implements ConstraintValidator<ValidUserId, UUID> {

    @Autowired
    private UserRepository userRepository;

    /**
     * Valida el ID de usuario.
     *
     * @param userId el ID del usuario que se debe validar. Puede ser nulo.
     * @param context el contexto de validación.
     * @return {@code true} si el ID es nulo o si existe en el sistema; de lo contrario, {@code false}.
     */
    @Override
    public boolean isValid(UUID userId, ConstraintValidatorContext context) {
        // Permitir valores nulos
        if (userId == null) {
            return true;
        }
        // Verificar si el ID existe en la base de datos
        return userRepository.findById(userId).isPresent();
    }
}