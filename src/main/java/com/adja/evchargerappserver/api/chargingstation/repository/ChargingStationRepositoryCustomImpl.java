package com.adja.evchargerappserver.api.chargingstation.repository;

import com.adja.evchargerappserver.api.chargingstation.ChargingStation;
import com.adja.evchargerappserver.api.chargingstation.ChargingStationFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

public class ChargingStationRepositoryCustomImpl implements ChargingStationRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Collection<ChargingStation> filterEntities(ChargingStationFilter filter) {
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
            queryString.append("and c.owner_company_name LIKE '%").append(filter.getOwnerCompanyName()).append("%' ");
        }
        if(filter.getChargerTypes() != null && filter.getChargerTypes().size() != 0) {
            queryString.append(
                            "and exists (select * from charger ch " +
                                    "join chargertype ct on ch.charger_type_id = ct.id " +
                                    "where c.id = ch.station_id and (ct.name LIKE '%")
                    .append(filter.getChargerTypes().get(0)).append("%' ");

            if(filter.getChargerTypes().size() > 0) {
                for(int i = 1; i < filter.getChargerTypes().size(); ++i) {
                    queryString.append("or ct.name LIKE '%")
                            .append(filter.getChargerTypes().get(i)).append("%' ");
                }
                queryString.append(")");
            }
            queryString.append(")");
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
}
