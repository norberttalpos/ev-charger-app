package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.chargertype.ChargerTypeRepository;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectricCarTypeService extends AbstractService<ElectricCarType,ElectricCarTypeRepository> {
    @Autowired
    ChargerTypeRepository chargerTypeRepository;

    @Override
    protected boolean validateEntity(ElectricCarType electricCarType) {

        for (var chargerType:electricCarType.getCompatibleChargerTypes()) {
            if( ! chargerTypeRepository.findById(chargerType.getId()).isPresent() )
                return false;
        }
        return true;
    }
}
