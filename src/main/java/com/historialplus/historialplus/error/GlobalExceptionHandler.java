package com.historialplus.historialplus.error;

import com.historialplus.historialplus.error.dto.ApiError;
import com.historialplus.historialplus.error.dto.ApiErrorDetail;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest ignoredRequest) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ApiErrorDetail> details = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(e -> {
                String field = (e instanceof FieldError fieldError) ? fieldError.getField() : e.getObjectName();
                return ApiErrorDetail.builder()
                    .field(field)
                    .message(e.getDefaultMessage())
                    .build();
            })
            .toList();

        ApiError response = ApiError.builder()
            .code("VALIDATION_ERROR")
            .message("Validación fallida")
            .timestamp(LocalDateTime.now())
            .details(details)
            .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();

        ApiErrorDetail errorDetail = ApiErrorDetail.builder()
            .field("dataIntegrityViolation")
            .message("Error de integridad de datos")
            .build();

        if (message.contains("UK_file_type_name")) {
            errorDetail.setField("name");
            errorDetail.setMessage("El nombre del tipo de archivo ya está en uso");
        } else if (message.contains("UK_hospital_email")) {
            errorDetail.setField("email");
            errorDetail.setMessage("El correo electrónico del hospital ya está en uso");
        } else if (message.contains("UK_hospital_name")) {
            errorDetail.setField("name");
            errorDetail.setMessage("El nombre del hospital ya está en uso");
        } else if (message.contains("UK_hospital_phone")) {
            errorDetail.setField("phone");
            errorDetail.setMessage("El teléfono del hospital ya está en uso");
        } else if (message.contains("UK_hospital_ruc")) {
            errorDetail.setField("ruc");
            errorDetail.setMessage("El RUC del hospital ya está en uso");
        } else if (message.contains("UK_people_address")) {
            errorDetail.setField("address");
            errorDetail.setMessage("La dirección ya está en uso");
        } else if (message.contains("UK_people_document_number")) {
            errorDetail.setField("documentNumber");
            errorDetail.setMessage("El número de documento ya está en uso");
        } else if (message.contains("UK_roles_name")) {
            errorDetail.setField("name");
            errorDetail.setMessage("El nombre del rol ya está en uso");
        } else if (message.contains("UK_sex_type_name")) {
            errorDetail.setField("name");
            errorDetail.setMessage("El nombre del tipo de sexo ya está en uso");
        } else if (message.contains("UK_states_name")) {
            errorDetail.setField("name");
            errorDetail.setMessage("El nombre del estado ya está en uso");
        } else if (message.contains("UK_type_document_name")) {
            errorDetail.setField("name");
            errorDetail.setMessage("El nombre del tipo de documento ya está en uso");
        } else if (message.contains("UKa9dydk3dj4qb8cvmjijqnrg5t")) {
            errorDetail.setField("userRole");
            errorDetail.setMessage("La combinación de usuario y rol ya está en uso");
        } else if (message.contains("UK_users_email")) {
            errorDetail.setField("email");
            errorDetail.setMessage("El correo electrónico del usuario ya está en uso");
        } else if (message.contains("UK_users_name")) {
            errorDetail.setField("name");
            errorDetail.setMessage("El nombre del usuario ya está en uso");
        }

        ApiError apiError = ApiError.builder()
            .code("DATA_INTEGRITY_VIOLATION")
            .message("Error de integridad de datos")
            .timestamp(LocalDateTime.now())
            .details(Collections.singletonList(errorDetail))
            .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }
}

