package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class LocationService extends AbstractService<Location, LocationRepository> {
    @Override
    protected boolean validateEntity(Location location) {
        return true;
    }
}
