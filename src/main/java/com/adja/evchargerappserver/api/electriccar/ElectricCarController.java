package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.AbstractController;
import com.adja.evchargerappserver.api.abstracts.NotValidUpdateException;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/electricCar")
@Api(value = "/api/electricCar", tags = "ElectricCars")
public class ElectricCarController extends AbstractController<ElectricCar, ElectricCarFilter, ElectricCarService> {

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
}
