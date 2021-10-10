package com.adja.evchargerappserver.api.person;


import com.adja.evchargerappserver.api.abstracts.AbstractController;
import com.adja.evchargerappserver.api.abstracts.NotValidUpdateException;
import com.adja.evchargerappserver.api.role.Role;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

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
}
