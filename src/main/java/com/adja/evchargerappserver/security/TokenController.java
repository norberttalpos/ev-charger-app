package com.adja.evchargerappserver.security;

import com.adja.evchargerappserver.api.person.Person;
import com.adja.evchargerappserver.api.person.PersonService;
import com.adja.evchargerappserver.api.role.Role;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Api(value = "/api", tags = "TokenHandler")
public class TokenController {

    @Autowired
    private PersonService personService;

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                Person person = this.personService.getByUsername(JwtUtil.getUsernameFromJwt(authorizationHeader));

                String accessToken = JwtUtil.createAccessToken(
                        request.getRequestURL().toString(),
                        person.getRoles().stream().map(Role::getName).collect(Collectors.toList()),
                        request.getRequestURL().toString());

                String refreshToken = authorizationHeader.substring("Bearer ".length());


                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
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
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @ApiOperation("Login")
    @PostMapping("/login")
    public void fakeLogin(@RequestBody AuthenticationRequest body) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
