package com.adja.evchargerappserver.api.charger;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.location.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ChargerService extends AbstractService<Charger, ChargerRepository> {
    @Autowired
    LocationRepository locationRepository;

    @Override
    protected boolean validateEntity(Charger charger) {
        return locationRepository.findById(charger.getChargerType().getId()).isPresent() ;
    }
}
