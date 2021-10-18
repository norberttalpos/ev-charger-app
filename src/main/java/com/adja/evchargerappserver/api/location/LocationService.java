package com.adja.evchargerappserver.api.location;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.chargingstation.ChargingStation;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Service
public class LocationService extends AbstractService<Location, LocationFilter, LocationRepository> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Collection<Location> search(LocationFilter filter) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("select * from location l ");
        queryString.append("where 1 = 1 ");

        if(filter.getPoint() != null && filter.getRadius() != null) {
            queryString.append("and ST_DWithin(l.coordinates, ST_SetSRID(ST_MakePoint(:latitude, :longitude), 4326)\\:\\:\\geography, :radius) ");
        }

        queryString.append(";");

        Query query = em.createNativeQuery(queryString.toString(), Location.class);

        if(filter.getPoint() != null && filter.getRadius() != null) {
            query.setParameter("radius", filter.getRadius());
            query.setParameter("latitude", filter.getPoint().getLatitude());
            query.setParameter("longitude", filter.getPoint().getLongitude());
        }

        return (Collection<Location>) query.getResultList();

    }

    @Override
    protected boolean validateEntity(Location location) {
        return true;
    }
}
