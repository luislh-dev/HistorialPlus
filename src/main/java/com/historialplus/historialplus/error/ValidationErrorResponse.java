package com.historialplus.historialplus.error;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationErrorResponse {
    private  List<String> errors;

    public ValidationErrorResponse(List<String> errors) {
        this.errors = errors;
    }
}
