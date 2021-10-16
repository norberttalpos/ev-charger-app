package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.abstracts.NotValidUpdateException;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.charger.ChargerService;
import com.adja.evchargerappserver.api.electriccar.mock.MockElectricCarHandler;
import com.adja.evchargerappserver.api.electriccartype.ElectricCarTypeRepository;
import com.adja.evchargerappserver.api.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ElectricCarService extends AbstractService<ElectricCar,ElectricCarRepository> {
    @Autowired
    private ElectricCarTypeRepository electricCarTypeRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MockElectricCarHandler mockElectricCarHandler;
    @Autowired
    private ChargerService chargerService;

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

            mockElectricCarHandler.endCharging(carId);
        }

        return true;
    }

    public void updateAfterCharging(Long carId, Integer batteryPercentage) {
        Optional<ElectricCar> electricCar = this.repository.findById(carId);
        electricCar.get().setBatteryPercentage(batteryPercentage);

        this.repository.save(electricCar.get());
    }
}
