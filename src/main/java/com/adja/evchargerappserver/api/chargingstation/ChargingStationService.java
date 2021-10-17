package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.location.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChargingStationService extends AbstractService<ChargingStation, ChargingStationFilter, ChargingStationRepository> {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Collection<ChargingStation> search(ChargingStationFilter chargingStationFilter) {
        return null;
    }

    @Override
    protected boolean validateEntity(ChargingStation chargingStation) {
        return this.locationRepository.findById(chargingStation.getLocation().getId()).isPresent();
    }
}
