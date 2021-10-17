package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.location.LocationRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChargingStationService extends AbstractService<ChargingStation, ChargingStationFilter, ChargingStationRepository> {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Collection<ChargingStation> search(ChargingStationFilter chargingStationFilter) {
        BooleanBuilder where = new BooleanBuilder();
        QChargingStation chargingStation=QChargingStation.chargingStation;

        if(chargingStationFilter.getId()!=null)
            where.and(chargingStation.id.eq(chargingStationFilter.getId()));

        if(chargingStationFilter.getCharger()!=null) {
            //where.and(chargingStation.chargers.contains(chargingStationFilter.getChargerObject()));
        }
        if(chargingStationFilter.getOwnerCompanyName()!=null)
            where.and(chargingStation.ownerCompanyName.contains((chargingStationFilter.getOwnerCompanyName())));
        if(chargingStationFilter.getMaxNumberOfChargers()!=null)
            where.and(chargingStation.maxNumberOfChargers.eq(chargingStationFilter.getMaxNumberOfChargers()));
        if(chargingStationFilter.getDistance()!= null && chargingStationFilter.getPoint()!=null) {
            //where.and(chargingStation.location.coordinates.);
            //nem adja todo
        }
        return null;
    }

    @Override
    protected boolean validateEntity(ChargingStation chargingStation) {
        return this.locationRepository.findById(chargingStation.getLocation().getId()).isPresent();
    }
}
