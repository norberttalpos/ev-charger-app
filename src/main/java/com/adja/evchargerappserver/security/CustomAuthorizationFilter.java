package com.adja.evchargerappserver.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("api/login") || request.getServletPath().equals("/api/person/refreshToken")) {
            filterChain.doFilter(request, response);
        }
        else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(authorizationHeader);
                    String username = decodedJWT.getSubject();

                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);
                }
                catch(Exception e) {
                    response.setHeader("error", e.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", e.getMessage());

                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            }
            else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
