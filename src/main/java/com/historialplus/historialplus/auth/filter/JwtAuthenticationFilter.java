package com.historialplus.historialplus.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.historialplus.historialplus.auth.dto.LoginRequestDTO;
import com.historialplus.historialplus.auth.service.AuthService;
import com.historialplus.historialplus.error.dto.ApiError;
import com.historialplus.historialplus.error.dto.ApiErrorDetail;
import com.historialplus.historialplus.util.TimeUtils;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.historialplus.historialplus.auth.constants.JwtConfig.CONTENT_TYPE;
import static com.historialplus.historialplus.auth.constants.JwtConfig.HEADER_AUTHORIZATION;
import static com.historialplus.historialplus.auth.constants.JwtConfig.JWT_EXPIRATION_IN_MS;
import static com.historialplus.historialplus.auth.constants.JwtConfig.PREFIX_TOKEN;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final SecretKey jwtSecretKey;
    private final AuthService authService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SecretKey jwtSecretKey, AuthService authService, ObjectMapper objectMapper) {
        super.setAuthenticationManager(authenticationManager);
        this.jwtSecretKey = jwtSecretKey;
        this.authService = authService;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {

        LoginRequestDTO loginRequestDTO = objectMapper.readValue(request.getInputStream(), LoginRequestDTO.class);
        String username = loginRequestDTO.getName();

        request.setAttribute(SPRING_SECURITY_FORM_USERNAME_KEY, username);

        if (authService.isBlocked(username)) {
            writeResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                Map.of("message", "La cuenta está temporalmente bloqueada. Intente nuevamente más tarde."));
            return null;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, loginRequestDTO.getPassword());
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        String username = authResult.getName();

        authService.loginSucceeded(username);

        Instant now = Instant.now();

        // Obtener las autoridades y convertirlas en una lista de Strings
        List<String> authorities = authResult.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Crear el token JWT e incluir las autoridades como un claim llamado "roles"
        String token = Jwts.builder()
                .subject(username)
                .claim("authorities", authorities)  // Agregar el claim de roles
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(JWT_EXPIRATION_IN_MS)))
                .signWith(jwtSecretKey)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        writeResponse(response, HttpServletResponse.SC_OK, Map.of(SPRING_SECURITY_FORM_USERNAME_KEY, username, "token", token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        String username = (String) request.getAttribute(SPRING_SECURITY_FORM_USERNAME_KEY);
        authService.loginFailed(username);

        ApiError apiError = ApiError.builder()
            .code("AUTHENTICATION_FAILED")
            .message("Usuario o Contraseña incorrectos")
            .timestamp(TimeUtils.getCurrentTime())
            .details(List.of(
                ApiErrorDetail.builder()
                    .field(SPRING_SECURITY_FORM_USERNAME_KEY)
                    .message(failed.getMessage())
                    .build()
            ))
            .build();

        writeResponse(response, HttpServletResponse.SC_UNAUTHORIZED, apiError);
    }

    private void writeResponse(HttpServletResponse response, int status, Object body) throws IOException {
        response.setStatus(status);
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(objectMapper.writeValueAsString(body));
        response.getWriter().flush();
        response.getWriter().close();
    }
}