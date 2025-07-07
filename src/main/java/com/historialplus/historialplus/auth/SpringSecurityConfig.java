package com.historialplus.historialplus.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.historialplus.historialplus.auth.filter.JwtAuthenticationFilter;
import com.historialplus.historialplus.auth.filter.JwtValidationFilter;
import com.historialplus.historialplus.auth.service.AuthService;
import com.historialplus.historialplus.common.enums.RoleEnum;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringSecurityConfig {

    // --- CONSTANTES PARA LAS RUTAS ---
    private static final String ALLERGY_CATALOG_BASE_URL = "/api/allergy-catalog";
    private static final String ALLERGY_CATALOG_BY_ID_URL = "/api/allergy-catalog/{id}";
    private static final String ALLERGY_CATALOG_DEACTIVATE_URL = "/api/allergy-catalog/{id}/deactivate";
    private static final String ALLERGY_CATALOG_REACTIVATE_URL = "/api/allergy-catalog/{id}/reactivate";

    private static final String PATIENT_ALLERGIES_BASE_URL = "/api/patient-allergies";
    private static final String PATIENT_ALLERGIES_BY_ID_URL = "/api/patient-allergies/{id}";
    private static final String PATIENT_ALLERGIES_BY_RECORD_URL = "/api/patient-allergies/by-record/{recordId}";
    private static final String PATIENT_ALLERGIES_DEACTIVATE_URL = "/api/patient-allergies/{id}/deactivate";
    private static final String PATIENT_ALLERGIES_REACTIVATE_URL = "/api/patient-allergies/{id}/reactivate";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager, SecretKey jwtSecretKey, AuthService authService,  ObjectMapper objectMapper) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
            authenticationManager, jwtSecretKey, authService, objectMapper
        );
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

        JwtValidationFilter jwtValidationFilter = new JwtValidationFilter(authenticationManager, jwtSecretKey);

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole(RoleEnum.management(), RoleEnum.admin())
                    .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole(RoleEnum.user())
                    .requestMatchers(HttpMethod.POST, "/api/users").hasRole(RoleEnum.admin())
                    .requestMatchers(HttpMethod.POST, "/api/users/createManagementUser").hasRole(RoleEnum.admin())
                    .requestMatchers(HttpMethod.POST, "/api/users/createDoctorUser").hasRole(RoleEnum.management())
                    .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole(RoleEnum.admin())
                    .requestMatchers(HttpMethod.POST, "/api/record-details").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/compress-image").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/pdf/compress").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/hospitals").hasRole(RoleEnum.admin())
                    .requestMatchers(HttpMethod.GET, "/api/hospitals").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/records").hasRole(RoleEnum.admin())
                    .requestMatchers(HttpMethod.GET, "/api/records/{documentNumber}").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/records").hasRole(RoleEnum.admin())
                    .requestMatchers(HttpMethod.GET, "/api/files").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/files/{id}").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/files").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/api/hospitals/{id}").hasRole(RoleEnum.admin())
                    // Catálogo de Alergias
                    .requestMatchers(HttpMethod.POST, ALLERGY_CATALOG_BASE_URL).permitAll()
                    .requestMatchers(HttpMethod.GET, ALLERGY_CATALOG_BASE_URL).permitAll()
                    .requestMatchers(HttpMethod.GET, ALLERGY_CATALOG_BY_ID_URL).permitAll()
                    .requestMatchers(HttpMethod.PUT, ALLERGY_CATALOG_BY_ID_URL).permitAll()
                    .requestMatchers(HttpMethod.PATCH, ALLERGY_CATALOG_DEACTIVATE_URL).permitAll()
                    .requestMatchers(HttpMethod.PATCH, ALLERGY_CATALOG_REACTIVATE_URL).permitAll()
                    //Alergias del Paciente
                    .requestMatchers(HttpMethod.POST, PATIENT_ALLERGIES_BASE_URL).permitAll()
                    .requestMatchers(HttpMethod.GET, PATIENT_ALLERGIES_BY_RECORD_URL).permitAll()
                    .requestMatchers(HttpMethod.PUT, PATIENT_ALLERGIES_BY_ID_URL).permitAll()
                    .requestMatchers(HttpMethod.PATCH, PATIENT_ALLERGIES_DEACTIVATE_URL).permitAll()
                    .requestMatchers(HttpMethod.PATCH, PATIENT_ALLERGIES_REACTIVATE_URL).permitAll()
                    .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(frontendUrl));
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}