package com.adja.evchargerappserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            AuthenticationRequest requestBody = mapper.readValue(request.getInputStream(), AuthenticationRequest.class);

            String username = requestBody.getUsername();
            String password = requestBody.getPassword();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            return this.authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        String accessToken = JwtUtil.createAccessToken(
                user.getUsername(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
                request.getRequestURL().toString()
        );

        String refreshToken = JwtUtil.createRefreshToken(
                user.getUsername(),
                request.getRequestURL().toString()
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accesToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
