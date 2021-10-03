package com.adja.evchargerappserver.api.location;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/location")
@Api(value = "/api/location", tags = "Locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<Collection<Location>> getLocations() {
        return new ResponseEntity<>(this.locationService.getLocations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id") Long id) {
        Optional<Location> locationById = this.locationService.getLocationById(id);

        if(locationById.isPresent())
            return new ResponseEntity<>(locationById.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addLocation(@RequestBody Location location) {
        return new ResponseEntity<>(this.locationService.saveLocation(location), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable("id") Long id, @RequestBody Location location) {
        Optional<Location> locationWithId = this.locationService.getLocationById(id);

        if(locationWithId.isPresent()) {
            Location currentLocation = locationWithId.get();
            currentLocation.setX(location.getX());
            currentLocation.setY(location.getY());

            return new ResponseEntity<>(this.locationService.saveLocation(currentLocation), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocationById(@PathVariable("id") Long id) {
        this.locationService.deleteLocationById(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
