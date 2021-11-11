package com.adja.evchargerappserver.api.location.repository;

import com.adja.evchargerappserver.api.location.Location;
import com.adja.evchargerappserver.api.location.LocationFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Collection<Location> filterEntities(LocationFilter filter) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("select * from Location l ");
        queryString.append("where 1 = 1 ");

        if(filter.getPoint() != null && filter.getRadius() != null) {
            queryString.append("and ST_DWithin(l.coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)\\:\\:\\geography, :radius) ");
        }

        Query query = em.createNativeQuery(queryString.toString(), Location.class);

        if(filter.getPoint() != null && filter.getRadius() != null) {
            query.setParameter("radius", filter.getRadius());
            query.setParameter("latitude", filter.getPoint().getLatitude());
            query.setParameter("longitude", filter.getPoint().getLongitude());
        }

        return (Collection<Location>) query.getResultList();
    }
}
