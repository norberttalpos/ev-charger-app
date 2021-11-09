package com.adja.evchargerappserver.api.person;


import com.adja.evchargerappserver.api.abstracts.AbstractController;
import com.adja.evchargerappserver.api.abstracts.NotValidUpdateException;
import com.adja.evchargerappserver.api.role.Role;
import com.adja.evchargerappserver.security.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/api/person")
@Api(value = "/api/person", tags = "Person")
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
    @PostMapping("/search")
    public ResponseEntity<Collection<Person>> search(@RequestBody PersonFilter filter) {
        Collection<Person> collection = this.service.search(filter);
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
    @Override
    public ResponseEntity<?> post(@RequestBody Person entity) {
        return super.post(entity);
    }

    @GetMapping("/current-person")
    public ResponseEntity<Person> getByJwtToken(@RequestHeader HttpHeaders headers){
        String username = JwtUtil.getUsernameFromJwt(headers.getFirst(headers.AUTHORIZATION));

        Person entityByName= this.service.getByUsername(username);

        return new ResponseEntity<>(entityByName, HttpStatus.OK);
    }

    @Override
    public boolean hasRightForUpdate(Long id, Person entity, HttpHeaders headers) {
        String username = JwtUtil.getUsernameFromJwt(headers.getFirst(HttpHeaders.AUTHORIZATION));

        Person personByName = this.service.getByUsername(username);

        return Objects.equals(id, personByName.getId());
    }
}
