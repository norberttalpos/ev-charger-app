package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LocationService extends AbstractService<Location, LocationFilter, LocationRepository> {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Collection<Location> search(LocationFilter filter) {
        return this.locationRepository.filterEntities(filter);
    }

    @Override
    protected boolean validateEntity(Location location) {
        return true;
    }
}
