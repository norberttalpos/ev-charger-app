package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LocationService extends AbstractService<Location, LocationFilter, LocationRepository> {
    @Override
    public Collection<Location> search(LocationFilter locationFilter) {
        QLocation location=QLocation.location;
        BooleanBuilder where = new BooleanBuilder();
        if(locationFilter.getId()!=null)
            where.and(location.id.eq(locationFilter.getId()));
        if(locationFilter.getLatitude()!=null){
            //todo
            //where.and(location.coordinates.)
        }

        return (Collection<Location>) this.repository.findAll(where);

    }

    @Override
    protected boolean validateEntity(Location location) {
        return true;
    }
}
