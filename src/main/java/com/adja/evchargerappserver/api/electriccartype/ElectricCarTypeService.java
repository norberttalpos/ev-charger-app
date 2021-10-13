package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import org.springframework.stereotype.Service;

@Service
public class ElectricCarTypeService extends AbstractService<ElectricCarType,ElectricCarTypeRepository> {

    @Override
    protected boolean validateEntity(ElectricCarType electricCarType) {
        return false;
    }
}
