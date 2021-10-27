package com.adja.evchargerappserver.api.person;


import com.adja.evchargerappserver.api.abstracts.AbstractController;
import com.adja.evchargerappserver.api.abstracts.NotValidUpdateException;
import com.adja.evchargerappserver.api.role.Role;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@RestController
@RequestMapping("/api/person")
@Api(value = "/api/person", tags = "Persons")
public class PersonController extends AbstractController<Person, PersonFilter, PersonService> {

    @PutMapping("/{id}/addrole")
    public ResponseEntity<Person> addRoleToUser(@RequestParam Long id, @RequestBody Role role) {

        try {
            Person person = this.service.getById(id);
            this.service.addRoleToUser(person.getUsername(), role.getName());

            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        catch (NotValidUpdateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<Person>> getAll() {
        Collection<Person> collection = this.service.getAll();
        collection.forEach(person -> {
            person.setPassword(null);
        });
        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") Long id) {
        try {
            Person entityById = this.service.getById(id);
            entityById.setPassword(null);

            return new ResponseEntity<>(entityById, HttpStatus.OK);
        }
        catch(EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Person entity) {
        return super.post(entity);
    }


    /*
    @GetMapping("/{username}")
    public ResponseEntity<Person> getByUsername(@PathVariable String username) {
        try {
            Person personByUsername = this.service.getByUsername(username);
            return new ResponseEntity<>(personByUsername, HttpStatus.OK);
        }
        catch(EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }*/
}
