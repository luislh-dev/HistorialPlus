package com.historialplus.historialplus.error;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ApiError {
    private String code;
    private String message;
    private List<String> details;
    private LocalDateTime timestamp;

    public ApiError(String code, String message, List<String> details) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}