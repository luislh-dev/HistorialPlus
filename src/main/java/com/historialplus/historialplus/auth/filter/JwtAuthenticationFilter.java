package com.historialplus.historialplus.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.historialplus.historialplus.model.UserModel;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final SecretKey jwtSecretKey;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SecretKey jwtSecretKey) {
        super.setAuthenticationManager(authenticationManager);
        this.jwtSecretKey = jwtSecretKey;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        UserModel user = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = authResult.getName();
        int jwtExpirationInMs = 3600000;

        Instant now = Instant.now();

        // Obtener las autoridades y convertirlas en una lista de Strings
        List<String> roles = authResult.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Crear el token JWT e incluir las autoridades como un claim llamado "roles"
        String token = Jwts.builder()
                .subject(username)
                .claim("roles", roles)  // Agregar el claim de roles
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(jwtExpirationInMs)))
                .signWith(jwtSecretKey)
                .compact();

        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> body = Map.of("username", username, "token", token);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Usuario o Contraseña incorrectos");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().flush();
        response.getWriter().close();
    }
}