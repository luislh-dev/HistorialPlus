package com.historialplus.historialplus.error;

import com.historialplus.historialplus.error.dto.ApiError;
import com.historialplus.historialplus.error.dto.ApiErrorDetail;
import com.historialplus.historialplus.error.exceptions.ConflictException;
import com.historialplus.historialplus.error.exceptions.NotFoundException;
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
import java.util.Map;

import static com.historialplus.historialplus.error.constants.ConstraintMappings.getConstraintMappings;

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

        for (Map.Entry<String, ApiErrorDetail> entry : getConstraintMappings.entrySet()) {
            if (message.contains(entry.getKey())) {
                errorDetail = entry.getValue();
                break;
            }
        }

        ApiError apiError = ApiError.builder()
            .code("DATA_INTEGRITY_VIOLATION")
            .message("Error de integridad de datos")
            .timestamp(LocalDateTime.now())
            .details(Collections.singletonList(errorDetail))
            .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        ApiError apiError = ApiError.builder()
                .code("RESOURCE_NOT_FOUND")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .details(Collections.emptyList())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflictException(ConflictException ex) {
        ApiErrorDetail detail = ApiErrorDetail.builder()
                .field("conflict")
                .message(ex.getMessage())
                .build();

        ApiError apiError = ApiError.builder()
                .code("RESOURCE_CONFLICT")
                .message("Conflicto de recurso")
                .timestamp(LocalDateTime.now())
                .details(Collections.singletonList(detail))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}

