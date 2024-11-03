package com.historialplus.historialplus.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.historialplus.historialplus.service.AuthService.DTO.LoginRequestDTO;
import com.historialplus.historialplus.service.AuthService.IAuthService;
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
import java.util.stream.Collectors;

import static com.historialplus.historialplus.auth.constants.JwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final SecretKey jwtSecretKey;
    private final IAuthService authService;
    private String name;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SecretKey jwtSecretKey, IAuthService authService) {
        super.setAuthenticationManager(authenticationManager);
        this.jwtSecretKey = jwtSecretKey;
        this.authService = authService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        LoginRequestDTO loginRequestDTO = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);

        // Guardar el nombre de usuario solo para el caso de que la autenticación sea errónea
        name = loginRequestDTO.getName();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getName(), loginRequestDTO.getPassword());
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
                .collect(Collectors.toList());

        // Crear el token JWT e incluir las autoridades como un claim llamado "roles"
        String token = Jwts.builder()
                .subject(username)
                .claim("authorities", authorities)  // Agregar el claim de roles
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(JWT_EXPIRATION_IN_MS)))
                .signWith(jwtSecretKey)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        writeResponse(response, HttpServletResponse.SC_OK, Map.of("username", username, "token", token));
        this.name = null;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {

        authService.loginFailed(name);

        writeResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
            Map.of("message", "Usuario o Contraseña incorrectos", "error", failed.getMessage()));
        this.name = null;
    }

    private void writeResponse(HttpServletResponse response, int status, Map<String, Object> body) throws IOException {
        response.setStatus(status);
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.getWriter().flush();
        response.getWriter().close();
    }
}