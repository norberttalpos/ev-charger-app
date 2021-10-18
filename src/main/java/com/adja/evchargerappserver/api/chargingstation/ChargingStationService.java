package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.location.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Service
public class ChargingStationService extends AbstractService<ChargingStation, ChargingStationFilter, ChargingStationRepository> {

    @Autowired
    private LocationRepository locationRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Collection<ChargingStation> search(ChargingStationFilter filter) {

        StringBuilder queryString = new StringBuilder();
        queryString.append("select * from chargingstation c left join location l on c.location_id = l.id ");
        queryString.append("where 1 = 1 ");

        if(filter.getPoint() != null && filter.getRadius() != null) {
            queryString.append("and ST_DWithin(l.coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)\\:\\:\\geography, :radius) ");
        }
        if(filter.getMaxNumberOfChargers() != null) {
            queryString.append(String.format("and c.max_number_of_chargers > %d ", filter.getMaxNumberOfChargers()));
        }
        if(filter.getOwnerCompanyName() != null) {
            queryString.append("and c.owner_company_name LIKE '%").append(filter.getOwnerCompanyName()).append("%'");
        }

        queryString.append(";");

        Query query = em.createNativeQuery(queryString.toString(), ChargingStation.class);

        if(filter.getPoint() != null && filter.getRadius() != null) {
            query.setParameter("radius", filter.getRadius());
            query.setParameter("latitude", filter.getPoint().getLatitude());
            query.setParameter("longitude", filter.getPoint().getLongitude());
        }

        return (Collection<ChargingStation>) query.getResultList();
    }

    @Override
    protected boolean validateEntity(ChargingStation chargingStation) {
        return this.locationRepository.findById(chargingStation.getLocation().getId()).isPresent();
    }
}
