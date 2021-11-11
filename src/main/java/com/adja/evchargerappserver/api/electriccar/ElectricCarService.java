package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.NotValidUpdateException;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.charger.ChargerService;
import com.adja.evchargerappserver.api.charger.QCharger;
import com.adja.evchargerappserver.api.electriccar.mock.MockElectricCarHandler;
import com.adja.evchargerappserver.api.electriccartype.ElectricCarTypeRepository;
import com.adja.evchargerappserver.api.notification.NotificationService;
import com.adja.evchargerappserver.api.person.PersonRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ElectricCarService extends AbstractService<ElectricCar, ElectricCarFilter, ElectricCarRepository> {
    @Autowired
    private ElectricCarTypeRepository electricCarTypeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MockElectricCarHandler mockElectricCarHandler;

    @Autowired
    private ChargerService chargerService;

    @Autowired
    private NotificationService notificationService;


    @Override
    public Collection<ElectricCar> search(ElectricCarFilter filter) {
        QElectricCar electricCar = QElectricCar.electricCar;
        QCharger charger = QCharger.charger;
        BooleanBuilder where = new BooleanBuilder();

        if(filter.getLicensePlate() != null) {
            where.and(electricCar.licensePlate.eq(filter.getLicensePlate()));
        }
        if(filter.getDriver() != null) {
            where.and(electricCar.driver.id.eq(filter.getDriver()));
        }
        if(filter.getMinBatteryPercentage() != null) {
            where.and(electricCar.batteryPercentage.gt(filter.getMinBatteryPercentage()));
        }
        if(filter.getMaxBatteryPercentage() != null) {
            where.and(electricCar.batteryPercentage.lt(filter.getMaxBatteryPercentage()));
        }
        if(filter.getCharger() != null) {
            where.and(electricCar.charger.id.eq(filter.getCharger()));
        }
        if(filter.getCurrentlyCharging() != null) {
            if(filter.getCurrentlyCharging()) {
                where.and(electricCar.charger.id.isNotNull());
            }
            else {
                where.and(electricCar.charger.id.isNull());
            }
        }
        if(filter.getCarType() != null) {
            where.and(electricCar.carType.name.contains(filter.getCarType()));
        }

        return (Collection<ElectricCar>) this.repository.findAll(where);
    }

    @Override
    protected boolean validateEntity(ElectricCar electricCar) {
        return electricCarTypeRepository.findById(electricCar.getCarType().getId()).isPresent() &&
                ( personRepository.findById(electricCar.getDriver().getId()).isPresent() || electricCar.getDriver()==null);
    }

    public boolean startCharging(Long carId, Long chargerId) throws NotValidUpdateException {
        Optional<ElectricCar> carById = this.repository.findById(carId);
        if(carById.isEmpty()) {
            throw new NotValidUpdateException("");
        }
        ElectricCar car = carById.get();

        if(car.getCharger() != null) {
            return false;
        }
        boolean successfulAttempt = chargerService.carAttemptsCharging(chargerId, carId);

        if(successfulAttempt)
            mockElectricCarHandler.startCharging(car);

        return successfulAttempt;
    }

    public boolean endCharging(Long carId) throws NotValidUpdateException {
        Optional<ElectricCar> carById = this.repository.findById(carId);
        if(carById.isEmpty()) {
            throw new NotValidUpdateException("");
        }
        ElectricCar car = carById.get();

        if(car.getCharger() != null) {
            Charger charger = this.chargerService.getById(car.getCharger().getId());
            charger.setCurrentlyChargingCar(null);

            car.setCharger(null);

            this.chargerService.put(charger.getId(), charger);
            this.put(carId, car);

            mockElectricCarHandler.endCharging(car);

            this.notificationService.chargingEnded(charger.getId());
        }

        return true;
    }

    public void persistBatteryPercentageChanges(Long carId, Integer batteryPercentage) {
        Optional<ElectricCar> electricCar = this.repository.findById(carId);
        electricCar.get().setBatteryPercentage(batteryPercentage);

        this.repository.save(electricCar.get());
    }
}
