package com.adja.evchargerappserver.api.charger;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.chargertype.ChargerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargerService extends AbstractService<Charger, ChargerRepository> {
    @Autowired
    ChargerTypeRepository chargerTypeRepository;

    @Override
    protected boolean validateEntity(Charger charger) {
        return true;//chargerTypeRepository.findById(charger.getChargerType().getId()).isPresent() ;
    }
}
