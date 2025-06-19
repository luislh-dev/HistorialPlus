package com.historialplus.historialplus.error.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiError {
    private String code;
    private String message;
    private List<ApiErrorDetail> details;
    private LocalDateTime timestamp;
}