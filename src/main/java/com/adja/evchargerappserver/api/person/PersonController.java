package com.adja.evchargerappserver.api.person;


import com.adja.evchargerappserver.api.abstracts.AbstractController;
import com.adja.evchargerappserver.api.abstracts.NotValidUpdateException;
import com.adja.evchargerappserver.api.role.Role;
import com.adja.evchargerappserver.security.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/person")
@Api(value = "/api/person", tags = "Persons")
public class PersonController extends AbstractController<Person,PersonService> {

    @PostMapping("/{id}/addrole")
    public ResponseEntity<?> addRoleToUser(@RequestParam Long id, @RequestBody Role role) {

        try {
            this.service.addRoleToUser(id, role.getName());

            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch (NotValidUpdateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<Person> getByUsername(@PathVariable String username) {
        try {
            Person personByUsername = this.service.getByUsername(username);
            return new ResponseEntity<>(personByUsername, HttpStatus.OK);
        }
        catch(EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(authorizationHeader);
                Person person = this.service.getByUsername(decodedJWT.getSubject());

                String accessToken = this.service.getAccessToken(request.getRequestURL().toString(), person);
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
}
