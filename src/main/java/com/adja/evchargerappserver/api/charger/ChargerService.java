package com.adja.evchargerappserver.api.charger;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.chargertype.ChargerTypeRepository;
import com.adja.evchargerappserver.api.chargingstation.ChargingStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargerService extends AbstractService<Charger, ChargerRepository> {
    @Autowired
    ChargerTypeRepository chargerTypeRepository;

    @Autowired
    ChargerRepository chargerRepository;

    @Autowired
    ChargingStationRepository chargingStationRepository;

    @Override
    protected boolean validateEntity(Charger charger) {
        return this.chargerTypeRepository.findById(charger.getChargerType().getId()).isPresent() &&
                (charger.getCurrentlyChargingCar() == null || this.chargerRepository.findById(charger.getCurrentlyChargingCar().getId()).isPresent()) &&
                (charger.getChargingStation() == null || this.chargingStationRepository.findById(charger.getChargingStation().getId()).isPresent());
    }
}
