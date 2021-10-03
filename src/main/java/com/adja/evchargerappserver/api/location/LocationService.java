package com.adja.evchargerappserver.api.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Collection<Location> getLocations() {
        return this.locationRepository.findAll();
    }

    public Optional<Location> getLocationById(Long id) {
        return this.locationRepository.findById(id);
    }

    public Location saveLocation(Location location) {
        return this.locationRepository.save(location);
    }

    public void deleteLocationById(Long id) {
        this.locationRepository.deleteById(id);
    }
}
