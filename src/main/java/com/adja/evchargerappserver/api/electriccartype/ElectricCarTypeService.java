package com.adja.evchargerappserver.api.electriccartype;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.chargertype.ChargerTypeRepository;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ElectricCarTypeService extends AbstractService<ElectricCarType, ElectricCarTypeFilter, ElectricCarTypeRepository> {
    @Autowired
    ChargerTypeRepository chargerTypeRepository;

    @Override
    public Collection<ElectricCarType> search(ElectricCarTypeFilter electricCarTypeFilter) {
        return null;
    }

    @Override
    protected boolean validateEntity(ElectricCarType electricCarType) {

        for (var chargerType:electricCarType.getCompatibleChargerTypes()) {
            if( ! chargerTypeRepository.findById(chargerType.getId()).isPresent() )
                return false;
        }
        return true;
    }
}
