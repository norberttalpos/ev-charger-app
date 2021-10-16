package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.electriccar.mock.MockElectricCarHandler;
import com.adja.evchargerappserver.api.electriccartype.ElectricCarTypeRepository;
import com.adja.evchargerappserver.api.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ElectricCarService extends AbstractService<ElectricCar,ElectricCarRepository> {
    @Autowired
    ElectricCarTypeRepository electricCarTypeRepository;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    private MockElectricCarHandler mockElectricCarHandler;

    @Override
    protected boolean validateEntity(ElectricCar electricCar) {
        return electricCarTypeRepository.findById(electricCar.getCarType().getId()).isPresent() &&
                ( personRepository.findById(electricCar.getDriver().getId()).isPresent() || electricCar.getDriver()==null);
    }

    public boolean startCharging(Long carId) {
        mockElectricCarHandler.startCharging(carId);
        return true;
    }

    public boolean endCharging(Long carId) {
        mockElectricCarHandler.endCharging(carId);

        return true;
    }

    public void updateAfterCharging(Long carId, Integer batteryPercentage) {
        Optional<ElectricCar> electricCar = this.repository.findById(carId);
        electricCar.get().setBatteryPercentage(batteryPercentage);

        this.repository.save(electricCar.get());
    }
}
