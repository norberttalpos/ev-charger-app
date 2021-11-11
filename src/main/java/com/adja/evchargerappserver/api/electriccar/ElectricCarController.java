package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.AbstractController;
import com.adja.evchargerappserver.api.NotValidUpdateException;
import com.adja.evchargerappserver.api.person.Person;
import com.adja.evchargerappserver.api.person.PersonService;
import com.adja.evchargerappserver.security.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/electricCar")
@Api(value = "/api/electricCar", tags = "ElectricCar")
public class ElectricCarController extends AbstractController<ElectricCar, ElectricCarFilter, ElectricCarService> {

    @Autowired
    private PersonService personService;

    @PostMapping("/{id}/startCharging")
    public ResponseEntity<?> startCharging(@PathVariable("id") Long id, @RequestBody StartChargingRequest requestBody) {
        boolean success = false;
        try {
            success = this.service.startCharging(id, requestBody.getChargerId());
        }
        catch(NotValidUpdateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if(success) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/endCharging")
    public ResponseEntity<?> endCharging(@PathVariable("id") Long id) {
        boolean success = false;
        try {
            success = this.service.endCharging(id);
        }
        catch(NotValidUpdateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if(success) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public boolean hasRightForUpdate(Long id, ElectricCar car, HttpHeaders headers) {
        String username = JwtUtil.getUsernameFromJwt(headers.getFirst(HttpHeaders.AUTHORIZATION));

        Person personByName = this.personService.getByUsername(username);

        return Objects.equals(car.getDriver().getId(), personByName.getId());
    }
}
