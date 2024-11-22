package com.historialplus.historialplus.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return ((FieldError) error).getField() + ": " + error.getDefaultMessage();
                    } else {
                        return error.getObjectName() + ": " + error.getDefaultMessage();
                    }
                })
                .collect(Collectors.toList());

        ApiError response = new ApiError("VALIDATION_ERROR", "Validation failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();

        // Procesar el mensaje para hacerlo más amigable
        String userFriendlyMessage;
        if (message.contains("UK_file_type_name")) {
            userFriendlyMessage = "El nombre del tipo de archivo ya está en uso";
        } else if (message.contains("UK_hospital_email")) {
            userFriendlyMessage = "El correo electrónico del hospital ya está en uso";
        } else if (message.contains("UK_hospital_name")) {
            userFriendlyMessage = "El nombre del hospital ya está en uso";
        } else if (message.contains("UK_hospital_phone")) {
            userFriendlyMessage = "El teléfono del hospital ya está en uso";
        } else if (message.contains("UK_hospital_ruc")) {
            userFriendlyMessage = "El RUC del hospital ya está en uso";
        } else if (message.contains("UK_people_address")) {
            userFriendlyMessage = "La dirección ya está en uso";
        } else if (message.contains("UK_people_document_number")) {
            userFriendlyMessage = "El número de documento ya está en uso";
        } else if (message.contains("UK_roles_name")) {
            userFriendlyMessage = "El nombre del rol ya está en uso";
        } else if (message.contains("UK_sex_type_name")) {
            userFriendlyMessage = "El nombre del tipo de sexo ya está en uso";
        } else if (message.contains("UK_states_name")) {
            userFriendlyMessage = "El nombre del estado ya está en uso";
        } else if (message.contains("UK_type_document_name")) {
            userFriendlyMessage = "El nombre del tipo de documento ya está en uso";
        } else if (message.contains("UKa9dydk3dj4qb8cvmjijqnrg5t")) {
            userFriendlyMessage = "La combinación de usuario y rol ya está en uso";
        } else if (message.contains("UK_users_email")) {
            userFriendlyMessage = "El correo electrónico del usuario ya está en uso";
        } else if (message.contains("UK_users_name")) {
            userFriendlyMessage = "El nombre del usuario ya está en uso";
        } else {
            userFriendlyMessage = "Error de integridad de datos";
        }

        ApiError apiError = new ApiError(
                "DUPLICATE_ENTRY",
                userFriendlyMessage,
                Collections.singletonList(message)
        );

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}

