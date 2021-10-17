package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LocationService extends AbstractService<Location, LocationFilter, LocationRepository> {
    @Override
    public Collection<Location> search(LocationFilter locationFilter) {
        return null;
    }

    @Override
    protected boolean validateEntity(Location location) {
        return true;
    }
}
