package com.historialplus.historialplus.common.validators.state;

import com.historialplus.historialplus.internal.state.service.StateService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * Validador personalizado para verificar si un ID de estado es válido.
 * <p>
 * Este validador comprueba si el ID de estado proporcionado existe en la base de datos
 * utilizando el servicio {@link StateService}. Si el ID es nulo, se considera válido
 * para permitir valores opcionales.
 *
 * <p>Recomendación: Utilizar la anotación {@code @NonNull} de Java en las propiedades o métodos
 * donde se deba garantizar que el valor no sea nulo, especialmente si la lógica o reglas de negocio
 * requieren una validación estricta.</p>
 */
@RequiredArgsConstructor
public class StateIdValidator implements ConstraintValidator<ValidStateId, Integer> {

    private final StateService stateService;

    /**
     * Valida el ID de estado.
     *
     * @param stateId el ID del estado que se debe validar. Puede ser nulo.
     * @param context el contexto de validación.
     * @return {@code true} si el ID es nulo o si existe en el sistema; de lo contrario, {@code false}.
     */
    @Override
    public boolean isValid(Integer stateId, ConstraintValidatorContext context) {
        // Permitir valores nulos
        if (stateId == null) {
            return true;
        }
        // Verificar si el ID existe en la base de datos
        return stateService.findById(stateId).isPresent();
    }
}
