package com.adja.evchargerappserver.api.charger;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.abstracts.NotValidUpdateException;
import com.adja.evchargerappserver.api.chargertype.ChargerTypeRepository;
import com.adja.evchargerappserver.api.chargingstation.ChargingStationRepository;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.adja.evchargerappserver.api.electriccar.ElectricCarRepository;
import com.adja.evchargerappserver.api.electriccar.ElectricCarService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ChargerService extends AbstractService<Charger, ChargerFilter, ChargerRepository> {
    @Autowired
    private ChargerTypeRepository chargerTypeRepository;

    @Autowired
    private ChargerRepository chargerRepository;

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Autowired
    private ElectricCarRepository electricCarRepository;

    @Override
    public Collection<Charger> search(ChargerFilter filter) {
        QCharger charger = QCharger.charger;
        BooleanBuilder where = new BooleanBuilder();

        if(filter.getCurrentlyChargingCar() != null) {
            where.and(charger.currentlyChargingCar.id.eq(filter.getCurrentlyChargingCar()));
        }
        if(filter.getChargerType() != null) {
            where.and(charger.chargerType.name.contains(filter.getChargerType()));
        }
        if(filter.getChargingStation() != null) {
            where.and(charger.chargingStation.id.eq(filter.getChargingStation()));
        }
        if(filter.getReserved() != null) {
            if(filter.getReserved()) {
                where.and(charger.currentlyChargingCar.id.isNotNull());
            }
            else {
                where.and(charger.currentlyChargingCar.id.isNull());
            }
        }
        if(filter.getObserved() != null) {
            if(filter.getObserved()) {
                where.and(charger.personsToNotify.size().goe(1));
            }
            else {
                where.and(charger.personsToNotify.size().eq(0));
            }
        }

        return (Collection<Charger>) this.repository.findAll(where);
    }

    @Override
    protected boolean validateEntity(Charger charger) {
        return this.chargerTypeRepository.findById(charger.getChargerType().getId()).isPresent() &&
                (charger.getCurrentlyChargingCar() == null || this.chargerRepository.findById(charger.getCurrentlyChargingCar().getId()).isPresent()) &&
                (charger.getChargingStation() == null || this.chargingStationRepository.findById(charger.getChargingStation().getId()).isPresent());
    }

    public boolean carAttemptsCharging(Long chargerId, Long carId) throws NotValidUpdateException {
        Optional<Charger> chargerById = this.repository.findById(chargerId);
        if(chargerById.isEmpty()) {
            throw new NotValidUpdateException("");
        }
        else {
            Charger charger = chargerById.get();
            if(charger.getCurrentlyChargingCar() != null) {
                return false;
            }
            else {
                ElectricCar car = this.electricCarRepository.findById(carId).get();

                if(!car.getCarType().getCompatibleChargerTypes().contains(charger.getChargerType())) {
                    return false;
                }
                car.setCharger(charger);
                this.electricCarRepository.save(car);

                charger.setCurrentlyChargingCar(car);
                this.repository.save(charger);

                return true;
            }
        }
    }
}
