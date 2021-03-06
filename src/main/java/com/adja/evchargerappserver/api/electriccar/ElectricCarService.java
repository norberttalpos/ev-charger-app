package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.NotValidUpdateException;
import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.charger.ChargerService;
import com.adja.evchargerappserver.api.electriccar.mock.MockElectricCarHandler;
import com.adja.evchargerappserver.api.electriccartype.ElectricCarTypeRepository;
import com.adja.evchargerappserver.api.notification.NotificationService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class ElectricCarService extends AbstractService<ElectricCar, ElectricCarFilter, ElectricCarRepository> {
    @Autowired
    private ElectricCarTypeRepository electricCarTypeRepository;

    @Autowired
    private MockElectricCarHandler mockElectricCarHandler;

    @Autowired
    private ChargerService chargerService;

    @Autowired
    private NotificationService notificationService;


    @Override
    public Collection<ElectricCar> search(ElectricCarFilter filter) {
        QElectricCar electricCar = QElectricCar.electricCar;
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
    public ElectricCar post(ElectricCar entity) throws NotValidUpdateException {
        ElectricCar car = super.post(entity);

        mockElectricCarHandler.newCar(car);

        return car;
    }

    @Override
    protected boolean validateEntity(ElectricCar electricCar) {
        if(electricCarTypeRepository.findById(electricCar.getCarType().getId()).isPresent()) {

            ElectricCarFilter filter = new ElectricCarFilter();
            filter.setLicensePlate(electricCar.getLicensePlate());

            return this.search(filter).size() == 0;
        }
        else
            return false;
    }

    @Override
    protected ElectricCar mapToEntity(ElectricCar persisted, ElectricCar dto) {
        dto.setCharger(persisted.getCharger());
        dto.setDriver(persisted.getDriver());
        dto.setBatteryPercentage(persisted.getBatteryPercentage());
        dto.setLicensePlate(persisted.getLicensePlate());

        return dto;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = NotValidUpdateException.class)
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

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = NotValidUpdateException.class)
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

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void persistBatteryPercentageChanges(Long carId, Integer batteryPercentage) {
        Optional<ElectricCar> electricCar = this.repository.findById(carId);
        electricCar.get().setBatteryPercentage(batteryPercentage);

        this.repository.save(electricCar.get());
    }
}
