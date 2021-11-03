package com.adja.evchargerappserver.api.abstracts;

import com.adja.evchargerappserver.api.person.Person;
import com.adja.evchargerappserver.security.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class AbstractController<ENTITY extends AbstractEntity, FILTER, SERVICE extends AbstractService<ENTITY, FILTER, ? extends CustomRepository<ENTITY>>> {

    @Autowired
    protected SERVICE service;

    @GetMapping
    public ResponseEntity<Collection<ENTITY>> getAll() {
        Collection<ENTITY> e = this.service.getAll();

        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<Collection<ENTITY>> search(@RequestBody FILTER filter) {
        Collection<ENTITY> e = this.service.search(filter);

        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ENTITY> getById(@PathVariable("id") Long id) {
        try {
            ENTITY entityById = this.service.getById(id);

            return new ResponseEntity<>(entityById, HttpStatus.OK);
        }
        catch(EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ENTITY entity) {
        try {
            ENTITY savedEntity = this.service.post(entity);

            return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
        }
        catch(NotValidUpdateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody ENTITY entity, @RequestHeader HttpHeaders headers) {
        try{

            if(this.hasRightForUpdate(id, entity, headers)) {
                ENTITY updatedEntity = this.service.put(id, entity);

                return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
        }
        catch(NotValidUpdateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            this.service.deleteById(id);

            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch(EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public boolean hasRightForUpdate(Long id, ENTITY entity, HttpHeaders headers) {
        DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(headers.getFirst(HttpHeaders.AUTHORIZATION));

        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        return roles.contains("role_admin");
    }
}