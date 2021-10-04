package com.adja.evchargerappserver.api.abstracts;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Optional;

public abstract class AbstractController<ENTITY, SERVICE extends AbstractService<ENTITY, ?>> {

    @Autowired
    protected SERVICE service;

    @GetMapping
    public ResponseEntity<Collection<ENTITY>> getAll() {
        return new ResponseEntity<>(this.service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ENTITY> getById(@PathVariable("id") Long id) {
        ENTITY entityById = null;
        try {
            entityById = this.service.getById(id);

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
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody ENTITY entity) {
        try{
            ENTITY updatedEntity = this.service.put(id, entity);

            return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
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
}