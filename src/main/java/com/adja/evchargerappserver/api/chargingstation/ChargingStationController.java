package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.NotValidUpdateException;
import com.adja.evchargerappserver.api.abstracts.AbstractController;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chargingStation")
@Api(value = "/api/chargingStation", tags = "ChargingStation")
public class ChargingStationController extends AbstractController<ChargingStation, ChargingStationFilter, ChargingStationService> {

    @PutMapping("/{id}/addCharger")
    public ResponseEntity<ChargingStation> addChargerToChargingStation(@PathVariable Long id, @RequestBody ChargerChargingStation body) {
        try {
            this.service.addCharger(id, body.getChargerId());

            ChargingStation asd = this.service.getById(id);
            return new ResponseEntity<>(asd, HttpStatus.OK);
        }
        catch(NotValidUpdateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/removeCharger")
    public ResponseEntity<ChargingStation> removeChargerFromChargingStation(@PathVariable Long id, @RequestBody ChargerChargingStation body) {
        try {
            this.service.removeCharger(id, body.getChargerId());

            return new ResponseEntity<>(this.service.getById(id), HttpStatus.OK);
        }
        catch(NotValidUpdateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
