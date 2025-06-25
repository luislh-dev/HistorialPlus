package com.historialplus.historialplus.auth.constants;

public class JwtConfig {
    private JwtConfig(){}

    public static final String CONTENT_TYPE = "application/json";
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final int JWT_EXPIRATION_IN_MS = 36000000; // 10 hours
}
