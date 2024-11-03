package com.historialplus.historialplus.auth;

import com.historialplus.historialplus.auth.filter.JwtAuthenticationFilter;
import com.historialplus.historialplus.auth.filter.JwtValidationFilter;
import com.historialplus.historialplus.service.userservice.IUserService;
import io.jsonwebtoken.security.Keys;
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

    // obtener de las variables de sistema
    private static final String JWT_SECRET = System.getenv("JWT_SECRET_RECORD_PLUS") != null
            ? System.getenv("JWT_SECRET_RECORD_PLUS")
            : "default_secret_key_for_testing_purposes";

    private static final String FRONTEND_URL = System.getenv("FRONTEND_URL_RECORD_PLUS") != null
            ? System.getenv("FRONTEND_URL_RECORD_PLUS")
            : "http://localhost:3000";

    @Bean
    SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
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
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager, SecretKey jwtSecretKey, IUserService userService) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtSecretKey, userService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login"); // URL para la autenticación

        JwtValidationFilter jwtValidationFilter = new JwtValidationFilter(authenticationManager, jwtSecretKey); // Instancia del filtro de validación


        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(FRONTEND_URL));
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}