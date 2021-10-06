package com.adja.evchargerappserver.api.electriccar;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.electriccartype.ElectricCarTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class ElectricCarService extends AbstractService<ElectricCar,ElectricCarRepository> {
    ElectricCarTypeRepository electricCarTypeRepository;
    @Override
    protected boolean validateEntity(ElectricCar electricCar) {
        return false; //electricCarTypeRepository.findById(electricCar.getCarType().getId()).isPresent() ;
    }
}
