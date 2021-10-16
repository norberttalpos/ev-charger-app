package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.AbstractController;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/electricCar")
@Api(value = "/api/electricCar", tags = "ElectricCars")
public class ElectricCarController extends AbstractController<ElectricCar,ElectricCarService> {

    @PostMapping("/{id}/startCharging")
    public ResponseEntity<?> startCharging(@PathVariable("id") Long id) {
        this.service.startCharging(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/{id}/endCharging")
    public ResponseEntity<?> endCharging(@PathVariable("id") Long id) {
        this.service.endCharging(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
