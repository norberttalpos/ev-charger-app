package com.adja.evchargerappserver.api.chargingstation;

import com.adja.evchargerappserver.api.NotValidUpdateException;
import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.charger.ChargerService;
import com.adja.evchargerappserver.api.chargingstation.repository.ChargingStationRepository;
import com.adja.evchargerappserver.api.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ChargingStationService extends AbstractService<ChargingStation, ChargingStationFilter, ChargingStationRepository> {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ChargerService chargerService;

    @Override
    public Collection<ChargingStation> search(ChargingStationFilter chargingStationFilter) {
        return this.repository.filterEntities(chargingStationFilter);
    }

    @Override
    protected boolean validateEntity(ChargingStation chargingStation) {
        return this.locationRepository.findById(chargingStation.getLocation().getId()).isPresent();
    }

    @Override
    protected ChargingStation mapToEntity(ChargingStation persisted, ChargingStation dto) {
        dto.setChargers(persisted.getChargers().stream().filter(charger -> {
            return dto.getChargers().contains(charger) || charger.getCurrentlyChargingCar() != null;
        }).collect(Collectors.toList()));

        return dto;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void addCharger(Long id, Long chargerId) throws NotValidUpdateException {
        try {
            ChargingStation chargingStation = this.getById(id);

            Charger charger = chargerService.getById(chargerId);

            Collection<Charger> existingChargers = chargingStation.getChargers();

            if(existingChargers.size() < chargingStation.getMaxNumberOfChargers() && !existingChargers.contains(charger)) {
                charger.setChargingStation(chargingStation);
                existingChargers.add(charger);
                chargingStation.setChargers(existingChargers);

                this.repository.save(chargingStation);
            }
            else {
                throw new NotValidUpdateException("");
            }
        }
        catch(EntityNotFoundException e) {
            throw new NotValidUpdateException("");
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void removeCharger(Long id, Long chargerId) throws NotValidUpdateException {
        try {
            ChargingStation chargingStation = this.getById(id);

            Charger charger = chargerService.getById(chargerId);

            Collection<Charger> existingChargers = chargingStation.getChargers();

            if(existingChargers.contains(charger)) {
                existingChargers.remove(charger);
                chargingStation.setChargers(existingChargers);
            }

            this.repository.save(chargingStation);
        }
        catch(EntityNotFoundException e) {
            throw new NotValidUpdateException("");
        }
    }
}
