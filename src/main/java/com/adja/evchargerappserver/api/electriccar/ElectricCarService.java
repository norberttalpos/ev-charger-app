package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.electriccartype.ElectricCarTypeRepository;
import com.adja.evchargerappserver.api.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectricCarService extends AbstractService<ElectricCar,ElectricCarRepository> {
    @Autowired
    ElectricCarTypeRepository electricCarTypeRepository;
    @Autowired
    PersonRepository personRepository;

    @Override
    protected boolean validateEntity(ElectricCar electricCar) {
        return electricCarTypeRepository.findById(electricCar.getCarType().getId()).isPresent() &&
                ( personRepository.findById(electricCar.getDriver().getId()).isPresent() || electricCar.getDriver()==null);
    }
}
