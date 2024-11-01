package com.historialplus.historialplus.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    private final SecretKey jwtSecretKey;

    public JwtValidationFilter(AuthenticationManager authenticationManager, SecretKey jwtSecretKey) {
        super(authenticationManager);
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        // Si el header es null o no contiene "Bearer", pasamos la solicitud al siguiente filtro
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Eliminar "Bearer " del token
        String token = header.replace("Bearer ", "");

        try {
            // Crear el parser con la clave de firma
            JwtParser parser = Jwts.parser().setSigningKey(jwtSecretKey).build();

            // Decodificar y validar el token
            Claims claims = parser.parseClaimsJws(token).getBody();
            String username = claims.getSubject();

            // Obtener los roles desde el claim "roles"
            List<?> rolesList = claims.get("roles", List.class);
            List<String> roles = rolesList.stream()
                    .filter(role -> role instanceof String)
                    .map(role -> (String) role)
                    .collect(Collectors.toList());

            System.out.println("Roles: " + roles);

            // Convertir los roles en una colección de GrantedAuthority
            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())) // Asegura el prefijo
                    .collect(Collectors.toList());

            System.out.println("Authorities: " + authorities);

            // Validar que el token contiene el username
            if (username != null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext(); // Limpiar contexto en caso de fallo
        }

        // Continuar con el siguiente filtro
        chain.doFilter(request, response);
    }

}
