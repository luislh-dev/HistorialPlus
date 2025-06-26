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
import java.util.List;
import java.util.stream.Collectors;

import static com.historialplus.historialplus.auth.constants.JwtConfig.HEADER_AUTHORIZATION;
import static com.historialplus.historialplus.auth.constants.JwtConfig.PREFIX_TOKEN;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    private final SecretKey jwtSecretKey;

    public JwtValidationFilter(AuthenticationManager authenticationManager, SecretKey jwtSecretKey) {
        super(authenticationManager);
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);

        // Si el header es null o no contiene "Bearer", pasamos la solicitud al siguiente filtro
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        // Eliminar "Bearer " del token
        String token = header.replace(PREFIX_TOKEN, "");

        try {
            // Crear el parser con la clave de firma
            JwtParser parser = Jwts.parser().verifyWith(jwtSecretKey).build();

            // Decodificar y validar el token
            Claims claims = parser.parseSignedClaims(token).getPayload();
            String username = claims.getSubject();

            // Obtener los roles desde el claim "roles"
            List<?> rolesList = claims.get("authorities", List.class);
            List<String> roles = rolesList.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();

            // Convertir los roles en una colección de GrantedAuthority
            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new) // Asegura el prefijo
                    .collect(Collectors.toList());

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
